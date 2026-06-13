package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.types.FheUint128;
import io.github.rdlopes.tfhe.api.types.FheUint128Array;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.FheUint8Array;
import io.github.rdlopes.tfhe.api.types.FheInt8;
import io.github.rdlopes.tfhe.api.types.FheInt8Array;
import io.github.rdlopes.tfhe.api.types.FheInt128;
import io.github.rdlopes.tfhe.api.types.FheInt128Array;
import io.github.rdlopes.tfhe.api.values.U128;
import io.github.rdlopes.tfhe.api.values.I128;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerStepDefinitions {

  private final TfheTestContext context;

  public IntegerStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @When("I encrypt {int} as a FheUint8 ciphertext")
  public void iEncryptAsAFheUint8Ciphertext(int val) {
    context.uint8s.add(context.track(FheUint8.encrypt((byte) val, context.publicKey)));
  }

  @When("I encrypt {int} as another FheUint8 ciphertext")
  public void iEncryptAsAnotherFheUint8Ciphertext(int val) {
    context.uint8s.add(context.track(FheUint8.encrypt((byte) val, context.publicKey)));
  }

  @Then("the FheUint8 ciphertext decrypted using the ClientKey is {int}")
  public void theFheUint8CiphertextDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 latest = context.uint8s.get(context.uint8s.size() - 1);
    byte decrypted = latest.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the sum of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theSumOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = context.uint8s.get(0);
    FheUint8 rhs = context.uint8s.get(1);
    FheUint8 result = context.track(lhs.add(rhs));
    byte decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the difference of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theDifferenceOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = context.uint8s.get(0);
    FheUint8 rhs = context.uint8s.get(1);
    FheUint8 result = context.track(lhs.subtract(rhs));
    byte decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the product of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theProductOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = context.uint8s.get(0);
    FheUint8 rhs = context.uint8s.get(1);
    FheUint8 result = context.track(lhs.multiply(rhs));
    byte decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  // FheUint8Array steps
  @When("I encrypt the list of bytes {string} as a FheUint8Array")
  public void iEncryptTheListOfBytesAsAFheUint8Array(String bytesStr) {
    List<Byte> values = Arrays.stream(bytesStr.split(","))
                              .map(String::trim)
                              .map(Byte::parseByte)
                              .toList();
    context.uint8Array = context.track(FheUint8Array.encrypt(values, context.keySet.getClientKey()));
  }

  @When("I encrypt an empty list of bytes as a FheUint8Array")
  public void iEncryptAnEmptyListOfBytesAsAFheUint8Array() {
    context.uint8Array = context.track(FheUint8Array.encrypt(List.of(), context.keySet.getClientKey()));
  }

  @When("I encrypt the list of bytes {string} as a FheUint8Array using the PublicKey")
  public void iEncryptTheListOfBytesAsAFheUint8ArrayUsingPublicKey(String bytesStr) {
    List<Byte> values = Arrays.stream(bytesStr.split(","))
                              .map(String::trim)
                              .map(Byte::parseByte)
                              .toList();
    context.uint8Array = context.track(FheUint8Array.encrypt(values, context.publicKey));
  }

  @Then("the sum of the FheUint8Array decrypted using the ClientKey is {int}")
  public void theSumOfTheFheUint8ArrayDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 sum = context.track(context.uint8Array.sum());
    byte decrypted = sum.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  // FheUint128Array steps
  @When("I encrypt the list of integers {string} as a FheUint128Array")
  public void iEncryptTheListOfIntegersAsAFheUint128Array(String intsStr) {
    List<U128> values = Arrays.stream(intsStr.split(","))
                              .map(String::trim)
                              .map(BigInteger::new)
                              .map(U128::valueOf)
                              .toList();
    context.uint128Array = context.track(FheUint128Array.encrypt(values, context.keySet.getClientKey()));
  }

  @When("I encrypt an empty list of integers as a FheUint128Array")
  public void iEncryptAnEmptyListOfIntegersAsAFheUint128Array() {
    context.uint128Array = context.track(FheUint128Array.encrypt(List.of(), context.keySet.getClientKey()));
  }

  @When("I encrypt the list of integers {string} as a FheUint128Array using the PublicKey")
  public void iEncryptTheListOfIntegersAsAFheUint128ArrayUsingPublicKey(String intsStr) {
    List<U128> values = Arrays.stream(intsStr.split(","))
                              .map(String::trim)
                              .map(BigInteger::new)
                              .map(U128::valueOf)
                              .toList();
    context.uint128Array = context.track(FheUint128Array.encrypt(values, context.publicKey));
  }

  @Then("the sum of the FheUint128Array decrypted using the ClientKey is {int}")
  public void theSumOfTheFheUint128ArrayDecryptedUsingTheClientKeyIs(int expected) {
    FheUint128 sum = context.track(context.uint128Array.sum());
    U128 decrypted = sum.decrypt(context.keySet.getClientKey());
    assertThat(decrypted.getValue()).isEqualTo(BigInteger.valueOf(expected));
  }

  // FheInt8Array steps
  @When("I encrypt the list of bytes {string} as a FheInt8Array")
  public void iEncryptTheListOfBytesAsAFheInt8Array(String bytesStr) {
    List<Byte> values = Arrays.stream(bytesStr.split(","))
                              .map(String::trim)
                              .map(Byte::parseByte)
                              .toList();
    context.int8Array = context.track(FheInt8Array.encrypt(values, context.keySet.getClientKey()));
  }

  @Then("the sum of the FheInt8Array decrypted using the ClientKey is {int}")
  public void theSumOfTheFheInt8ArrayDecryptedUsingTheClientKeyIs(int expected) {
    FheInt8 sum = context.track(context.int8Array.sum());
    byte decrypted = sum.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  // FheInt128Array steps
  @When("I encrypt the list of integers {string} as a FheInt128Array")
  public void iEncryptTheListOfIntegersAsAFheInt128Array(String intsStr) {
    List<I128> values = Arrays.stream(intsStr.split(","))
                              .map(String::trim)
                              .map(BigInteger::new)
                              .map(I128::valueOf)
                              .toList();
    context.int128Array = context.track(FheInt128Array.encrypt(values, context.keySet.getClientKey()));
  }

  @Then("the sum of the FheInt128Array decrypted using the ClientKey is {int}")
  public void theSumOfTheFheInt128ArrayDecryptedUsingTheClientKeyIs(int expected) {
    FheInt128 sum = context.track(context.int128Array.sum());
    I128 decrypted = sum.decrypt(context.keySet.getClientKey());
    assertThat(decrypted.getValue()).isEqualTo(BigInteger.valueOf(expected));
  }

  // Element-wise array operations steps
  @When("I encrypt the list of bytes {string} as another FheUint8Array")
  public void iEncryptTheListOfBytesAsAnotherFheUint8Array(String bytesStr) {
    List<Byte> values = Arrays.stream(bytesStr.split(","))
                              .map(String::trim)
                              .map(Byte::parseByte)
                              .toList();
    context.uint8Array2 = context.track(FheUint8Array.encrypt(values, context.keySet.getClientKey()));
  }

  @When("I perform homomorphic element-wise addition of the two FheUint8Array arrays")
  public void iPerformHomomorphicElementWiseAdditionOfTwoFheUint8ArrayArrays() {
    context.uint8Array = context.track(context.uint8Array.add(context.uint8Array2));
  }

  @When("I perform homomorphic element-wise subtraction of the two FheUint8Array arrays")
  public void iPerformHomomorphicElementWiseSubtractionOfTwoFheUint8ArrayArrays() {
    context.uint8Array = context.track(context.uint8Array.subtract(context.uint8Array2));
  }

  @Then("the element-wise array result decrypted using the ClientKey is {string}")
  public void theElementWiseArrayResultDecryptedUsingClientKeyIs(String expectedStr) {
    List<Byte> expected = Arrays.stream(expectedStr.split(","))
                                .map(String::trim)
                                .map(Byte::parseByte)
                                .toList();
    java.util.List<FheUint8> elements = context.uint8Array.getElements();
    assertThat(elements).hasSize(expected.size());
    for (int i = 0; i < elements.size(); i++) {
      byte decrypted = elements.get(i).decrypt(context.keySet.getClientKey());
      assertThat(decrypted).isEqualTo(expected.get(i));
    }
  }
}
