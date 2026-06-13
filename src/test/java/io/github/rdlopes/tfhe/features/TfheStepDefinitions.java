package io.github.rdlopes.tfhe.features;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.TfheThreadingContext;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters;
import io.github.rdlopes.tfhe.api.keys.Config;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListBuilder;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListExpander;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.ProvenCompactCiphertextList;
import io.github.rdlopes.tfhe.api.types.ZkComputeLoad;
import io.github.rdlopes.tfhe.ffm.NativeAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;
import static org.assertj.core.api.Assertions.assertThat;

public class TfheStepDefinitions {

  private final List<NativeAddress> nativeObjects = new ArrayList<>();
  private final List<FheUint8> uint8s = new ArrayList<>();
  private final List<FheBool> bools = new ArrayList<>();

  private KeySet keySet;
  private PublicKey publicKey;
  private CompactPkeCrs crs;
  private CompactPublicKey compactPublicKey;

  // Voting state
  private record Ballot(byte[] serializedData, byte[] metadata) {}
  private final List<Ballot> ballots = new ArrayList<>();

  public static class ElectionTallies {
    public FheUint8 alice;
    public FheUint8 bob;
    public FheUint8 charlie;
    public FheUint8 invalid;

    public ElectionTallies(PublicKey publicKey) {
      this.alice = FheUint8.encrypt((byte) 0, publicKey);
      this.bob = FheUint8.encrypt((byte) 0, publicKey);
      this.charlie = FheUint8.encrypt((byte) 0, publicKey);
      this.invalid = FheUint8.encrypt((byte) 0, publicKey);
    }
  }

  private ElectionTallies tallies;

  private <T extends NativeAddress> T track(T obj) {
    nativeObjects.add(obj);
    return obj;
  }

  @After
  public void tearDown() {
    for (NativeAddress obj : nativeObjects) {
      try {
        obj.destroy();
      } catch (Exception ignored) {
      }
    }
    if (tallies != null) {
      try {
        tallies.alice.destroy();
        tallies.bob.destroy();
        tallies.charlie.destroy();
        tallies.invalid.destroy();
      } catch (Exception ignored) {
      }
    }
  }

  @Given("a ClientKey and a PublicKey are initialized")
  public void aClientKeyAndAPublicKeyAreInitialized() {
    keySet = KeySet.builder().build();
    keySet.getServerKey().use();
    publicKey = new PublicKey(keySet.getClientKey());
  }

  @When("I encrypt {int} as a FheUint8 ciphertext")
  public void iEncryptAsAFheUint8Ciphertext(int val) {
    uint8s.add(track(FheUint8.encrypt((byte) val, publicKey)));
  }

  @When("I encrypt {int} as another FheUint8 ciphertext")
  public void iEncryptAsAnotherFheUint8Ciphertext(int val) {
    uint8s.add(track(FheUint8.encrypt((byte) val, publicKey)));
  }

  @Then("the FheUint8 ciphertext decrypted using the ClientKey is {int}")
  public void theFheUint8CiphertextDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 latest = uint8s.get(uint8s.size() - 1);
    byte decrypted = latest.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the sum of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theSumOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = uint8s.get(0);
    FheUint8 rhs = uint8s.get(1);
    FheUint8 result = track(lhs.add(rhs));
    byte decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the difference of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theDifferenceOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = uint8s.get(0);
    FheUint8 rhs = uint8s.get(1);
    FheUint8 result = track(lhs.subtract(rhs));
    byte decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @Then("the product of these FheUint8 ciphertexts decrypted using the ClientKey is {int}")
  public void theProductOfTheseFheUint8CiphertextsDecryptedUsingTheClientKeyIs(int expected) {
    FheUint8 lhs = uint8s.get(0);
    FheUint8 rhs = uint8s.get(1);
    FheUint8 result = track(lhs.multiply(rhs));
    byte decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo((byte) expected);
  }

