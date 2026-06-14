package io.github.rdlopes.tfhe.utils;

import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.lang.foreign.MemorySegment;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class FheRegistry {
  private static final Map<ClassPair, FheOps.UnaryOp> CAST_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, Supplier<?>> FACTORIES = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.ListGetOp> COMPRESSED_GET_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.ListGetOp> COMPACT_GET_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.TryDecryptPrimitiveOp> TRY_DECRYPT_PRIMITIVE_OPS = new ConcurrentHashMap<>();
  private static final Map<Class<?>, FheOps.TryDecryptWideOp> TRY_DECRYPT_WIDE_OPS = new ConcurrentHashMap<>();

  private FheRegistry() {
  }

  public static void registerFactory(Class<?> clazz, Supplier<?> factory) {
    FACTORIES.put(clazz, factory);
  }

  @SuppressWarnings("unchecked")
  public static <T> Supplier<T> getFactory(Class<T> clazz) {
    try {
      Class.forName(clazz.getName(), true, clazz.getClassLoader());
    } catch (ClassNotFoundException ignored) {
    }
    return (Supplier<T>) FACTORIES.get(clazz);
  }

  public static FheOps.UnaryOp getCastOp(Class<?> sourceClass, Class<?> targetClass) {
    return CAST_OPS.computeIfAbsent(new ClassPair(sourceClass, targetClass), pair -> {
      String srcName = FheUtils.nativeType(sourceClass.getSimpleName());
      String tgtName = FheUtils.nativeType(targetClass.getSimpleName());
      String methodName = srcName + "_cast_into_" + tgtName;

      try {
        Method method = TfheHeader.class.getMethod(methodName, MemorySegment.class, MemorySegment.class);
        method.setAccessible(true);
        return (self, result) -> {
          try {
            return (int) method.invoke(null, self, result);
          } catch (Exception e) {
            throw new RuntimeException("Error invoking native cast " + methodName, e);
          }
        };
      } catch (NoSuchMethodException e) {
        return null;
      }
    });
  }

  public static FheOps.ListGetOp getCompressedGetOp(Class<?> targetClass) {
    return COMPRESSED_GET_OPS.computeIfAbsent(targetClass, clazz -> {
      String name = FheUtils.nativeType(clazz.getSimpleName());
      String methodName = "compressed_ciphertext_list_get_" + name;

      try {
        Method method = TfheHeader.class.getMethod(methodName, MemorySegment.class, long.class, MemorySegment.class);
        method.setAccessible(true);
        return (list, index, result) -> {
          try {
            return (int) method.invoke(null, list, index, result);
          } catch (Exception e) {
            throw new RuntimeException("Error invoking native get " + methodName, e);
          }
        };
      } catch (NoSuchMethodException e) {
        return null;
      }
    });
  }

  public static FheOps.ListGetOp getCompactGetOp(Class<?> targetClass) {
    return COMPACT_GET_OPS.computeIfAbsent(targetClass, clazz -> {
      String name = FheUtils.nativeType(clazz.getSimpleName());
      String methodName = "compact_ciphertext_list_expander_get_" + name;

      try {
        Method method = TfheHeader.class.getMethod(methodName, MemorySegment.class, long.class, MemorySegment.class);
        method.setAccessible(true);
        return (list, index, result) -> {
          try {
            return (int) method.invoke(null, list, index, result);
          } catch (Exception e) {
            throw new RuntimeException("Error invoking native get " + methodName, e);
          }
        };
      } catch (NoSuchMethodException e) {
        return null;
      }
    });
  }

  public static FheOps.TryDecryptPrimitiveOp getTryDecryptPrimitiveOp(Class<?> clazz) {
    return TRY_DECRYPT_PRIMITIVE_OPS.computeIfAbsent(clazz, c -> {
      String name = FheUtils.nativeType(c.getSimpleName());
      String methodName = name + "_try_decrypt_trivial";
      try {
        Method method = TfheHeader.class.getMethod(methodName, MemorySegment.class, MemorySegment.class);
        method.setAccessible(true);
        return (encrypted, outPtr) -> {
          try {
            return (int) method.invoke(null, encrypted, outPtr);
          } catch (Exception e) {
            throw new RuntimeException("Error invoking native try decrypt " + methodName, e);
          }
        };
      } catch (NoSuchMethodException e) {
        return null;
      }
    });
  }

  public static FheOps.TryDecryptWideOp getTryDecryptWideOp(Class<?> clazz) {
    return TRY_DECRYPT_WIDE_OPS.computeIfAbsent(clazz, c -> {
      String name = FheUtils.nativeType(c.getSimpleName());
      String methodName = name + "_try_decrypt_trivial";
      try {
        Method method = TfheHeader.class.getMethod(methodName, MemorySegment.class, MemorySegment.class);
        method.setAccessible(true);
        return (encrypted, out) -> {
          try {
            return (int) method.invoke(null, encrypted, out);
          } catch (Exception e) {
            throw new RuntimeException("Error invoking native try decrypt " + methodName, e);
          }
        };
      } catch (NoSuchMethodException e) {
        return null;
      }
    });
  }

  private record ClassPair(Class<?> first, Class<?> second) {
  }
}
