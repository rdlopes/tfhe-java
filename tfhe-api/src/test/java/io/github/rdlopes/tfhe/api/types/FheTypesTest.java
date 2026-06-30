package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.*;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.extended.*;
import io.github.rdlopes.tfhe.core.utils.FheRegistry;
import io.github.rdlopes.tfhe.api.utils.FheUtils;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class FheTypesTest {

  private static final KeySet keySet;
  private static final ClientKey clientKey;
  private static final PublicKey publicKey;
  private static final ServerKey serverKey;

  static {
    keySet = KeySet.builder()
                   .useCustomParameters(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .enableCompression(CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .build();
    clientKey = keySet.getClientKey();
    serverKey = keySet.getServerKey();
    publicKey = new PublicKey(clientKey);
    serverKey.use();
  }

  private boolean isCoveredType(Class<?> clazz) {
    return isCoveredTypeName(clazz.getSimpleName());
  }

  private boolean isCoveredTypeName(String name) {
    if (name.startsWith("Compressed")) {
      name = name.substring(10);
    }
    if (name.endsWith("Array")) {
      name = name.substring(0, name.length() - 5);
    }
    if (name.equals("FheBool")) {
      return true;
    }
    int bitSize = FheUtils.bitSize(name);
    boolean signed = FheUtils.isSigned(name);
    if (signed) {
      return false;
    }
    return bitSize == 2 || bitSize == 8 || bitSize == 128;
  }

  @TestFactory
  Stream<DynamicNode> generateZombiesTests() throws Exception {
    String packageName = "io.github.rdlopes.tfhe.api.types";
    String packagePath = packageName.replace('.', '/');
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> resources = classLoader.getResources(packagePath);
    Set<Class<?>> classes = new LinkedHashSet<>();

    while (resources.hasMoreElements()) {
      URL resource = resources.nextElement();
      String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
      java.io.File directory = new java.io.File(filePath);
      if (directory.exists() && directory.isDirectory()) {
        java.io.File[] files = directory.listFiles();
        if (files != null) {
          Arrays.sort(files, Comparator.comparing(java.io.File::getName));
          for (java.io.File file : files) {
            String name = file.getName();
            if (name.endsWith(".class")) {
              String className = packageName + "." + name.substring(0, name.length() - 6);
              if (className.contains("Test") || className.contains("$")) {
                continue;
              }
              try {
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
              } catch (Throwable _) {
                // Ignore
              }
            }
          }
        }
      }
    }

    classes.add(FheUint2.class);
    classes.add(CompressedFheUint2.class);
    classes.add(FheUint2Array.class);

    List<DynamicNode> containers = new ArrayList<>();
    for (Class<?> clazz : classes) {
      if (AbstractFheType.class.isAssignableFrom(clazz) || clazz == FheBool.class) {
        containers.add(buildFheTypeContainer(clazz));
      } else if (FheArray.class.isAssignableFrom(clazz)) {
        containers.add(buildFheArrayContainer(clazz));
      } else if (clazz == CompactCiphertextList.class
          || clazz == CompactCiphertextListBuilder.class
          || clazz == CompactCiphertextListExpander.class
          || clazz == CompressedCiphertextList.class
          || clazz == CompressedCiphertextListBuilder.class
          || clazz == ProvenCompactCiphertextList.class) {
        containers.add(buildListAndBuilderContainer(clazz));
      } else if (clazz == ZkComputeLoad.class) {
        containers.add(buildZkComputeLoadContainer());
      }
    }

    return containers.stream();
  }

  private DynamicNode buildFheTypeContainer(Class<?> clazz) {
    String name = clazz.getSimpleName();
    int bitSize = FheUtils.bitSize(name);
    boolean signed = FheUtils.isSigned(name);
    boolean isSupported = isCoveredType(clazz);

    Class<?> cleartextType = null;
    for (Method m : clazz.getMethods()) {
      if (m.getName().equals("encrypt") && m.getParameterCount() == 2 && m.getParameterTypes()[1] == ClientKey.class) {
        cleartextType = m.getParameterTypes()[0];
        break;
      }
    }
    if (cleartextType == null) {
      cleartextType = Boolean.class;
    }

    Class<?> finalCleartextType = cleartextType;
    List<DynamicTest> tests = new ArrayList<>();

    // 1. Z (Zero) Tests
    tests.add(dynamicTest("Z - Encrypt and Decrypt Zero", () -> {
      if (!isSupported) {
        testUnsupportedTypeLifecycle(clazz);
        return;
      }
      Object zeroVal = getZeroValue(finalCleartextType);
      try (AutoCloseable encrypted = encrypt(clazz, finalCleartextType, zeroVal, clientKey)) {
        Object decrypted = decrypt(encrypted);
        assertThat(decrypted).isEqualTo(zeroVal);
      }
    }));

    // 2. O (One) Tests
    tests.add(dynamicTest("O - Encrypt and Decrypt One", () -> {
      if (!isSupported) return;
      Object oneVal = getOneValue(finalCleartextType);
      try (AutoCloseable encrypted = encrypt(clazz, finalCleartextType, oneVal, clientKey)) {
        Object decrypted = decrypt(encrypted);
        assertThat(decrypted).isEqualTo(oneVal);
      }
    }));

    // 3. M (Many) Tests
    tests.add(dynamicTest("M - Basic Operations (Many)", () -> {
      if (!isSupported) return;
      Object manyVal1 = getManyValue(finalCleartextType, bitSize, signed, 1);
      Object manyVal2 = getManyValue(finalCleartextType, bitSize, signed, 2);
      try (AutoCloseable enc1 = encrypt(clazz, finalCleartextType, manyVal1, clientKey);
           AutoCloseable enc2 = encrypt(clazz, finalCleartextType, manyVal2, clientKey)) {
        testAllFheOperations(enc1, enc2, manyVal1, manyVal2, finalCleartextType, bitSize, signed);
      }
    }));

    // 4. B (Boundaries) Tests
    tests.add(dynamicTest("B - Boundary Values (Min/Max)", () -> {
      if (!isSupported) return;
      Object minVal = getMinValue(finalCleartextType, bitSize, signed);
      Object maxVal = getMaxValue(finalCleartextType, bitSize, signed);

      try (AutoCloseable encMin = encrypt(clazz, finalCleartextType, minVal, clientKey);
           AutoCloseable encMax = encrypt(clazz, finalCleartextType, maxVal, clientKey)) {
        assertThat(decrypt(encMin)).isEqualTo(minVal);
        assertThat(decrypt(encMax)).isEqualTo(maxVal);
      }
    }));

    // 5. I (Interface / Serialization / Compression / Casting) Tests
    tests.add(dynamicTest("I - Serialization, Compression, Casting Interfaces", () -> {
      if (!isSupported) return;
      Object clearVal = getManyValue(finalCleartextType, bitSize, signed, 1);
      try (AutoCloseable encrypted = encrypt(clazz, finalCleartextType, clearVal, clientKey)) {
        
        // 5a. Serialization
        if (encrypted instanceof FheObject fheObj) {
          try (DynamicBuffer buffer = fheObj.serialize()) {
            assertThat(buffer).isNotNull();
            Method deserializeMethod = clazz.getMethod("deserialize", DynamicBuffer.class, ServerKey.class);
            try (AutoCloseable deserialized = (AutoCloseable) deserializeMethod.invoke(null, buffer, serverKey)) {
              assertThat(decrypt(deserialized)).isEqualTo(clearVal);
            }
          }
        }

        // 5b. Compression
        if (encrypted instanceof FheType fheType) {
          try (AutoCloseable compressed = (AutoCloseable) fheType.compress()) {
            assertThat(compressed).isNotNull();
            Method decompressMethod = compressed.getClass().getMethod("decompress");
            try (AutoCloseable decompressed = (AutoCloseable) decompressMethod.invoke(compressed)) {
              assertThat(decrypt(decompressed)).isEqualTo(clearVal);
            }
          }
        }

        // 5c. Casting (if applicable)
        if (encrypted instanceof AbstractFheType<?, ?, ?> fheType && clazz != FheBool.class) {
          // Cast to itself (always supported)
          Method castIntoMethod = AbstractFheType.class.getMethod("castInto", Class.class);
          try (AutoCloseable casted = (AutoCloseable) castIntoMethod.invoke(fheType, clazz)) {
            assertThat(casted).isNotNull();
          }
        }
      }
    }));

    // 6. E (Exceptional Behavior) Tests
    tests.add(dynamicTest("E - Exceptional Deserialization", () -> {
      if (!isSupported) return;
      try (DynamicBuffer emptyBuffer = new DynamicBuffer()) {
        Method deserializeMethod = clazz.getMethod("deserialize", DynamicBuffer.class, ServerKey.class);
        assertThatThrownBy(() -> deserializeMethod.invoke(null, emptyBuffer, serverKey))
            .isNotNull();
      }
    }));

    // 7. S (Simple Scenarios / Comparisons) Tests
    tests.add(dynamicTest("S - Simple Comparisons and Equality", () -> {
      if (!isSupported) return;
      Object val1 = getManyValue(finalCleartextType, bitSize, signed, 1);
      Object val2 = getManyValue(finalCleartextType, bitSize, signed, 2);

      try (AutoCloseable enc1 = encrypt(clazz, finalCleartextType, val1, clientKey);
           AutoCloseable enc2 = encrypt(clazz, finalCleartextType, val2, clientKey)) {
        
        if (enc1 instanceof FheEquality<?, ?> eq) {
          try (AutoCloseable equalsResult = (AutoCloseable) eq.equalTo(castHelper(enc2))) {
            assertThat(equalsResult).isNotNull();
            Object eqDec = decrypt(equalsResult);
            assertThat(eqDec).isEqualTo(val1.equals(val2));
          }
        }
      }
    }));

    // 8. Static Factory Tests (PublicKey, Trivial, and ifThenElse)
    tests.add(dynamicTest("Static Factories & Conditional Selection", () -> {
      if (!isSupported) return;
      Object zeroVal = getZeroValue(finalCleartextType);
      Object oneVal = getOneValue(finalCleartextType);
      Object val = getManyValue(finalCleartextType, bitSize, signed, 1);
      
      // Convert to AbstractValue if needed
      Object finalVal = val;
      if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(finalCleartextType) && val instanceof BigInteger bi) {
        finalVal = finalCleartextType.getMethod("of", BigInteger.class).invoke(null, bi);
      }

      // Test encrypt with PublicKey
      try {
        Method encryptPub = clazz.getMethod("encrypt", finalCleartextType, PublicKey.class);
        try (AutoCloseable encPub = (AutoCloseable) encryptPub.invoke(null, finalVal, publicKey)) {
          assertThat(decrypt(encPub)).isEqualTo(val);
        }
      } catch (NoSuchMethodException _) {
        // Ignore
      }

      // Test encrypt trivial
      try {
        Method encryptTrivial = clazz.getMethod("encrypt", finalCleartextType);
        try (AutoCloseable encTrivial = (AutoCloseable) encryptTrivial.invoke(null, finalVal)) {
          assertThat(decrypt(encTrivial)).isEqualTo(val);

          Method tryDecryptTrivialMethod = clazz.getMethod("tryDecryptTrivial");
          Optional<?> decryptedTrivial = (Optional<?>) tryDecryptTrivialMethod.invoke(encTrivial);
          assertThat(decryptedTrivial).isPresent();
          Object decryptedVal = decryptedTrivial.get();
          if (decryptedVal instanceof io.github.rdlopes.tfhe.api.values.AbstractValue av) {
            assertThat(av.asBigInteger()).isEqualTo(val instanceof BigInteger bi ? bi : BigInteger.valueOf(((Number) val).longValue()));
          } else {
            assertThat(decryptedVal).isEqualTo(val);
          }
        }
      } catch (NoSuchMethodException _) {
        // Ignore
      }

      // Test ifThenElse
      try {
        Method ifThenElseMethod = clazz.getMethod("ifThenElse", FheBool.class, clazz, clazz);
        try (FheBool cond = FheBool.encrypt(true, clientKey);
             AutoCloseable thenVal = encrypt(clazz, finalCleartextType, oneVal, clientKey);
             AutoCloseable elseVal = encrypt(clazz, finalCleartextType, zeroVal, clientKey);
             AutoCloseable result = (AutoCloseable) ifThenElseMethod.invoke(null, cond, thenVal, elseVal)) {
          assertThat(decrypt(result)).isEqualTo(oneVal);
        }
      } catch (NoSuchMethodException _) {
        // Ignore
      }
    }));

    return dynamicContainer(name + " ZOMBIES Tests", tests);
  }

  private DynamicNode buildFheArrayContainer(Class<?> clazz) {
    String name = clazz.getSimpleName();
    String elemTypeName = name.substring(0, name.length() - 5); // strip "Array"
    boolean isSupported = isCoveredTypeName(elemTypeName);
    
    List<DynamicTest> tests = new ArrayList<>();

    // Z (Zero): empty array
    tests.add(dynamicTest("Z - Empty Array Lifecycle", () -> {
      Constructor<?> ctor = clazz.getConstructor(Collection.class);
      try (AutoCloseable array = (AutoCloseable) ctor.newInstance(List.of())) {
        assertThat(array).isNotNull();
        if (array instanceof io.github.rdlopes.tfhe.core.ffm.NativeArray nativeArray) {
          assertThat(nativeArray.getSize()).isZero();
        }
      }
    }));

    // O (One) & M (Many): array operations
    tests.add(dynamicTest("O / M / B - Array Sum & Arithmetic", () -> {
      if (!isSupported) return;
      try {
        Class<?> elemClass = Class.forName("io.github.rdlopes.tfhe.api.types." + elemTypeName);
        Class<?> cleartextType = null;
        for (Method m : elemClass.getMethods()) {
          if (m.getName().equals("encrypt") && m.getParameterCount() == 2 && m.getParameterTypes()[1] == ClientKey.class) {
            cleartextType = m.getParameterTypes()[0];
            break;
          }
        }
        if (cleartextType != null) {
          Object clearVal1 = castClearValue(2, cleartextType);
          Object clearVal2 = castClearValue(3, cleartextType);
          Object finalClearVal1 = clearVal1;
          Object finalClearVal2 = clearVal2;
          if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(cleartextType)) {
            if (clearVal1 instanceof BigInteger bi) finalClearVal1 = cleartextType.getMethod("of", BigInteger.class).invoke(null, bi);
            if (clearVal2 instanceof BigInteger bi) finalClearVal2 = cleartextType.getMethod("of", BigInteger.class).invoke(null, bi);
          }
          final Object cv1 = finalClearVal1;
          final Object cv2 = finalClearVal2;
          
          try (AutoCloseable elem1 = encrypt(elemClass, cleartextType, clearVal1, clientKey);
               AutoCloseable elem2 = encrypt(elemClass, cleartextType, clearVal2, clientKey)) {
            
            Constructor<?> ctor = clazz.getConstructor(Collection.class);
            List<?> elements = List.of(elem1, elem2);
            try (AutoCloseable array = (AutoCloseable) ctor.newInstance(elements)) {
              assertThat(array).isNotNull();
              
              if (array instanceof FheArray<?, ?> fheArray) {
                if (fheArray instanceof io.github.rdlopes.tfhe.core.ffm.NativeArray nativeArray) {
                  assertThat(nativeArray.getSize()).isEqualTo(2);
                }
                
                // Sum
                try (AutoCloseable sumElem = (AutoCloseable) fheArray.sum()) {
                  Object decryptedSum = decrypt(sumElem);
                  Object expectedSum = addClearValues(clearVal1, clearVal2, cleartextType);
                  assertThat(decryptedSum).isEqualTo(expectedSum);
                }

                // Add
                try (AutoCloseable addedArray = (AutoCloseable) fheArray.add(castHelper(fheArray))) {
                  assertThat(addedArray).isNotNull();
                }

                // Subtract
                try (AutoCloseable subArray = (AutoCloseable) fheArray.subtract(castHelper(fheArray))) {
                  assertThat(subArray).isNotNull();
                }

                // Contains Array
                try (FheBool res = fheArray.containsArray(castHelper(fheArray))) {
                  assertThat(res).isNotNull();
                }

                // Equals Array
                try (FheBool res = fheArray.equalsArray(castHelper(fheArray))) {
                  assertThat(res).isNotNull();
                }

                // Test static encrypt(Collection, ClientKey)
                try {
                  Method encryptCollClientKey = clazz.getMethod("encrypt", Collection.class, ClientKey.class);
                  try (AutoCloseable encColl = (AutoCloseable) encryptCollClientKey.invoke(null, List.of(cv1, cv2), clientKey)) {
                    assertThat(encColl).isNotNull();
                  }
                } catch (NoSuchMethodException _) {
                  // Ignore
                }

                // Test static encrypt(Collection, PublicKey)
                try {
                  Method encryptCollPublicKey = clazz.getMethod("encrypt", Collection.class, PublicKey.class);
                  try (AutoCloseable encColl = (AutoCloseable) encryptCollPublicKey.invoke(null, List.of(cv1, cv2), publicKey)) {
                    assertThat(encColl).isNotNull();
                  }
                } catch (NoSuchMethodException _) {
                  // Ignore
                }

                // Test static encrypt(Collection)
                try {
                  Method encryptCollTrivial = clazz.getMethod("encrypt", Collection.class);
                  try (AutoCloseable encColl = (AutoCloseable) encryptCollTrivial.invoke(null, List.of(cv1, cv2))) {
                    assertThat(encColl).isNotNull();
                  }
                } catch (NoSuchMethodException _) {
                  // Ignore
                }
              }
            }
          }
        }
      } catch (ClassNotFoundException _) {
        // Skip
      }
    }));

    return dynamicContainer(name + " ZOMBIES Tests", tests);
  }

  private DynamicNode buildListAndBuilderContainer(Class<?> clazz) {
    String name = clazz.getSimpleName();
    List<DynamicTest> tests = new ArrayList<>();

    tests.add(dynamicTest("Builder and List Lifecycle", () -> {
      if (clazz == CompressedCiphertextListBuilder.class) {
        try (CompressedCiphertextListBuilder builder = new CompressedCiphertextListBuilder();
             FheBool b = FheBool.encrypt(true, clientKey)) {
          assertThat(builder).isNotNull();
          builder.push(b);

          for (Method m : CompressedCiphertextListBuilder.class.getMethods()) {
            if (m.getName().equals("push") && m.getParameterCount() == 1) {
              Class<?> paramType = m.getParameterTypes()[0];
              if (paramType != FheBool.class && AutoCloseable.class.isAssignableFrom(paramType)) {
                Supplier<?> factory = FheRegistry.getFactory(paramType);
                if (factory != null) {
                  try (AutoCloseable instance = (AutoCloseable) factory.get()) {
                    m.invoke(builder, instance);
                  } catch (Exception _) {
                    // Ignore
                  }
                }
              }
            }
          }

          try (CompressedCiphertextList list = builder.build()) {
            assertThat(list).isNotNull();
            assertThat(list.size()).isGreaterThan(0);
            assertThat(list.getKindOf(0)).isEqualTo(FheTypes.FheBool);
            try (FheBool bDec = list.get(0, FheBool.class)) {
              assertThat(bDec.decrypt(clientKey)).isTrue();
            }
            // Serialization roundtrip
            try (DynamicBuffer buf = list.serialize()) {
              try (CompressedCiphertextList deserialized = CompressedCiphertextList.deserialize(buf)) {
                assertThat(deserialized).isNotNull();
              }
            }
          }
        }
      } else if (clazz == CompactCiphertextListBuilder.class) {
        CompactPublicKey pk = new CompactPublicKey(clientKey);
        try (CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(pk)) {
          builder.push(true);
          builder.push((byte) 42);

          for (Method m : CompactCiphertextListBuilder.class.getMethods()) {
            if (m.getName().equals("push") && m.getParameterCount() == 1) {
              Class<?> paramType = m.getParameterTypes()[0];
              try {
                if (paramType == short.class) {
                  m.invoke(builder, (short) 42);
                } else if (paramType == int.class) {
                  m.invoke(builder, 42);
                } else if (paramType == long.class) {
                  m.invoke(builder, 42L);
                } else if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(paramType)) {
                  Method ofMethod = paramType.getMethod("of", BigInteger.class);
                  Object val = ofMethod.invoke(null, BigInteger.valueOf(42));
                  m.invoke(builder, val);
                }
              } catch (Exception _) {
                // Ignore
              }
            } else if (m.getName().equals("pushUnsigned") && m.getParameterCount() == 1) {
              Class<?> paramType = m.getParameterTypes()[0];
              try {
                if (paramType == byte.class) {
                  m.invoke(builder, (byte) 42);
                } else if (paramType == short.class) {
                  m.invoke(builder, (short) 42);
                } else if (paramType == int.class) {
                  m.invoke(builder, 42);
                } else if (paramType == long.class) {
                  m.invoke(builder, 42L);
                }
              } catch (Exception _) {
                // Ignore
              }
            } else if ((m.getName().startsWith("pushI") || m.getName().startsWith("pushU")) && m.getParameterCount() == 1) {
              Class<?> paramType = m.getParameterTypes()[0];
              try {
                if (paramType == byte.class) {
                  m.invoke(builder, (byte) 1);
                } else if (paramType == short.class) {
                  m.invoke(builder, (short) 1);
                } else if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(paramType)) {
                  Method ofMethod = paramType.getMethod("of", BigInteger.class);
                  Object val = ofMethod.invoke(null, BigInteger.valueOf(1));
                  m.invoke(builder, val);
                }
              } catch (Exception _) {
                // Ignore
              }
            }
          }

          try (CompactCiphertextList list = builder.build()) {
            assertThat(list).isNotNull();
            try (CompactCiphertextListExpander expander = list.expand()) {
              assertThat(expander).isNotNull();
              assertThat(expander.size()).isGreaterThan(0);
              assertThat(expander.getKindOf(0)).isEqualTo(FheTypes.FheBool);
            }
          }
        }
      }
    }));

    return dynamicContainer(name + " Zombies", tests);
  }

  private DynamicNode buildZkComputeLoadContainer() {
    return dynamicContainer("ZkComputeLoad Zombies", List.of(
        dynamicTest("Values Check", () -> {
          assertThat(ZkComputeLoad.PROOF.getValue()).isZero();
          assertThat(ZkComputeLoad.VERIFY.getValue()).isOne();
          assertThat(ZkComputeLoad.valueOf("PROOF")).isEqualTo(ZkComputeLoad.PROOF);
        })
    ));
  }

  // Helper cast functions to bypass generic type rules in reflection
  @SuppressWarnings("unchecked")
  private <T> T castHelper(Object obj) {
    return (T) obj;
  }

  @SuppressWarnings("unchecked")
  private <E extends AbstractFheType<?, E, ?>, A extends AbstractFheArray<E, A>> A castHelperArray(Object obj) {
    return (A) obj;
  }

  private Object getZeroValue(Class<?> type) {
    if (type == Boolean.class || type == boolean.class) return false;
    if (type == Byte.class || type == byte.class) return (byte) 0;
    if (type == Short.class || type == short.class) return (short) 0;
    if (type == Integer.class || type == int.class) return 0;
    if (type == Long.class || type == long.class) return 0L;
    if (type == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(type)) return BigInteger.ZERO;
    return null;
  }

  private Object getOneValue(Class<?> type) {
    if (type == Boolean.class || type == boolean.class) return true;
    if (type == Byte.class || type == byte.class) return (byte) 1;
    if (type == Short.class || type == short.class) return (short) 1;
    if (type == Integer.class || type == int.class) return 1;
    if (type == Long.class || type == long.class) return 1L;
    if (type == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(type)) return BigInteger.ONE;
    return null;
  }

  private Object getManyValue(Class<?> type, int bitSize, boolean signed, int offset) {
    if (type == Boolean.class || type == boolean.class) return true;
    int intVal = (bitSize == 2 && signed) ? -1 : (2 + offset);
    return castClearValue(intVal, type);
  }

  private Object getMinValue(Class<?> type, int bitSize, boolean signed) {
    if (type == Boolean.class || type == boolean.class) return false;
    if (type == Byte.class || type == byte.class) {
      return signed ? (byte) (-(1 << (bitSize - 1))) : (byte) 0;
    }
    if (type == Short.class || type == short.class) {
      return signed ? (short) (-(1 << (bitSize - 1))) : (short) 0;
    }
    if (type == Integer.class || type == int.class) {
      return signed ? -(1 << (bitSize - 1)) : 0;
    }
    if (type == Long.class || type == long.class) {
      return signed ? -(1L << (bitSize - 1)) : 0L;
    }
    if (type == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(type)) {
      return signed ? BigInteger.TWO.pow(bitSize - 1).negate() : BigInteger.ZERO;
    }
    return null;
  }

  private Object getMaxValue(Class<?> type, int bitSize, boolean signed) {
    if (type == Boolean.class || type == boolean.class) return true;
    if (type == Byte.class || type == byte.class) {
      return signed ? (byte) ((1 << (bitSize - 1)) - 1) : (byte) ((1 << bitSize) - 1);
    }
    if (type == Short.class || type == short.class) {
      return signed ? (short) ((1 << (bitSize - 1)) - 1) : (short) ((1 << bitSize) - 1);
    }
    if (type == Integer.class || type == int.class) {
      return signed ? (1 << (bitSize - 1)) - 1 : (1 << bitSize) - 1;
    }
    if (type == Long.class || type == long.class) {
      return signed ? (1L << (bitSize - 1)) - 1 : (1L << bitSize) - 1;
    }
    if (type == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(type)) {
      return signed ? BigInteger.TWO.pow(bitSize - 1).subtract(BigInteger.ONE) : BigInteger.TWO.pow(bitSize).subtract(BigInteger.ONE);
    }
    return null;
  }

  private Object castClearValue(Object value, Class<?> targetType) {
    if (targetType == Boolean.class || targetType == boolean.class) {
      if (value instanceof Boolean) return value;
      if (value instanceof Number n) return n.intValue() != 0;
    }
    if (value instanceof Number num) {
      if (targetType == Byte.class || targetType == byte.class) return num.byteValue();
      if (targetType == Short.class || targetType == short.class) return num.shortValue();
      if (targetType == Integer.class || targetType == int.class) return num.intValue();
      if (targetType == Long.class || targetType == long.class) return num.longValue();
      if (targetType == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(targetType)) {
        return BigInteger.valueOf(num.longValue());
      }
    }
    return value;
  }

  private Object addClearValues(Object v1, Object v2, Class<?> targetType) {
    if (targetType == Byte.class || targetType == byte.class) return (byte) (((Byte) v1) + ((Byte) v2));
    if (targetType == Short.class || targetType == short.class) return (short) (((Short) v1) + ((Short) v2));
    if (targetType == Integer.class || targetType == int.class) return ((Integer) v1) + ((Integer) v2);
    if (targetType == Long.class || targetType == long.class) return ((Long) v1) + ((Long) v2);
    if (targetType == BigInteger.class || io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(targetType)) {
      return ((BigInteger) v1).add((BigInteger) v2);
    }
    return v1;
  }

  private AutoCloseable encrypt(Class<?> clazz, Class<?> cleartextType, Object value, ClientKey clientKey) throws Exception {
    try {
      Object finalValue = value;
      if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(cleartextType) && value instanceof BigInteger bi) {
        Method ofMethod = cleartextType.getMethod("of", BigInteger.class);
        finalValue = ofMethod.invoke(null, bi);
      }
      Method encryptMethod = clazz.getMethod("encrypt", cleartextType, ClientKey.class);
      return (AutoCloseable) encryptMethod.invoke(null, finalValue, clientKey);
    } catch (IllegalArgumentException e) {
      System.err.println("Mismatch for class: " + clazz.getName() 
          + ", cleartextType: " + cleartextType.getName() 
          + ", value type: " + (value != null ? value.getClass().getName() : "null")
          + ", value: " + value);
      throw e;
    }
  }

  private Object decrypt(AutoCloseable fheInstance) throws Exception {
    Method decryptMethod = fheInstance.getClass().getMethod("decrypt", ClientKey.class);
    Object result = decryptMethod.invoke(fheInstance, clientKey);
    if (result instanceof io.github.rdlopes.tfhe.api.values.AbstractValue value) {
      return value.asBigInteger();
    }
    return result;
  }

  private void testUnsupportedTypeLifecycle(Class<?> clazz) throws Exception {
    Supplier<?> factory = FheRegistry.getFactory(clazz);
    if (factory == null) return;
    
    // Test instantiation
    try (AutoCloseable instance = (AutoCloseable) factory.get()) {
      assertThat(instance).isNotNull();
      
      // Test newCompressed
      Method newCompressedMethod = clazz.getDeclaredMethod("newCompressed");
      newCompressedMethod.setAccessible(true);
      try (AutoCloseable compressed = (AutoCloseable) newCompressedMethod.invoke(instance)) {
        assertThat(compressed).isNotNull();
      }
      
      // Test handles
      Method handlesMethod = clazz.getDeclaredMethod("handles");
      handlesMethod.setAccessible(true);
      assertThat(handlesMethod.invoke(instance)).isNotNull();
    }

    // Identify V (cleartext type)
    Class<?> cleartextType = null;
    for (Method m : clazz.getMethods()) {
      if (m.getName().equals("encrypt") && m.getParameterCount() == 2 && m.getParameterTypes()[1] == ClientKey.class) {
        cleartextType = m.getParameterTypes()[0];
        break;
      }
    }
    if (cleartextType == null) return;

    Object zeroVal = getZeroValue(cleartextType);
    Object finalZeroVal = zeroVal;
    if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(cleartextType) && zeroVal instanceof BigInteger bi) {
      finalZeroVal = cleartextType.getMethod("of", BigInteger.class).invoke(null, bi);
    }
    final Object zeroValToUse = finalZeroVal;
    
    // Call static encrypt(V, ClientKey) with null key
    Method encryptClientKey = clazz.getMethod("encrypt", cleartextType, ClientKey.class);
    assertThatThrownBy(() -> encryptClientKey.invoke(null, zeroValToUse, null))
        .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
        .hasCauseInstanceOf(NullPointerException.class);

    // Call static encrypt(V, PublicKey) with null key
    Method encryptPublicKey = clazz.getMethod("encrypt", cleartextType, PublicKey.class);
    assertThatThrownBy(() -> encryptPublicKey.invoke(null, zeroValToUse, null))
        .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
        .hasCauseInstanceOf(NullPointerException.class);

    // Call static deserialize(DynamicBuffer, ServerKey) with null buffer
    Method deserializeMethod = clazz.getMethod("deserialize", DynamicBuffer.class, ServerKey.class);
    assertThatThrownBy(() -> deserializeMethod.invoke(null, null, serverKey))
        .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
        .hasCauseInstanceOf(NullPointerException.class);

    // Call static ifThenElse(FheBool, T, T) with null condition
    Method ifThenElseMethod = clazz.getMethod("ifThenElse", FheBool.class, clazz, clazz);
    assertThatThrownBy(() -> ifThenElseMethod.invoke(null, null, null, null))
        .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
        .hasCauseInstanceOf(NullPointerException.class);
    
    // Trivial Encryption and roundtrip (if supported by native library)
    try {
      Method encryptTrivial = clazz.getMethod("encrypt", cleartextType);
      try (AutoCloseable trivialEncrypted = (AutoCloseable) encryptTrivial.invoke(null, zeroValToUse)) {
        assertThat(trivialEncrypted).isNotNull();
        
        Method decryptMethod = clazz.getMethod("decrypt", ClientKey.class);
        assertThatThrownBy(() -> decryptMethod.invoke(trivialEncrypted, (ClientKey) null))
            .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
            .hasCauseInstanceOf(NullPointerException.class);
      }
    } catch (Exception _) {
      // Catch native/JVM/NoSuchMethodException errors gracefully
    }
  }

  private void testAllFheOperations(AutoCloseable enc1, AutoCloseable enc2, Object val1, Object val2, Class<?> cleartextType, int bitSize, boolean signed) throws Exception {
    boolean runAll = (bitSize <= 64);
    Object scalarVal2 = val2;
    if (io.github.rdlopes.tfhe.api.values.AbstractValue.class.isAssignableFrom(cleartextType) && val2 instanceof BigInteger bi) {
      scalarVal2 = cleartextType.getMethod("of", BigInteger.class).invoke(null, bi);
    }

    if (enc1 instanceof FheLogic<?, ?> logic1 && enc2 instanceof FheLogic<?, ?> logic2) {
      try (AutoCloseable notResult = (AutoCloseable) logic1.bitNot()) {
        assertThat(notResult).isNotNull();
      }
      try (AutoCloseable andResult = (AutoCloseable) logic1.bitAnd(castHelper(logic2))) {
        assertThat(andResult).isNotNull();
      }
      try (AutoCloseable orResult = (AutoCloseable) logic1.bitOr(castHelper(logic2))) {
        assertThat(orResult).isNotNull();
      }
      try (AutoCloseable xorResult = (AutoCloseable) logic1.bitXor(castHelper(logic2))) {
        assertThat(xorResult).isNotNull();
      }
      try (AutoCloseable andScalarResult = (AutoCloseable) logic1.bitAndScalar(castHelper(scalarVal2))) {
        assertThat(andScalarResult).isNotNull();
      }
      try (AutoCloseable orScalarResult = (AutoCloseable) logic1.bitOrScalar(castHelper(scalarVal2))) {
        assertThat(orScalarResult).isNotNull();
      }
      try (AutoCloseable xorScalarResult = (AutoCloseable) logic1.bitXorScalar(castHelper(scalarVal2))) {
        assertThat(xorScalarResult).isNotNull();
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitAndAssign(castHelper(logic2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitOrAssign(castHelper(logic2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitXorAssign(castHelper(logic2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitAndScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitOrScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheLogic<?, ?> cloneLogic) {
          cloneLogic.bitXorScalarAssign(castHelper(scalarVal2));
        }
      }
    }
    
    if (enc1 instanceof FheArithmetics<?, ?> arith1 && enc2 instanceof FheArithmetics<?, ?> arith2) {
      try (AutoCloseable added = (AutoCloseable) arith1.add(castHelper(arith2))) {
        assertThat(added).isNotNull();
      }
      try (AutoCloseable subResult = (AutoCloseable) arith1.subtract(castHelper(arith2))) {
        assertThat(subResult).isNotNull();
      }
      if (runAll) {
        try (AutoCloseable mulResult = (AutoCloseable) arith1.multiply(castHelper(arith2))) {
          assertThat(mulResult).isNotNull();
        }
        try (AutoCloseable divResult = (AutoCloseable) arith1.divide(castHelper(arith2))) {
          assertThat(divResult).isNotNull();
        }
        try (AutoCloseable remResult = (AutoCloseable) arith1.remainder(castHelper(arith2))) {
          assertThat(remResult).isNotNull();
        }
      }
      try (AutoCloseable negResult = (AutoCloseable) arith1.negate()) {
        assertThat(negResult).isNotNull();
      }
      try (AutoCloseable addScalar = (AutoCloseable) arith1.addScalar(castHelper(scalarVal2))) {
        assertThat(addScalar).isNotNull();
      }
      try (AutoCloseable subScalar = (AutoCloseable) arith1.subtractScalar(castHelper(scalarVal2))) {
        assertThat(subScalar).isNotNull();
      }
      if (runAll) {
        try (AutoCloseable mulScalar = (AutoCloseable) arith1.multiplyScalar(castHelper(scalarVal2))) {
          assertThat(mulScalar).isNotNull();
        }
        try (AutoCloseable divScalar = (AutoCloseable) arith1.divideScalar(castHelper(scalarVal2))) {
          assertThat(divScalar).isNotNull();
        }
        try (AutoCloseable remScalar = (AutoCloseable) arith1.remainderScalar(castHelper(scalarVal2))) {
          assertThat(remScalar).isNotNull();
        }
      }
      
      assertThat(arith1.addWithOverflow(castHelper(arith2))).isNotNull();
      assertThat(arith1.subtractWithOverflow(castHelper(arith2))).isNotNull();
      if (runAll) {
        assertThat(arith1.multiplyWithOverflow(castHelper(arith2))).isNotNull();
        assertThat(arith1.divideWithRemainder(castHelper(arith2))).isNotNull();
        assertThat(arith1.divideWithRemainderScalar(castHelper(scalarVal2))).isNotNull();
      }
      
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
          cloneArith.addAssign(castHelper(arith2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
          cloneArith.subtractAssign(castHelper(arith2));
        }
      }
      if (runAll) {
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
             cloneArith.multiplyAssign(castHelper(arith2));
          }
        }
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
             cloneArith.divideAssign(castHelper(arith2));
          }
        }
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
             cloneArith.remainderAssign(castHelper(arith2));
          }
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
          cloneArith.addScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
          cloneArith.subtractScalarAssign(castHelper(scalarVal2));
        }
      }
      if (runAll) {
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
            cloneArith.multiplyScalarAssign(castHelper(scalarVal2));
          }
        }
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
            cloneArith.divideScalarAssign(castHelper(scalarVal2));
          }
        }
        try (AutoCloseable cloneObj = cloneHelper(enc1)) {
          if (cloneObj instanceof FheArithmetics<?, ?> cloneArith) {
            cloneArith.remainderScalarAssign(castHelper(scalarVal2));
          }
        }
        
        BigInteger val1BI = toBigInteger(val1);
        if (val1BI.compareTo(BigInteger.ZERO) > 0) {
          int expectedILog2 = val1BI.bitLength() - 1;
          try (AutoCloseable ilog2Val = (AutoCloseable) arith1.ilog2()) {
            assertThat(ilog2Val).isNotNull();
            assertThat(ilog2Val.getClass()).isEqualTo(enc1.getClass());
            Object decrypted = decrypt(ilog2Val);
            assertThat(toBigInteger(decrypted)).isEqualTo(BigInteger.valueOf(expectedILog2));
          }

          FheArithmetics.CheckedResult<?> checkedResult = arith1.ilog2WithCheck();
          assertThat(checkedResult).isNotNull();
          try (AutoCloseable checkedVal = (AutoCloseable) checkedResult.result()) {
            assertThat(checkedVal).isNotNull();
            assertThat(checkedVal.getClass()).isEqualTo(enc1.getClass());
            Object decrypted = decrypt(checkedVal);
            assertThat(toBigInteger(decrypted)).isEqualTo(BigInteger.valueOf(expectedILog2));
            assertThat((Boolean) decrypt(checkedResult.check())).isTrue();
          }
        }
      }
      
      if (signed && enc1 instanceof AbstractFheType<?, ?, ?> fheType) {
        try (AutoCloseable absResult = (AutoCloseable) fheType.abs()) {
          assertThat(absResult).isNotNull();
        }
      } else if (!signed && enc1 instanceof AbstractFheType<?, ?, ?> fheType) {
        assertThatThrownBy(fheType::abs)
            .isInstanceOf(UnsupportedOperationException.class);
      }
    }
    
    if (enc1 instanceof FheComparison<?, ?> cmp1 && enc2 instanceof FheComparison<?, ?> cmp2) {
      try (FheBool lt = cmp1.lessThan(castHelper(cmp2))) { assertThat(lt).isNotNull(); }
      try (FheBool le = cmp1.lessThanOrEqualTo(castHelper(cmp2))) { assertThat(le).isNotNull(); }
      try (FheBool gt = cmp1.greaterThan(castHelper(cmp2))) { assertThat(gt).isNotNull(); }
      try (FheBool ge = cmp1.greaterThanOrEqualTo(castHelper(cmp2))) { assertThat(ge).isNotNull(); }
      try (AutoCloseable minVal = (AutoCloseable) cmp1.min(castHelper(cmp2))) { assertThat(minVal).isNotNull(); }
      try (AutoCloseable maxVal = (AutoCloseable) cmp1.max(castHelper(cmp2))) { assertThat(maxVal).isNotNull(); }
      
      try (FheBool ltS = cmp1.lessThanScalar(castHelper(scalarVal2))) { assertThat(ltS).isNotNull(); }
      try (FheBool leS = cmp1.lessThanOrEqualToScalar(castHelper(scalarVal2))) { assertThat(leS).isNotNull(); }
      try (FheBool gtS = cmp1.greaterThanScalar(castHelper(scalarVal2))) { assertThat(gtS).isNotNull(); }
      try (FheBool geS = cmp1.greaterThanOrEqualToScalar(castHelper(scalarVal2))) { assertThat(geS).isNotNull(); }
      try (AutoCloseable minS = (AutoCloseable) cmp1.minScalar(castHelper(scalarVal2))) { assertThat(minS).isNotNull(); }
      try (AutoCloseable maxS = (AutoCloseable) cmp1.maxScalar(castHelper(scalarVal2))) { assertThat(maxS).isNotNull(); }
    }
    
    if (enc1 instanceof FheEquality<?, ?> eq1 && enc2 instanceof FheEquality<?, ?> eq2) {
      try (FheBool eq = eq1.equalTo(castHelper(eq2))) { assertThat(eq).isNotNull(); }
      try (FheBool eqS = eq1.equalToScalar(castHelper(scalarVal2))) { assertThat(eqS).isNotNull(); }
      try (FheBool ne = eq1.notEqualTo(castHelper(eq2))) { assertThat(ne).isNotNull(); }
      try (FheBool neS = eq1.notEqualToScalar(castHelper(scalarVal2))) { assertThat(neS).isNotNull(); }
    }
    
    if (runAll && enc1 instanceof FheBitwise<?, ?> bw1 && enc2 instanceof FheBitwise<?, ?> bw2) {
      try (AutoCloseable shl = (AutoCloseable) bw1.shiftLeft(castHelper(bw2))) { assertThat(shl).isNotNull(); }
      try (AutoCloseable shr = (AutoCloseable) bw1.shiftRight(castHelper(bw2))) { assertThat(shr).isNotNull(); }
      try (AutoCloseable rotL = (AutoCloseable) bw1.rotateLeft(castHelper(bw2))) { assertThat(rotL).isNotNull(); }
      try (AutoCloseable rotR = (AutoCloseable) bw1.rotateRight(castHelper(bw2))) { assertThat(rotR).isNotNull(); }
      
      try (AutoCloseable shlS = (AutoCloseable) bw1.shiftLeftScalar(castHelper(scalarVal2))) { assertThat(shlS).isNotNull(); }
      try (AutoCloseable shrS = (AutoCloseable) bw1.shiftRightScalar(castHelper(scalarVal2))) { assertThat(shrS).isNotNull(); }
      try (AutoCloseable rotLS = (AutoCloseable) bw1.rotateLeftScalar(castHelper(scalarVal2))) { assertThat(rotLS).isNotNull(); }
      try (AutoCloseable rotRS = (AutoCloseable) bw1.rotateRightScalar(castHelper(scalarVal2))) { assertThat(rotRS).isNotNull(); }
      
      try (AutoCloseable lo = (AutoCloseable) bw1.leadingOnes()) { assertThat(lo).isNotNull(); }
      try (AutoCloseable lz = (AutoCloseable) bw1.leadingZeros()) { assertThat(lz).isNotNull(); }
      try (AutoCloseable to = (AutoCloseable) bw1.trailingOnes()) { assertThat(to).isNotNull(); }
      try (AutoCloseable tz = (AutoCloseable) bw1.trailingZeros()) { assertThat(tz).isNotNull(); }
      
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.shiftLeftAssign(castHelper(bw2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.shiftRightAssign(castHelper(bw2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.rotateLeftAssign(castHelper(bw2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.rotateRightAssign(castHelper(bw2));
        }
      }
      
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.shiftLeftScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.shiftRightScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.rotateLeftScalarAssign(castHelper(scalarVal2));
        }
      }
      try (AutoCloseable cloneObj = cloneHelper(enc1)) {
        if (cloneObj instanceof FheBitwise<?, ?> cloneBw) {
          cloneBw.rotateRightScalarAssign(castHelper(scalarVal2));
        }
      }
    }
  }

  private BigInteger toBigInteger(Object val) {
    if (val instanceof BigInteger) {
      return (BigInteger) val;
    }
    if (val instanceof io.github.rdlopes.tfhe.api.values.AbstractValue) {
      return ((io.github.rdlopes.tfhe.api.values.AbstractValue) val).asBigInteger();
    }
    if (val instanceof Number) {
      return BigInteger.valueOf(((Number) val).longValue());
    }
    throw new IllegalArgumentException("Unknown type: " + val.getClass());
  }

  private AutoCloseable cloneHelper(AutoCloseable enc) {
    if (enc instanceof FheCloneable<?> fc) {
      return (AutoCloseable) fc.clone();
    }
    throw new IllegalArgumentException("Not cloneable: " + enc);
  }
}