  @When("I encrypt {word} as a FheBool ciphertext")
  public void iEncryptAsAFheBoolCiphertext(String val) {
    bools.add(track(FheBool.encrypt(Boolean.parseBoolean(val), publicKey)));
  }

  @When("I encrypt {word} as another FheBool ciphertext")
  public void iEncryptAsAnotherFheBoolCiphertext(String val) {
    bools.add(track(FheBool.encrypt(Boolean.parseBoolean(val), publicKey)));
  }

  @Then("the FheBool ciphertext decrypted using the ClientKey is {word}")
  public void theFheBoolCiphertextDecryptedUsingTheClientKeyIs(String expected) {
    FheBool latest = bools.get(bools.size() - 1);
    boolean decrypted = latest.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise AND of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseANDOfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = bools.get(0);
    FheBool rhs = bools.get(1);
    FheBool result = track(lhs.bitAnd(rhs));
    boolean decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise OR of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseOROfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = bools.get(0);
    FheBool rhs = bools.get(1);
    FheBool result = track(lhs.bitOr(rhs));
    boolean decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @Then("the bitwise XOR of these FheBool ciphertexts decrypted using the ClientKey is {word}")
  public void theBitwiseXOROfTheseFheBoolCiphertextsDecryptedUsingTheClientKeyIs(String expected) {
    FheBool lhs = bools.get(0);
    FheBool rhs = bools.get(1);
    FheBool result = track(lhs.bitXor(rhs));
    boolean decrypted = result.decrypt(keySet.getClientKey());
    assertThat(decrypted).isEqualTo(Boolean.parseBoolean(expected));
  }

  @When("I cast the FheBool ciphertext into a FheUint8 ciphertext")
  public void iCastTheFheBoolCiphertextIntoAFheUint8Ciphertext() {
    FheBool latest = bools.get(bools.size() - 1);
    uint8s.add(track(latest.castIntoFheUint8()));
  }

  // Voting Simulation steps
  @Given("a privacy-preserving election setup is prepared")
  public void aPrivacyPreservingElectionSetupIsPrepared() {
    ConfigBuilder crsBuilder = new ConfigBuilder();
    execute(() -> use_dedicated_compact_public_key_parameters(
        crsBuilder.getAddress(),
        CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
    ));
    Config crsConfig = crsBuilder.build();
    crs = track(new CompactPkeCrs(crsConfig, 8));

    keySet = KeySet.builder()
                   .useCompactKeyEncryptionParameters(CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                   .build();
    keySet.getServerKey().use();

    compactPublicKey = track(new CompactPublicKey(keySet.getClientKey()));
    publicKey = new PublicKey(keySet.getClientKey());
    tallies = new ElectionTallies(publicKey);
  }

  @When("voter {string} votes for candidate {int} with ZK proof")
  public void voterVotesForCandidateWithZKProof(String voterName, int candidateIndex) {
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);
    for (int i = 0; i < 3; i++) {
      builder.push(i == candidateIndex);
    }
    byte[] metadata = voterName.getBytes();
    try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF);
         DynamicBuffer serialized = provenList.serialize()) {
      ballots.add(new Ballot(serialized.toByteArray(), metadata));
    }
  }

  @When("voter {string} submits an invalid blank ballot with ZK proof")
  public void voterSubmitsAnInvalidBlankBallotWithZKProof(String voterName) {
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);
    for (int i = 0; i < 3; i++) {
      builder.push(false);
    }
    byte[] metadata = voterName.getBytes();
    try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF);
         DynamicBuffer serialized = provenList.serialize()) {
      ballots.add(new Ballot(serialized.toByteArray(), metadata));
    }
  }

  @When("voter {string} submits an invalid double vote ballot with ZK proof")
  public void voterSubmitsAnInvalidDoubleVoteBallotWithZKProof(String voterName) {
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(compactPublicKey);
    builder.push(true); // Alice
    builder.push(true); // Bob
    builder.push(false); // Charlie
    byte[] metadata = voterName.getBytes();
    try (ProvenCompactCiphertextList provenList = builder.buildWithProofPacked(crs, metadata, ZkComputeLoad.PROOF);
         DynamicBuffer serialized = provenList.serialize()) {
      ballots.add(new Ballot(serialized.toByteArray(), metadata));
    }
  }

  @Then("the tally server processes and aggregates the ballots inside a Rayon context")
  public void theTallyServerProcessesAndAggregatesTheBallotsInsideARayonContext() {
    FheUint8 zero = track(FheUint8.encrypt((byte) 0, publicKey));
    FheUint8 one = track(FheUint8.encrypt((byte) 1, publicKey));

    try (TfheThreadingContext context = new TfheThreadingContext(2)) {
      context.setServerKey(keySet.getServerKey());
      context.run(() -> {
        for (Ballot ballot : ballots) {
          try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(ballot.serializedData());
               ProvenCompactCiphertextList provenList = ProvenCompactCiphertextList.deserializeConformant(buffer, compactPublicKey, crs);
               CompactCiphertextListExpander expander = provenList.verifyAndExpand(crs, compactPublicKey, ballot.metadata())) {

            FheBool choiceA = expander.getFheBool(0);
            FheBool choiceB = expander.getFheBool(1);
            FheBool choiceC = expander.getFheBool(2);

            FheUint8 choiceAUint8 = choiceA.castIntoFheUint8();
            FheUint8 choiceBUint8 = choiceB.castIntoFheUint8();
            FheUint8 choiceCUint8 = choiceC.castIntoFheUint8();

            FheUint8 sumAB = choiceAUint8.add(choiceBUint8);
            FheUint8 sum = sumAB.add(choiceCUint8);

            FheBool isValid = sum.equalToScalar((byte) 1);

            FheUint8 contribA = FheUint8.ifThenElse(isValid, choiceAUint8, zero);
            FheUint8 contribB = FheUint8.ifThenElse(isValid, choiceBUint8, zero);
            FheUint8 contribC = FheUint8.ifThenElse(isValid, choiceCUint8, zero);

            tallies.alice.addAssign(contribA);
            tallies.bob.addAssign(contribB);
            tallies.charlie.addAssign(contribC);

            FheUint8 isValidUint8 = isValid.castIntoFheUint8();
            FheUint8 isInvalidUint8 = one.subtract(isValidUint8);
            tallies.invalid.addAssign(isInvalidUint8);

            choiceA.destroy();
            choiceB.destroy();
            choiceC.destroy();
            choiceAUint8.destroy();
            choiceBUint8.destroy();
            choiceCUint8.destroy();
            sumAB.destroy();
            sum.destroy();
            isValid.destroy();
            contribA.destroy();
            contribB.destroy();
            contribC.destroy();
            isValidUint8.destroy();
            isInvalidUint8.destroy();
          }
        }
      });
    }
  }

  @Then("the decryption authority decrypts and verifies the results:")
  public void theDecryptionAuthorityDecryptsAndVerifiesTheResults(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap(String.class, String.class);

    byte finalAlice = tallies.alice.decrypt(keySet.getClientKey());
    byte finalBob = tallies.bob.decrypt(keySet.getClientKey());
    byte finalCharlie = tallies.charlie.decrypt(keySet.getClientKey());
    byte finalInvalid = tallies.invalid.decrypt(keySet.getClientKey());

    assertThat(finalAlice).isEqualTo(Byte.parseByte(map.get("candidate_0")));
    assertThat(finalBob).isEqualTo(Byte.parseByte(map.get("candidate_1")));
    assertThat(finalCharlie).isEqualTo(Byte.parseByte(map.get("candidate_2")));
    assertThat(finalInvalid).isEqualTo(Byte.parseByte(map.get("invalid")));
  }
}
