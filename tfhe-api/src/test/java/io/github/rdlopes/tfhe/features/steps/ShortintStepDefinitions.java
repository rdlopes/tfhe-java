package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.shortint.ShortintCiphertext;
import io.github.rdlopes.tfhe.api.shortint.ShortintClientKey;
import io.github.rdlopes.tfhe.api.shortint.ShortintCompressedCiphertext;
import io.github.rdlopes.tfhe.api.shortint.ShortintPublicKey;
import io.github.rdlopes.tfhe.api.shortint.ShortintServerKey;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortintStepDefinitions {

  private final TfheTestContext context;

  public ShortintStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @Given("a shortint configuration is prepared with custom parameters")
  public void aShortintConfigurationIsPreparedWithCustomParameters() {
    context.shortintClientKey = context.track(ShortintClientKey.generate(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128));
    context.shortintServerKey = context.track(ShortintServerKey.generate(context.shortintClientKey));
    context.shortintPublicKey = context.track(ShortintPublicKey.generate(context.shortintClientKey));
  }

  @Then("the shortint client, server, and public keys are initialized successfully")
  public void theShortintClientServerAndPublicKeysAreInitializedSuccessfully() {
    assertThat(context.shortintClientKey).isNotNull();
    assertThat(context.shortintClientKey.getValue()).isNotNull();
    assertThat(context.shortintServerKey).isNotNull();
    assertThat(context.shortintPublicKey).isNotNull();
  }

  @When("I encrypt {long} as a shortint ciphertext")
  public void iEncryptAsAShortintCiphertext(long val) {
    context.shortintCiphertexts.add(context.track(context.shortintClientKey.encrypt(val)));
  }

  @When("I encrypt {long} as another shortint ciphertext")
  public void iEncryptAsAnotherShortintCiphertext(long val) {
    context.shortintCiphertexts.add(context.track(context.shortintClientKey.encrypt(val)));
  }

  @Then("the shortint ciphertext decrypted using the client key is {long}")
  @Then("the decompressed shortint ciphertext decrypted using the client key is {long}")
  public void theShortintCiphertextDecryptedUsingTheClientKeyIs(long expected) {
    ShortintCiphertext latest = context.shortintCiphertexts.get(context.shortintCiphertexts.size() - 1);
    long decrypted = context.shortintClientKey.decrypt(latest);
    assertThat(decrypted).isEqualTo(expected);
  }

  @When("I encrypt {long} as a shortint ciphertext using the public key")
  public void iEncryptAsAShortintCiphertextUsingPublicKey(long val) {
    context.shortintCiphertexts.add(context.track(context.shortintPublicKey.encrypt(val)));
  }

  @Then("serializing and deserializing the client key, server key, public key, and ciphertext yields identical decryption results")
  public void serializingAndDeserializingKeysAndCiphertextYieldsIdenticalResults() {
    long originalVal = 1L;
    ShortintCiphertext ct = context.shortintCiphertexts.get(context.shortintCiphertexts.size() - 1);

    // Serialize and deserialize client key
    try (DynamicBuffer clientKeyBuf = context.shortintClientKey.serialize()) {
      ShortintClientKey deserializedClientKey = context.track(ShortintClientKey.deserialize(clientKeyBuf));
      assertThat(deserializedClientKey.decrypt(ct)).isEqualTo(originalVal);

      // Serialize and deserialize server key
      try (DynamicBuffer serverKeyBuf = context.shortintServerKey.serialize()) {
        ShortintServerKey deserializedServerKey = context.track(ShortintServerKey.deserialize(serverKeyBuf));
        assertThat(deserializedServerKey).isNotNull();
      }

      // Serialize and deserialize public key
      try (DynamicBuffer publicKeyBuf = context.shortintPublicKey.serialize()) {
        ShortintPublicKey deserializedPublicKey = context.track(ShortintPublicKey.deserialize(publicKeyBuf));
        ShortintCiphertext ctPub = context.track(deserializedPublicKey.encrypt(2L));
        assertThat(deserializedClientKey.decrypt(ctPub)).isEqualTo(2L);
      }
    }

    // Serialize and deserialize ciphertext
    try (DynamicBuffer ctBuf = ct.serialize()) {
      ShortintCiphertext deserializedCt = context.track(ShortintCiphertext.deserialize(ctBuf));
      assertThat(context.shortintClientKey.decrypt(deserializedCt)).isEqualTo(originalVal);
    }
  }

  @When("I encrypt {long} as a compressed shortint ciphertext")
  public void iEncryptAsACompressedShortintCiphertext(long val) {
    context.shortintCompressedCiphertexts.add(context.track(context.shortintClientKey.encryptCompressed(val)));
  }

  @When("I decompress the compressed shortint ciphertext")
  public void iDecompressCompressedShortintCiphertext() {
    ShortintCompressedCiphertext latest = context.shortintCompressedCiphertexts.get(context.shortintCompressedCiphertexts.size() - 1);
    context.shortintCiphertexts.add(context.track(latest.decompress()));
  }
  
  @Then("the smart sum of these ciphertexts is {long}")
  public void theSmartSumOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartAdd(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.smartAddAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the smart difference of these ciphertexts is {long}")
  public void theSmartDifferenceOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartSub(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.smartSubAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the smart product of these ciphertexts is {long}")
  public void theSmartProductOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartMul(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.smartMulAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the smart division of these ciphertexts is {long}")
  public void theSmartDivisionOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartDiv(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.smartDivAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the smart negation of the second ciphertext is {long}")
  public void theSmartNegationOfSecondCiphertextIs(long expected) {
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct2.smartNeg(context.shortintServerKey));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct2Copy = context.track(ct2.clone());
    ct2Copy.smartNegAssign(context.shortintServerKey);
    assertThat(context.shortintClientKey.decrypt(ct2Copy)).isEqualTo(expected);
  }

  @Then("the smart scalar sum with {int} is {long}")
  public void theSmartScalarSumWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.smartScalarAdd(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.smartScalarAddAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the smart scalar difference with {int} is {long}")
  public void theSmartScalarDifferenceWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.smartScalarSub(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.smartScalarSubAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the smart scalar product with {int} is {long}")
  public void theSmartScalarProductWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.smartScalarMul(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.smartScalarMulAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the unchecked sum of these ciphertexts is {long}")
  public void theUncheckedSumOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.uncheckedAdd(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.uncheckedAddAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the unchecked difference of these ciphertexts is {long}")
  public void theUncheckedDifferenceOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.uncheckedSub(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.uncheckedSubAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the unchecked product of these ciphertexts is {long}")
  public void theUncheckedProductOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.uncheckedMul(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct1Copy = context.track(ct1.clone());
    ct1Copy.uncheckedMulAssign(context.shortintServerKey, ct2);
    assertThat(context.shortintClientKey.decrypt(ct1Copy)).isEqualTo(expected);
  }

  @Then("the unchecked negation of the second ciphertext is {long}")
  public void theUncheckedNegationOfSecondCiphertextIs(long expected) {
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct2.uncheckedNeg(context.shortintServerKey));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ct2Copy = context.track(ct2.clone());
    ct2Copy.uncheckedNegAssign(context.shortintServerKey);
    assertThat(context.shortintClientKey.decrypt(ct2Copy)).isEqualTo(expected);
  }

  @Then("the unchecked scalar sum with {int} is {long}")
  public void theUncheckedScalarSumWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.uncheckedScalarAdd(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.uncheckedScalarAddAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the unchecked scalar difference with {int} is {long}")
  public void theUncheckedScalarDifferenceWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.uncheckedScalarSub(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.uncheckedScalarSubAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the unchecked scalar product with {int} is {long}")
  public void theUncheckedScalarProductWithIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.uncheckedScalarMul(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.uncheckedScalarMulAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the unchecked scalar division by {int} is {long}")
  public void theUncheckedScalarDivisionByIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.uncheckedScalarDiv(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.uncheckedScalarDivAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the unchecked scalar modulo by {int} is {long}")
  public void theUncheckedScalarModuloByIs(int scalar, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(ct.uncheckedScalarMod(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    ctCopy.uncheckedScalarModAssign(context.shortintServerKey, (byte) scalar);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @Then("the smart bitwise AND of these ciphertexts is {long}")
  public void theSmartBitwiseAndOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartBitAnd(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);
  }

  @Then("the smart bitwise OR of these ciphertexts is {long}")
  public void theSmartBitwiseOrOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartBitOr(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);
  }

  @Then("the smart bitwise XOR of these ciphertexts is {long}")
  public void theSmartBitwiseXorOfTheseCiphertextsIs(long expected) {
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(ct1.smartBitXor(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);
  }

  @Then("shifting the first ciphertext left by {int} yields {long}")
  public void shiftingFirstCiphertextLeftByYields(int shift, long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);
    ShortintCiphertext result = context.track(ct.smartScalarLeftShift(context.shortintServerKey, (byte) shift));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);
    if (context.shortintCiphertexts.size() > 2) {
      context.shortintCiphertexts.set(2, result);
    } else {
      context.shortintCiphertexts.add(result);
    }
  }

  @Then("shifting the result right by {int} yields {long}")
  public void shiftingResultRightByYields(int shift, long expected) {
    ShortintCiphertext leftShiftResult = context.shortintCiphertexts.get(context.shortintCiphertexts.size() - 1);
    ShortintCiphertext result = context.track(leftShiftResult.smartScalarRightShift(context.shortintServerKey, (byte) shift));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);
  }

  @Then("checking if the first is less than the second yields {word}")
  public void checkingIfFirstIsLessThanSecondYields(String expectedStr) {
    boolean expected = Boolean.parseBoolean(expectedStr);
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext less = context.track(ct1.smartLess(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(less)).isEqualTo(expected ? 1L : 0L);
  }

  @Then("checking if the first is less than {int} yields {word}")
  public void checkingIfFirstIsLessThanScalarYields(int scalar, String expectedStr) {
    boolean expected = Boolean.parseBoolean(expectedStr);
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext less = context.track(ct.smartScalarLess(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(less)).isEqualTo(expected ? 1L : 0L);
  }

  @Then("checking if they are equal yields {word}")
  public void checkingIfTheyAreEqualYields(String expectedStr) {
    boolean expected = Boolean.parseBoolean(expectedStr);
    ShortintCiphertext ct1 = context.shortintCiphertexts.get(0);
    ShortintCiphertext ct2 = context.shortintCiphertexts.get(1);

    ShortintCiphertext equal = context.track(ct1.smartEqual(context.shortintServerKey, ct2));
    assertThat(context.shortintClientKey.decrypt(equal)).isEqualTo(expected ? 1L : 0L);
  }

  @Then("checking if the first is equal to {int} yields {word}")
  public void checkingIfFirstIsEqualScalarYields(int scalar, String expectedStr) {
    boolean expected = Boolean.parseBoolean(expectedStr);
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext equal = context.track(ct.smartScalarEqual(context.shortintServerKey, (byte) scalar));
    assertThat(context.shortintClientKey.decrypt(equal)).isEqualTo(expected ? 1L : 0L);
  }

  @When("I generate a univariate lookup table for f\\(x\\) = \\(x + 1\\) % 4")
  public void iGenerateUnivariateLookupTable() {
    context.shortintLut = context.track(context.shortintServerKey.generateLookupTable(x -> (x + 1) % 4));
  }

  @Then("evaluating the lookup table on the ciphertext yields {long}")
  public void evaluatingLookupTableOnCiphertextYields(long expected) {
    ShortintCiphertext ct = context.shortintCiphertexts.get(0);

    ShortintCiphertext result = context.track(context.shortintServerKey.programmableBootstrap(ct, context.shortintLut));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctCopy = context.track(ct.clone());
    context.shortintServerKey.programmableBootstrapAssign(ctCopy, context.shortintLut);
    assertThat(context.shortintClientKey.decrypt(ctCopy)).isEqualTo(expected);
  }

  @When("I generate a bivariate lookup table for f\\(x, y\\) = \\(x + y\\) % 4")
  public void iGenerateBivariateLookupTable() {
    context.shortintBivariateLut = context.track(context.shortintServerKey.generateBivariateLookupTable((x, y) -> (x + y) % 4));
  }

  @Then("evaluating the bivariate lookup table on the ciphertexts yields {long}")
  public void evaluatingBivariateLookupTableOnCiphertextsYields(long expected) {
    ShortintCiphertext ctLeft = context.shortintCiphertexts.get(0);
    ShortintCiphertext ctRight = context.shortintCiphertexts.get(1);

    ShortintCiphertext result = context.track(context.shortintServerKey.bivariateProgrammableBootstrap(ctLeft, ctRight, context.shortintBivariateLut));
    assertThat(context.shortintClientKey.decrypt(result)).isEqualTo(expected);

    ShortintCiphertext ctLeftCopy = context.track(ctLeft.clone());
    context.shortintServerKey.bivariateProgrammableBootstrapAssign(ctLeftCopy, ctRight, context.shortintBivariateLut);
    assertThat(context.shortintClientKey.decrypt(ctLeftCopy)).isEqualTo(expected);
  }

  @When("I clone the latest shortint ciphertext")
  public void iCloneLatestShortintCiphertext() {
    ShortintCiphertext latest = context.shortintCiphertexts.get(context.shortintCiphertexts.size() - 1);
    context.shortintCiphertexts.add(context.track(latest.clone()));
  }

  @When("I clone the latest compressed shortint ciphertext")
  public void iCloneLatestCompressedShortintCiphertext() {
    ShortintCompressedCiphertext latest = context.shortintCompressedCiphertexts.get(context.shortintCompressedCiphertexts.size() - 1);
    context.shortintCompressedCiphertexts.add(context.track(latest.clone()));
  }

  @When("I decompress the cloned compressed shortint ciphertext")
  public void iDecompressClonedCompressedShortintCiphertext() {
    iDecompressCompressedShortintCiphertext();
  }

  @Then("the cloned shortint ciphertext decrypted using the client key is {long}")
  public void theClonedShortintCiphertextDecryptedUsingTheClientKeyIs(long expected) {
    ShortintCiphertext latestCloned = context.shortintCiphertexts.get(context.shortintCiphertexts.size() - 1);
    long decrypted = context.shortintClientKey.decrypt(latestCloned);
    assertThat(decrypted).isEqualTo(expected);
  }
}
