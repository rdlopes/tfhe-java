package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheUint8;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanStepDefinitions {

  private final TfheTestContext context;

  public BooleanStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @When("I encrypt {word} as a FheBool ciphertext")
  public void iEncryptAsAFheBoolCiphertext(String val) {
    context.bools.add(context.track(FheBool.encrypt(Boolean.parseBoolean(val), context.publicKey)));
  }

  @When("I encrypt {word} as another FheBool ciphertext")
  public void iEncryptAsAnotherFheBoolCiphertext(String val) {
    context.bools.add(context.track(FheBool.encrypt(Boolean.parseBoolean(val), context.publicKey)));
  }

  @Then("the FheBool ciphertext decrypted using the ClientKey is {word}")
  public void theFheBoolCiphertextDecryptedUsingTheClientKeyIs(String expected) {
    FheBool latest = context.bools.get(context.bools.size() - 1);
    boolean decrypted = latest.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise AND of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseANDOfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = context.bools.get(0);
    FheBool rhs = context.bools.get(1);
    FheBool result = context.track(lhs.bitAnd(rhs));
    boolean decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise OR of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseOROfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = context.bools.get(0);
    FheBool rhs = context.bools.get(1);
    FheBool result = context.track(lhs.bitOr(rhs));
    boolean decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise XOR of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseXOROfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = context.bools.get(0);
    FheBool rhs = context.bools.get(1);
    FheBool result = context.track(lhs.bitXor(rhs));
    boolean decrypted = result.decrypt(context.keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @When("I cast the FheBool ciphertext into a FheUint8 ciphertext")
  public void iCastTheFheBoolCiphertextIntoAFheUint8Ciphertext() {
    FheBool latest = context.bools.get(context.bools.size() - 1);
    context.uint8s.add(context.track(latest.castInto(FheUint8.class)));
  }
}
