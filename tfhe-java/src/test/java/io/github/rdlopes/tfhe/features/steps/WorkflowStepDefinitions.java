package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.extended.CompressedFheUint32;
import io.github.rdlopes.tfhe.api.types.extended.FheUint32;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowStepDefinitions {

  private final TfheTestContext context;

  // Client-side state
  private ClientKey clientKey;
  private ServerKey serverKey;               // CPU key — used as conformant key for deserialization

  private FheUint32 clientCiphertext;
  private DynamicBuffer serializedCiphertext;
  private DynamicBuffer serializedCompressedKey;  // We serialize the CompressedServerKey, not the CPU ServerKey

  // Server-side state
  private CompressedServerKey serverCompressedKey; // received from client
  private ServerKey serverDecompressionKey;        // CPU key for ciphertext deserialization
  private FheUint32 serverCiphertext1;
  private FheUint32 serverCiphertext2;
  private FheUint32 serverResult;
  private DynamicBuffer serializedResult;

  private FheUint32 clientResultCiphertext;
  private int finalResult;

  // Compression workflow state
  private CompressedFheUint32 compressedCiphertext;
  private DynamicBuffer serializedCompressed;
  private CompressedFheUint32 serverCompressed;
  private FheUint32 decompressedCiphertext;

  // Trivial workflow state
  private FheUint32 trivialCiphertext;

  public WorkflowStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @Given("a standard configuration is prepared")
  public void aStandardConfigurationIsPrepared() {
    try (ConfigBuilder builder = new ConfigBuilder()) {
      assertThat(builder).isNotNull();
    }
  }

  @When("the keys are generated")
  public void theKeysAreGenerated() {
    context.keySet = KeySet.builder()
                           .enableCompression(io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build();
    clientKey = context.keySet.getClientKey();
    serverKey = context.keySet.getServerKey();                 // CPU key for conformant deserialization
    context.keySet.getCompressedServerKey().use();             // activates CPU + GPU transparently
  }

  @Then("the ClientKey and CompressedServerKey are initialized and ready")
  public void theClientKeyAndCompressedServerKeyAreInitializedAndReady() {
    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getValue()).isNotNull();
    assertThat(context.keySet.getCompressedServerKey()).isNotNull();
    assertThat(context.keySet.getCompressedServerKey().getValue()).isNotNull();
  }

  @When("I encrypt {int} as a FheUint32 ciphertext on the client side")
  public void iEncryptAsAFheUint32CiphertextOnTheClientSide(int value) {
    clientCiphertext = FheUint32.encrypt(value, clientKey);
    context.track(clientCiphertext);
  }

  @When("I encrypt {int} directly into a compressed FheUint32 ciphertext on the client side")
  public void iEncryptDirectlyIntoACompressedFheUint32CiphertextOnTheClientSide(int value) {
    compressedCiphertext = CompressedFheUint32.encrypt(value, clientKey);
    context.track(compressedCiphertext);
  }

  // The client serializes the ciphertext and the CompressedServerKey (not the CPU ServerKey)
  // so that the server can activate GPU support if needed.
  @When("I serialize the client data and server key for transmission")
  public void iSerializeTheClientDataAndServerKeyForTransmission() {
    serializedCiphertext = clientCiphertext.serialize();
    serializedCompressedKey = context.keySet.getCompressedServerKey().serialize();
    context.track(serializedCiphertext);
    context.track(serializedCompressedKey);
  }

  // The server deserializes the CompressedServerKey, activates it (CPU + optional GPU),
  // then decompresses a CPU ServerKey for use as a conformant key in ciphertext deserialization.
  @When("I deserialize them on the server side")
  public void iDeserializeThemOnTheServerSide() {
    serverCompressedKey = CompressedServerKey.deserialize(serializedCompressedKey);
    serverCompressedKey.use();                                          // activates CPU + optional GPU
    serverDecompressionKey = serverCompressedKey.decompress();          // conformant key for deserialization

    serverCiphertext1 = FheUint32.deserialize(serializedCiphertext, serverDecompressionKey);
    serverCiphertext2 = FheUint32.deserialize(serializedCiphertext, serverDecompressionKey);

    context.track(serverCompressedKey);
    context.track(serverDecompressionKey);
    context.track(serverCiphertext1);
    context.track(serverCiphertext2);
  }

  @When("I perform homomorphic addition of the value with itself on the server side")
  public void iPerformHomomorphicAdditionOfTheValueWithItselfOnTheServerSide() {
    // Server key is already active from the deserialization step
    serverResult = serverCiphertext1.add(serverCiphertext2);
    context.track(serverResult);
  }

  @When("I serialize the computation result")
  public void iSerializeTheComputationResult() {
    serializedResult = serverResult.serialize();
    context.track(serializedResult);
  }

  @When("I deserialize and decrypt the result on the client side")
  public void iDeserializeAndDecryptTheResultOnTheClientSide() {
    clientResultCiphertext = FheUint32.deserialize(serializedResult, serverKey);
    context.track(clientResultCiphertext);
    finalResult = clientResultCiphertext.decrypt(clientKey);
  }

  @Then("the final decrypted plaintext result is {int}")
  public void theFinalDecryptedPlaintextResultIs(int expected) {
    assertThat(finalResult).isEqualTo(expected);
  }

  // Compression steps
  @When("I compress the FheUint32 ciphertext")
  public void iCompressTheFheUint32Ciphertext() {
    compressedCiphertext = clientCiphertext.compress();
    context.track(compressedCiphertext);
  }

  @When("I serialize the compressed ciphertext")
  public void iSerializeTheCompressedCiphertext() {
    serializedCompressed = compressedCiphertext.serialize();
    context.track(serializedCompressed);
  }

  @When("I deserialize and decompress the ciphertext on the server side")
  public void iDeserializeAndDecompressTheCiphertextOnTheServerSide() {
    serverCompressed = CompressedFheUint32.deserialize(serializedCompressed, serverKey);
    context.track(serverCompressed);
    decompressedCiphertext = serverCompressed.decompress();
    context.track(decompressedCiphertext);
  }

  @When("I perform homomorphic addition of the decompressed value with {int} on the server side")
  public void iPerformHomomorphicAdditionWithScalarOnTheServerSide(int scalar) {
    serverKey.use();
    serverResult = decompressedCiphertext.addScalar(scalar);
    context.track(serverResult);
  }

  @Then("the decrypted result is {int}")
  public void theDecryptedResultIs(int expected) {
    int decrypted = serverResult.decrypt(clientKey);
    assertThat(decrypted).isEqualTo(expected);
  }

  // Trivial ciphertext steps
  @When("I create a trivial FheUint32 ciphertext from public value {int}")
  public void iCreateATrivialFheUint32CiphertextFromPublicValue(int val) {
    trivialCiphertext = FheUint32.encrypt(val);
    context.track(trivialCiphertext);
  }

  @When("I perform homomorphic addition of the encrypted value and the trivial ciphertext")
  public void iPerformHomomorphicAdditionOfEncryptedValueAndTrivialCiphertext() {
    serverKey.use();
    serverResult = clientCiphertext.add(trivialCiphertext);
    context.track(serverResult);
  }
}
