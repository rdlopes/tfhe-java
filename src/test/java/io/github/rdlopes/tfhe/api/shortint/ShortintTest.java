package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class ShortintTest {

  private CustomParameters parameters;
  private ShortintClientKey clientKey;
  private ShortintServerKey serverKey;
  private ShortintPublicKey publicKey;

  @BeforeEach
  void setUp() {
    parameters = CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
    clientKey = ShortintClientKey.generate(parameters);
    serverKey = ShortintServerKey.generate(clientKey);
    publicKey = ShortintPublicKey.generate(clientKey);
  }

  @Test
  void testClientKeyGenAndDestruction() {
    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getValue()).isNotNull();
  }

  @Test
  void testEncryptDecrypt() {
    long originalValue = 2L;
    ShortintCiphertext ciphertext = clientKey.encrypt(originalValue);
    assertThat(ciphertext).isNotNull();

    long decryptedValue = clientKey.decrypt(ciphertext);
    assertThat(decryptedValue).isEqualTo(originalValue);
  }

  @Test
  void testPublicKeyEncrypt() {
    long originalValue = 3L;
    ShortintCiphertext ciphertext = publicKey.encrypt(originalValue);
    assertThat(ciphertext).isNotNull();

    long decryptedValue = clientKey.decrypt(ciphertext);
    assertThat(decryptedValue).isEqualTo(originalValue);
  }

  @Test
  void testSerialization() {
    long val = 1L;
    ShortintCiphertext ct = clientKey.encrypt(val);

    // Serialize and deserialize client key
    DynamicBuffer clientKeyBuf = clientKey.serialize();
    ShortintClientKey deserializedClientKey = ShortintClientKey.deserialize(clientKeyBuf);
    assertThat(deserializedClientKey.decrypt(ct)).isEqualTo(val);

    // Serialize and deserialize server key
    DynamicBuffer serverKeyBuf = serverKey.serialize();
    ShortintServerKey deserializedServerKey = ShortintServerKey.deserialize(serverKeyBuf);
    assertThat(deserializedServerKey).isNotNull();

    // Serialize and deserialize public key
    DynamicBuffer publicKeyBuf = publicKey.serialize();
    ShortintPublicKey deserializedPublicKey = ShortintPublicKey.deserialize(publicKeyBuf);
    ShortintCiphertext ctPub = deserializedPublicKey.encrypt(2L);
    assertThat(clientKey.decrypt(ctPub)).isEqualTo(2L);

    // Serialize and deserialize ciphertext
    DynamicBuffer ctBuf = ct.serialize();
    ShortintCiphertext deserializedCt = ShortintCiphertext.deserialize(ctBuf);
    assertThat(clientKey.decrypt(deserializedCt)).isEqualTo(val);
  }

  @Test
  void testCompressedCiphertext() {
    long val = 2L;
    ShortintCompressedCiphertext compressed = clientKey.encryptCompressed(val);
    assertThat(compressed).isNotNull();

    ShortintCiphertext decompressed = compressed.decompress();
    assertThat(decompressed).isNotNull();

    long decrypted = clientKey.decrypt(decompressed);
    assertThat(decrypted).isEqualTo(val);
  }

  @Test
  void testSmartArithmetic() {
    ShortintCiphertext ct1 = clientKey.encrypt(2L);
    ShortintCiphertext ct2 = clientKey.encrypt(1L);

    // smartAdd
    ShortintCiphertext addResult = ct1.smartAdd(serverKey, ct2);
    assertThat(clientKey.decrypt(addResult)).isEqualTo(3L);

    // smartAddAssign
    ShortintCiphertext ct1Copy = ct1.clone();
    ct1Copy.smartAddAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1Copy)).isEqualTo(3L);

    // smartSub
    ShortintCiphertext subResult = ct1.smartSub(serverKey, ct2);
    assertThat(clientKey.decrypt(subResult)).isEqualTo(1L);

    // smartSubAssign
    ShortintCiphertext ct1SubCopy = ct1.clone();
    ct1SubCopy.smartSubAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1SubCopy)).isEqualTo(1L);

    // smartMul
    ShortintCiphertext mulResult = ct1.smartMul(serverKey, ct2);
    assertThat(clientKey.decrypt(mulResult)).isEqualTo(2L);

    // smartMulAssign
    ShortintCiphertext ct1MulCopy = ct1.clone();
    ct1MulCopy.smartMulAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1MulCopy)).isEqualTo(2L);

    // smartDiv
    ShortintCiphertext divResult = ct1.smartDiv(serverKey, ct2);
    assertThat(clientKey.decrypt(divResult)).isEqualTo(2L);

    // smartDivAssign
    ShortintCiphertext ct1DivCopy = ct1.clone();
    ct1DivCopy.smartDivAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1DivCopy)).isEqualTo(2L);

    // smartNeg
    ShortintCiphertext negResult = ct2.smartNeg(serverKey);
    // Since message modulus is 4, neg(1) = 3
    assertThat(clientKey.decrypt(negResult)).isEqualTo(3L);

    // smartNegAssign
    ShortintCiphertext ct2NegCopy = ct2.clone();
    ct2NegCopy.smartNegAssign(serverKey);
    assertThat(clientKey.decrypt(ct2NegCopy)).isEqualTo(3L);
  }

  @Test
  void testSmartScalarArithmetic() {
    ShortintCiphertext ct = clientKey.encrypt(2L);

    // smartScalarAdd
    ShortintCiphertext addResult = ct.smartScalarAdd(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(addResult)).isEqualTo(3L);

    // smartScalarAddAssign
    ShortintCiphertext ctAddCopy = ct.clone();
    ctAddCopy.smartScalarAddAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctAddCopy)).isEqualTo(3L);

    // smartScalarSub
    ShortintCiphertext subResult = ct.smartScalarSub(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(subResult)).isEqualTo(1L);

    // smartScalarSubAssign
    ShortintCiphertext ctSubCopy = ct.clone();
    ctSubCopy.smartScalarSubAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctSubCopy)).isEqualTo(1L);

    // smartScalarMul
    ShortintCiphertext mulResult = ct.smartScalarMul(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(mulResult)).isEqualTo(2L);

    // smartScalarMulAssign
    ShortintCiphertext ctMulCopy = ct.clone();
    ctMulCopy.smartScalarMulAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctMulCopy)).isEqualTo(2L);
  }

  @Test
  void testUncheckedArithmetic() {
    ShortintCiphertext ct1 = clientKey.encrypt(2L);
    ShortintCiphertext ct2 = clientKey.encrypt(1L);

    // uncheckedAdd
    ShortintCiphertext addResult = ct1.uncheckedAdd(serverKey, ct2);
    assertThat(clientKey.decrypt(addResult)).isEqualTo(3L);

    // uncheckedAddAssign
    ShortintCiphertext ct1Copy = ct1.clone();
    ct1Copy.uncheckedAddAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1Copy)).isEqualTo(3L);

    // uncheckedSub
    ShortintCiphertext subResult = ct1.uncheckedSub(serverKey, ct2);
    assertThat(clientKey.decrypt(subResult)).isEqualTo(1L);

    // uncheckedSubAssign
    ShortintCiphertext ct1SubCopy = ct1.clone();
    ct1SubCopy.uncheckedSubAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1SubCopy)).isEqualTo(1L);

    // uncheckedMul
    ShortintCiphertext mulResult = ct1.uncheckedMul(serverKey, ct2);
    assertThat(clientKey.decrypt(mulResult)).isEqualTo(2L);

    // uncheckedMulAssign
    ShortintCiphertext ct1MulCopy = ct1.clone();
    ct1MulCopy.uncheckedMulAssign(serverKey, ct2);
    assertThat(clientKey.decrypt(ct1MulCopy)).isEqualTo(2L);

    // uncheckedNeg
    ShortintCiphertext negResult = ct2.uncheckedNeg(serverKey);
    assertThat(clientKey.decrypt(negResult)).isEqualTo(3L);

    // uncheckedNegAssign
    ShortintCiphertext ct2NegCopy = ct2.clone();
    ct2NegCopy.uncheckedNegAssign(serverKey);
    assertThat(clientKey.decrypt(ct2NegCopy)).isEqualTo(3L);
  }

  @Test
  void testUncheckedScalarArithmetic() {
    ShortintCiphertext ct = clientKey.encrypt(2L);

    // uncheckedScalarAdd
    ShortintCiphertext addResult = ct.uncheckedScalarAdd(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(addResult)).isEqualTo(3L);

    // uncheckedScalarAddAssign
    ShortintCiphertext ctAddCopy = ct.clone();
    ctAddCopy.uncheckedScalarAddAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctAddCopy)).isEqualTo(3L);

    // uncheckedScalarSub
    ShortintCiphertext subResult = ct.uncheckedScalarSub(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(subResult)).isEqualTo(1L);

    // uncheckedScalarSubAssign
    ShortintCiphertext ctSubCopy = ct.clone();
    ctSubCopy.uncheckedScalarSubAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctSubCopy)).isEqualTo(1L);

    // uncheckedScalarMul
    ShortintCiphertext mulResult = ct.uncheckedScalarMul(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(mulResult)).isEqualTo(2L);

    // uncheckedScalarMulAssign
    ShortintCiphertext ctMulCopy = ct.clone();
    ctMulCopy.uncheckedScalarMulAssign(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(ctMulCopy)).isEqualTo(2L);

    // uncheckedScalarDiv
    ShortintCiphertext divResult = ct.uncheckedScalarDiv(serverKey, (byte) 2);
    assertThat(clientKey.decrypt(divResult)).isEqualTo(1L);

    // uncheckedScalarDivAssign
    ShortintCiphertext ctDivCopy = ct.clone();
    ctDivCopy.uncheckedScalarDivAssign(serverKey, (byte) 2);
    assertThat(clientKey.decrypt(ctDivCopy)).isEqualTo(1L);

    // uncheckedScalarMod
    ShortintCiphertext modResult = ct.uncheckedScalarMod(serverKey, (byte) 3);
    assertThat(clientKey.decrypt(modResult)).isEqualTo(2L);

    // uncheckedScalarModAssign
    ShortintCiphertext ctModCopy = ct.clone();
    ctModCopy.uncheckedScalarModAssign(serverKey, (byte) 3);
    assertThat(clientKey.decrypt(ctModCopy)).isEqualTo(2L);
  }

  @Test
  void testBitwiseOperations() {
    ShortintCiphertext ct1 = clientKey.encrypt(2L); // 10 binary
    ShortintCiphertext ct2 = clientKey.encrypt(3L); // 11 binary

    // smartBitAnd
    ShortintCiphertext andResult = ct1.smartBitAnd(serverKey, ct2);
    assertThat(clientKey.decrypt(andResult)).isEqualTo(2L);

    // smartBitOr
    ShortintCiphertext orResult = ct1.smartBitOr(serverKey, ct2);
    assertThat(clientKey.decrypt(orResult)).isEqualTo(3L);

    // smartBitXor
    ShortintCiphertext xorResult = ct1.smartBitXor(serverKey, ct2);
    assertThat(clientKey.decrypt(xorResult)).isEqualTo(1L);
  }

  @Test
  void testShiftOperations() {
    ShortintCiphertext ct = clientKey.encrypt(1L);

    // smartScalarLeftShift
    ShortintCiphertext leftShift = ct.smartScalarLeftShift(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(leftShift)).isEqualTo(2L);

    // smartScalarRightShift
    ShortintCiphertext rightShift = leftShift.smartScalarRightShift(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(rightShift)).isEqualTo(1L);
  }

  @Test
  void testComparisons() {
    ShortintCiphertext ct1 = clientKey.encrypt(2L);
    ShortintCiphertext ct2 = clientKey.encrypt(3L);

    // smartLess
    ShortintCiphertext less = ct1.smartLess(serverKey, ct2);
    assertThat(clientKey.decrypt(less)).isEqualTo(1L); // true

    // smartScalarLess
    ShortintCiphertext scalarLess = ct1.smartScalarLess(serverKey, (byte) 1);
    assertThat(clientKey.decrypt(scalarLess)).isEqualTo(0L); // false

    // smartEqual
    ShortintCiphertext equal = ct1.smartEqual(serverKey, ct2);
    assertThat(clientKey.decrypt(equal)).isEqualTo(0L); // false

    // smartScalarEqual
    ShortintCiphertext scalarEqual = ct1.smartScalarEqual(serverKey, (byte) 2);
    assertThat(clientKey.decrypt(scalarEqual)).isEqualTo(1L); // true
  }

  @Test
  void testUnivariatePBS() {
    ShortintCiphertext ct = clientKey.encrypt(2L);

    // f(x) = (x + 1) % 4
    LongUnaryOperator operator = x -> (x + 1) % 4;
    ShortintPbsLookupTable lut = serverKey.generateLookupTable(operator);
    assertThat(lut).isNotNull();

    // programmableBootstrap
    ShortintCiphertext result = serverKey.programmableBootstrap(ct, lut);
    assertThat(clientKey.decrypt(result)).isEqualTo(3L);

    // programmableBootstrapAssign
    ShortintCiphertext ctCopy = ct.clone();
    serverKey.programmableBootstrapAssign(ctCopy, lut);
    assertThat(clientKey.decrypt(ctCopy)).isEqualTo(3L);
  }

  @Test
  void testBivariatePBS() {
    ShortintCiphertext ctLeft = clientKey.encrypt(2L);
    ShortintCiphertext ctRight = clientKey.encrypt(1L);

    // f(x, y) = (x + y) % 4
    LongBinaryOperator operator = (x, y) -> (x + y) % 4;
    ShortintBivariatePbsLookupTable lut = serverKey.generateBivariateLookupTable(operator);
    assertThat(lut).isNotNull();

    // bivariateProgrammableBootstrap
    ShortintCiphertext result = serverKey.bivariateProgrammableBootstrap(ctLeft, ctRight, lut);
    assertThat(clientKey.decrypt(result)).isEqualTo(3L);

    // bivariateProgrammableBootstrapAssign
    ShortintCiphertext ctLeftCopy = ctLeft.clone();
    serverKey.bivariateProgrammableBootstrapAssign(ctLeftCopy, ctRight, lut);
    assertThat(clientKey.decrypt(ctLeftCopy)).isEqualTo(3L);
  }
}
