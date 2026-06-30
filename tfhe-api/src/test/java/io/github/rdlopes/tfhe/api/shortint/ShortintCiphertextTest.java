package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShortintCiphertextTest {

  private ShortintClientKey clientKey;
  private ShortintServerKey serverKey;
  private ShortintPublicKey publicKey;

  @BeforeEach
  void setUp() {
    clientKey = ShortintClientKey.generate(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    serverKey = ShortintServerKey.generate(clientKey);
    publicKey = ShortintPublicKey.generate(clientKey);
  }

  @AfterEach
  void tearDown() {
    if (clientKey != null) {
      clientKey.destroy();
    }
    if (serverKey != null) {
      serverKey.destroy();
    }
    if (publicKey != null) {
      publicKey.destroy();
    }
  }

  @Test
  void testZ_Zero() {
    // Zero: encrypt 0, decrypt it, test addition with 0
    try (ShortintCiphertext ctZero = clientKey.encrypt(0L)) {
      assertThat(clientKey.decrypt(ctZero)).isZero();

      try (ShortintCiphertext added = ctZero.smartAdd(serverKey, ctZero)) {
        assertThat(clientKey.decrypt(added)).isZero();
      }
    }
  }

  @Test
  void testO_One() {
    // One: encrypt 1, decrypt it, test addition with 1
    try (ShortintCiphertext ctOne = clientKey.encrypt(1L)) {
      assertThat(clientKey.decrypt(ctOne)).isOne();

      try (ShortintCiphertext ctZero = clientKey.encrypt(0L);
           ShortintCiphertext added = ctZero.smartAdd(serverKey, ctOne)) {
        assertThat(clientKey.decrypt(added)).isOne();
      }
    }
  }

  @Test
  void testM_ManyAndSimpleScenarios() {
    // Many/Simple: test math and comparison operations
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      
      // Smart Add, Sub, Mul, Div, Neg
      try (ShortintCiphertext sum = ct2.smartAdd(serverKey, ct1)) {
        assertThat(clientKey.decrypt(sum)).isEqualTo(3L);
      }
      try (ShortintCiphertext diff = ct2.smartSub(serverKey, ct1)) {
        assertThat(clientKey.decrypt(diff)).isOne();
      }
      try (ShortintCiphertext prod = ct2.smartMul(serverKey, ct1)) {
        assertThat(clientKey.decrypt(prod)).isEqualTo(2L);
      }
      try (ShortintCiphertext div = ct2.smartDiv(serverKey, ct1)) {
        assertThat(clientKey.decrypt(div)).isEqualTo(2L);
      }
      try (ShortintCiphertext neg = ct1.smartNeg(serverKey)) {
        assertThat(clientKey.decrypt(neg)).isEqualTo(3L); // mod 4 arithmetic: -1 = 3
      }
    }
  }

  @Test
  void testM_SmartScalarArithmetic() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L)) {
      // Smart Scalar Arithmetic
      try (ShortintCiphertext scalarSum = ct2.smartScalarAdd(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarSum)).isEqualTo(3L);
      }
      try (ShortintCiphertext scalarDiff = ct2.smartScalarSub(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarDiff)).isOne();
      }
      try (ShortintCiphertext scalarProd = ct2.smartScalarMul(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarProd)).isEqualTo(2L);
      }
    }
  }

  @Test
  void testM_UncheckedArithmetic() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      // Unchecked Arithmetic
      try (ShortintCiphertext sumUnchecked = ct2.uncheckedAdd(serverKey, ct1)) {
        assertThat(clientKey.decrypt(sumUnchecked)).isEqualTo(3L);
      }
      try (ShortintCiphertext diffUnchecked = ct2.uncheckedSub(serverKey, ct1)) {
        assertThat(clientKey.decrypt(diffUnchecked)).isOne();
      }
      try (ShortintCiphertext prodUnchecked = ct2.uncheckedMul(serverKey, ct1)) {
        assertThat(clientKey.decrypt(prodUnchecked)).isEqualTo(2L);
      }
      try (ShortintCiphertext negUnchecked = ct1.uncheckedNeg(serverKey)) {
        assertThat(clientKey.decrypt(negUnchecked)).isEqualTo(3L);
      }
    }
  }

  @Test
  void testM_UncheckedScalarArithmetic() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L)) {
      // Unchecked Scalar Arithmetic
      try (ShortintCiphertext scalarSumUnchecked = ct2.uncheckedScalarAdd(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarSumUnchecked)).isEqualTo(3L);
      }
      try (ShortintCiphertext scalarDiffUnchecked = ct2.uncheckedScalarSub(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarDiffUnchecked)).isOne();
      }
      try (ShortintCiphertext scalarProdUnchecked = ct2.uncheckedScalarMul(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(scalarProdUnchecked)).isEqualTo(2L);
      }
      try (ShortintCiphertext scalarDivUnchecked = ct2.uncheckedScalarDiv(serverKey, (byte) 2)) {
        assertThat(clientKey.decrypt(scalarDivUnchecked)).isOne();
      }
      try (ShortintCiphertext scalarModUnchecked = ct2.uncheckedScalarMod(serverKey, (byte) 3)) {
        assertThat(clientKey.decrypt(scalarModUnchecked)).isEqualTo(2L);
      }
    }
  }

  @Test
  void testM_BitwiseAndShifts() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      // Bitwise
      try (ShortintCiphertext ct3 = clientKey.encrypt(3L)) {
        try (ShortintCiphertext bitAnd = ct2.smartBitAnd(serverKey, ct3)) {
          assertThat(clientKey.decrypt(bitAnd)).isEqualTo(2L);
        }
        try (ShortintCiphertext bitOr = ct2.smartBitOr(serverKey, ct3)) {
          assertThat(clientKey.decrypt(bitOr)).isEqualTo(3L);
        }
        try (ShortintCiphertext bitXor = ct2.smartBitXor(serverKey, ct3)) {
          assertThat(clientKey.decrypt(bitXor)).isOne();
        }
      }

      // Shifts
      try (ShortintCiphertext shiftLeft = ct1.smartScalarLeftShift(serverKey, (byte) 1)) {
        assertThat(clientKey.decrypt(shiftLeft)).isEqualTo(2L);
        try (ShortintCiphertext shiftRight = shiftLeft.smartScalarRightShift(serverKey, (byte) 1)) {
          assertThat(clientKey.decrypt(shiftRight)).isOne();
        }
      }
    }
  }

  @Test
  void testM_ComparisonsAndPbs() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      // Comparisons
      try (ShortintCiphertext cmpLess = ct2.smartLess(serverKey, ct1)) {
        assertThat(clientKey.decrypt(cmpLess)).isZero(); // false
      }
      try (ShortintCiphertext cmpLessScalar = ct2.smartScalarLess(serverKey, (byte) 3)) {
        assertThat(clientKey.decrypt(cmpLessScalar)).isOne(); // true
      }
      try (ShortintCiphertext cmpEq = ct2.smartEqual(serverKey, ct1)) {
        assertThat(clientKey.decrypt(cmpEq)).isZero(); // false
      }
      try (ShortintCiphertext cmpEqScalar = ct2.smartScalarEqual(serverKey, (byte) 2)) {
        assertThat(clientKey.decrypt(cmpEqScalar)).isOne(); // true
      }

      // Unary Lookup Table (PBS)
      try (ShortintPbsLookupTable lut = serverKey.generateLookupTable(x -> (x + 1) % 4)) {
        assertThat(lut).isNotNull();
        try (ShortintCiphertext result = serverKey.programmableBootstrap(ct2, lut)) {
          assertThat(clientKey.decrypt(result)).isEqualTo(3L);
        }
      }

      // Bivariate Lookup Table (PBS)
      try (ShortintBivariatePbsLookupTable bivariateLut = serverKey.generateBivariateLookupTable((x, y) -> (x + y) % 4)) {
        assertThat(bivariateLut).isNotNull();
        try (ShortintCiphertext result = serverKey.bivariateProgrammableBootstrap(ct2, ct1, bivariateLut)) {
          assertThat(clientKey.decrypt(result)).isEqualTo(3L);
        }
      }
    }
  }

  @Test
  void testB_BoundaryAndExceptions() {
    // Boundary: Maximum message capacity check
    // Param has message bits = 2, meaning max value is 3. 4 is out of bounds (wrapped by modulo 4).
    try (ShortintCiphertext ct4 = clientKey.encrypt(4L)) {
      assertThat(clientKey.decrypt(ct4)).isZero(); // 4 % 4 = 0
    }
  }

  @Test
  void testI_InterfaceSerializationRoundtrip() {
    // Interface: Serialize and deserialize roundtrip
    try (ShortintCiphertext ct = clientKey.encrypt(1L)) {
      // 1. Ciphertext Serialization
      try (DynamicBuffer ctBuf = ct.serialize()) {
        try (ShortintCiphertext deserializedCt = ShortintCiphertext.deserialize(ctBuf)) {
          assertThat(clientKey.decrypt(deserializedCt)).isOne();
        }
      }

      // 2. Compressed Ciphertext Serialization
      try (ShortintCompressedCiphertext compressed = clientKey.encryptCompressed(1L)) {
        assertThat(compressed).isNotNull();
        try (ShortintCiphertext decompressed = compressed.decompress()) {
          assertThat(clientKey.decrypt(decompressed)).isOne();
        }
      }

      // 3. Client Key Serialization
      try (DynamicBuffer clientKeyBuf = clientKey.serialize()) {
        try (ShortintClientKey deserializedClientKey = ShortintClientKey.deserialize(clientKeyBuf)) {
          assertThat(deserializedClientKey.decrypt(ct)).isOne();
        }
      }

      // 4. Server Key Serialization
      try (DynamicBuffer serverKeyBuf = serverKey.serialize()) {
        try (ShortintServerKey deserializedServerKey = ShortintServerKey.deserialize(serverKeyBuf)) {
          assertThat(deserializedServerKey).isNotNull();
        }
      }

      // 5. Public Key Serialization
      try (DynamicBuffer publicKeyBuf = publicKey.serialize()) {
        try (ShortintPublicKey deserializedPublicKey = ShortintPublicKey.deserialize(publicKeyBuf)) {
          try (ShortintCiphertext ctPub = deserializedPublicKey.encrypt(2L)) {
            assertThat(clientKey.decrypt(ctPub)).isEqualTo(2L);
          }
        }
      }
    }
  }

  @Test
  void testE_ExceptionalBehavior() {
    // Exceptional: deserializing from bad buffer
    try (DynamicBuffer emptyBuffer = new DynamicBuffer()) {
      assertThatThrownBy(() -> ShortintCiphertext.deserialize(emptyBuffer))
          .isInstanceOf(RuntimeException.class);
    }
  }

  @Test
  void testAdditionalShortintStructures() {
    // 1. KeyPair generation
    ShortintClientKey.KeyPair keyPair = ShortintClientKey.generateKeysWithParameters(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128);
    assertThat(keyPair).isNotNull();
    assertThat(keyPair.clientKey).isNotNull();
    assertThat(keyPair.serverKey).isNotNull();

    // 2. Compressed PublicKey lifecycle
    try (ShortintCompressedPublicKey compPubKey = ShortintCompressedPublicKey.generate(clientKey)) {
      assertThat(compPubKey).isNotNull();
      try (DynamicBuffer buf = compPubKey.serialize()) {
        try (ShortintCompressedPublicKey decompPubKey = ShortintCompressedPublicKey.deserialize(buf)) {
          assertThat(decompPubKey).isNotNull();
          try (ShortintPublicKey pubKey = decompPubKey.decompress()) {
            assertThat(pubKey).isNotNull();
          }
        }
      }
      // Encrypt via compressed public key
      try (ShortintCiphertext ct = compPubKey.encrypt(1L)) {
        assertThat(clientKey.decrypt(ct)).isOne();
      }
    }

    // 3. Compressed ServerKey lifecycle
    try (ShortintCompressedServerKey compServerKey = ShortintCompressedServerKey.generate(clientKey)) {
      assertThat(compServerKey).isNotNull();
      try (DynamicBuffer buf = compServerKey.serialize()) {
        try (ShortintCompressedServerKey decompServerKey = ShortintCompressedServerKey.deserialize(buf)) {
          assertThat(decompServerKey).isNotNull();
          try (ShortintServerKey serverKeyDec = decompServerKey.decompress()) {
            assertThat(serverKeyDec).isNotNull();
          }
        }
      }
    }
  }

  @Test
  void testAssignOperations() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      
      // smartAddAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartAddAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartAddAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }

      // smartSubAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartSubAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartSubAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // smartMulAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartMulAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartMulAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }

      // smartDivAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartDivAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartDivAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }

      // smartNegAssign
      try (ShortintCiphertext ct = ct1.clone()) {
        ct.smartNegAssign(serverKey);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct1.clone()) {
        serverKey.smartNegAssign(ct);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
    }
  }

  @Test
  void testAssignScalarOperations() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L)) {
      // smartScalarAddAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartScalarAddAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartScalarAddAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }

      // smartScalarSubAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartScalarSubAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartScalarSubAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // smartScalarMulAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.smartScalarMulAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.smartScalarMulAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
    }
  }

  @Test
  void testAssignUncheckedOperations() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      // uncheckedAddAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedAddAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedAddAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }

      // uncheckedSubAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedSubAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedSubAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // uncheckedMulAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedMulAssign(serverKey, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedMulAssign(ct, ct1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }

      // uncheckedNegAssign
      try (ShortintCiphertext ct = ct1.clone()) {
        ct.uncheckedNegAssign(serverKey);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct1.clone()) {
        serverKey.uncheckedNegAssign(ct);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
    }
  }

  @Test
  void testAssignUncheckedScalarOperations() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L)) {
      // uncheckedScalarAddAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedScalarAddAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedScalarAddAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
      }

      // uncheckedScalarSubAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedScalarSubAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedScalarSubAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // uncheckedScalarMulAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedScalarMulAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedScalarMulAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }

      // uncheckedScalarDivAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedScalarDivAssign(serverKey, (byte) 2);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedScalarDivAssign(ct, (byte) 2);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // uncheckedScalarModAssign
      try (ShortintCiphertext ct = ct2.clone()) {
        ct.uncheckedScalarModAssign(serverKey, (byte) 3);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
      try (ShortintCiphertext ct = ct2.clone()) {
        serverKey.uncheckedScalarModAssign(ct, (byte) 3);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
      }
    }
  }

  @Test
  void testAssignBitwiseOperations() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L)) {
      // smartBitAndAssign, smartBitOrAssign, smartBitXorAssign
      try (ShortintCiphertext ct3 = clientKey.encrypt(3L)) {
        try (ShortintCiphertext ct = ct2.clone()) {
          ct.smartBitAndAssign(serverKey, ct3);
          assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
        }
        try (ShortintCiphertext ct = ct2.clone()) {
          serverKey.smartBitAndAssign(ct, ct3);
          assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
        }

        try (ShortintCiphertext ct = ct2.clone()) {
          ct.smartBitOrAssign(serverKey, ct3);
          assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
        }
        try (ShortintCiphertext ct = ct2.clone()) {
          serverKey.smartBitOrAssign(ct, ct3);
          assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
        }

        try (ShortintCiphertext ct = ct2.clone()) {
          ct.smartBitXorAssign(serverKey, ct3);
          assertThat(clientKey.decrypt(ct)).isOne();
        }
        try (ShortintCiphertext ct = ct2.clone()) {
          serverKey.smartBitXorAssign(ct, ct3);
          assertThat(clientKey.decrypt(ct)).isOne();
        }
      }
    }
  }

  @Test
  void testAssignShiftsAndPbs() {
    try (ShortintCiphertext ct2 = clientKey.encrypt(2L);
         ShortintCiphertext ct1 = clientKey.encrypt(1L)) {
      // Shifts assign
      try (ShortintCiphertext ct = ct1.clone()) {
        ct.smartScalarLeftShiftAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
        ct.smartScalarRightShiftAssign(serverKey, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }
      try (ShortintCiphertext ct = ct1.clone()) {
        serverKey.smartScalarLeftShiftAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isEqualTo(2L);
        serverKey.smartScalarRightShiftAssign(ct, (byte) 1);
        assertThat(clientKey.decrypt(ct)).isOne();
      }

      // PBS assign
      try (ShortintPbsLookupTable lut = serverKey.generateLookupTable(x -> (x + 1) % 4)) {
        try (ShortintCiphertext ct = ct2.clone()) {
          serverKey.programmableBootstrapAssign(ct, lut);
          assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
        }
      }

      // Bivariate PBS assign
      try (ShortintBivariatePbsLookupTable bivariateLut = serverKey.generateBivariateLookupTable((x, y) -> (x + y) % 4)) {
        try (ShortintCiphertext ct = ct2.clone()) {
          serverKey.bivariateProgrammableBootstrapAssign(ct, ct1, bivariateLut);
          assertThat(clientKey.decrypt(ct)).isEqualTo(3L);
        }
      }
    }
  }

  @Test
  void testCiphertextCloning() {
    try (ShortintCiphertext original = clientKey.encrypt(2L)) {
      assertThat(original).isNotNull();
      assertThat(clientKey.decrypt(original)).isEqualTo(2L);

      try (ShortintCiphertext cloned = original.clone()) {
        assertThat(cloned).isNotNull();
        // The cloned one should have the same decrypted value
        assertThat(clientKey.decrypt(cloned)).isEqualTo(2L);

        // They must point to different memory segment addresses (independent handles)
        assertThat(cloned.getValue().address()).isNotEqualTo(original.getValue().address());

        // Modifying the clone (e.g. setting its degree) must not affect the original
        long originalDegree = original.getDegree();
        cloned.setDegree(originalDegree + 1);
        assertThat(cloned.getDegree()).isEqualTo(originalDegree + 1);
        assertThat(original.getDegree()).isEqualTo(originalDegree);
      }
    }
  }

  @Test
  void testCompressedCiphertextCloning() {
    try (ShortintCompressedCiphertext originalComp = clientKey.encryptCompressed(3L)) {
      assertThat(originalComp).isNotNull();

      try (ShortintCompressedCiphertext clonedComp = originalComp.clone()) {
        assertThat(clonedComp).isNotNull();

        // They must point to different memory segment addresses
        assertThat(clonedComp.getValue().address()).isNotEqualTo(originalComp.getValue().address());

        // Decompressing the cloned one should yield 3
        try (ShortintCiphertext decompressed = clonedComp.decompress()) {
          assertThat(decompressed).isNotNull();
          assertThat(clientKey.decrypt(decompressed)).isEqualTo(3L);
        }
      }
    }
  }
}

