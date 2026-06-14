package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters;
import io.github.rdlopes.tfhe.api.keys.Config;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextList;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListBuilder;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListExpander;
import io.github.rdlopes.tfhe.api.types.CompressedCiphertextList;
import io.github.rdlopes.tfhe.api.types.CompressedCiphertextListBuilder;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheInt32;
import io.github.rdlopes.tfhe.api.types.FheInt64;
import io.github.rdlopes.tfhe.api.types.FheInt8;
import io.github.rdlopes.tfhe.api.types.FheTypes;
import io.github.rdlopes.tfhe.api.types.FheUint256;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.ProvenCompactCiphertextList;
import io.github.rdlopes.tfhe.api.types.ZkComputeLoad;
import io.github.rdlopes.tfhe.api.values.U256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.rdlopes.tfhe.api.keys.CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.api.keys.CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;
import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;
import static org.assertj.core.api.Assertions.assertThat;

public class CompactListStepDefinitions {

  private final TfheTestContext context;
  private final List<Object> originalInputs = new ArrayList<>();
  private byte[] compactListSerializedBytes;
  private byte[] compressedListSerializedBytes;
  private byte[] provenListSerializedBytes;

  public CompactListStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @Given("a compressed-enabled ClientKey and ServerKey are initialized")
  public void aCompressedEnabledClientKeyAndServerKeyAreInitialized() {
    context.keySet = KeySet.builder()
                           .useCustomParameters(SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .enableCompression(SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
                           .build();
    context.keySet.getServerKey().use();
  }

  @Given("a compact PKE CRS is created")
  public void aCompactPkeCrsIsCreated() {
    ConfigBuilder crsBuilder = new ConfigBuilder();
    execute(() -> use_dedicated_compact_public_key_parameters(
        crsBuilder.getAddress(),
        CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.address()
    ));
    Config crsConfig = crsBuilder.build();
    context.crs = context.track(new CompactPkeCrs(crsConfig, 8));
  }

  @Then("serializing and deserializing the CRS yields a valid CRS")
  public void serializingAndDeserializingTheCrsYieldsAValidCrs() {
    assertThat(context.crs).isNotNull();
    try (DynamicBuffer serialized = context.crs.serialize()) {
      byte[] bytes = serialized.toByteArray();
      assertThat(bytes).isNotEmpty();

      try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(bytes);
           CompactPkeCrs deserialized = CompactPkeCrs.deserialize(buffer)) {
        assertThat(deserialized).isNotNull();
      }
    }
  }

  @When("I build a packed compact ciphertext list with:")
  public void iBuildAPackedCompactCiphertextListWith(DataTable dataTable) {
    context.compactPublicKey = context.track(new CompactPublicKey(context.keySet.getClientKey()));
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(context.compactPublicKey);

    originalInputs.clear();
    for (Map.Entry<String, String> entry : dataTable.asMap(String.class, String.class).entrySet()) {
      String type = entry.getKey();
      String valueStr = entry.getValue();
      switch (type) {
        case "boolean" -> {
          boolean val = Boolean.parseBoolean(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "byte" -> {
          byte val = Byte.parseByte(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "int" -> {
          int val = Integer.parseInt(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "long" -> {
          long val = Long.parseLong(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "U256" -> {
          BigInteger val = new BigInteger(valueStr);
          originalInputs.add(val);
          builder.push(U256.valueOf(val));
        }
        default -> throw new IllegalArgumentException("Unsupported type: " + type);
      }
    }
    context.compactList = context.track(builder.buildPacked());
  }

  @When("I serialize and deserialize the compact ciphertext list")
  public void iSerializeAndDeserializeTheCompactCiphertextList() {
    try (DynamicBuffer serialized = context.compactList.serialize()) {
      compactListSerializedBytes = serialized.toByteArray();
    }
    assertThat(compactListSerializedBytes).isNotEmpty();

    try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(compactListSerializedBytes)) {
      context.compactList = context.track(CompactCiphertextList.deserialize(buffer));
    }
  }

  @Then("the expanded list has {int} elements of kinds {word}, {word}, {word}, {word}, and {word} respectively")
  public void theExpandedListHasElementsOfKinds(int count, String k1, String k2, String k3, String k4, String k5) {
    context.expander = context.track(context.compactList.expand());
    assertThat(context.expander.size()).isEqualTo(count);

    String cleanK1 = k1.replace(",", "");
    String cleanK2 = k2.replace(",", "");
    String cleanK3 = k3.replace(",", "");
    String cleanK4 = k4.replace(",", "");
    String cleanK5 = k5.replace(",", "");

    assertThat(context.expander.getKindOf(0).name()).isEqualTo(cleanK1);
    assertThat(context.expander.getKindOf(1).name()).isEqualTo(cleanK2);
    assertThat(context.expander.getKindOf(2).name()).isEqualTo(cleanK3);
    assertThat(context.expander.getKindOf(3).name()).isEqualTo(cleanK4);
    assertThat(context.expander.getKindOf(4).name()).isEqualTo(cleanK5);
  }

  @Then("the decrypted values match the original inputs")
  public void theDecryptedValuesMatchTheOriginalInputs() {
    ClientKey clientKey = context.keySet.getClientKey();
    if (context.expander != null) {
      for (int i = 0; i < originalInputs.size(); i++) {
        Object expected = originalInputs.get(i);
        FheTypes kind = context.expander.getKindOf(i);
        switch (kind) {
          case FheBool -> {
            FheBool decrypted = context.track(context.expander.get(i, FheBool.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(expected);
          }
          case FheInt8, FheUint8 -> {
            byte decrypted;
            if (kind == FheTypes.FheInt8) {
              FheInt8 dec = context.track(context.expander.get(i, FheInt8.class));
              decrypted = dec.decrypt(clientKey);
            } else {
              FheUint8 dec = context.track(context.expander.get(i, FheUint8.class));
              decrypted = dec.decrypt(clientKey);
            }
            assertThat(decrypted).isEqualTo(((Number) expected).byteValue());
          }
          case FheInt32 -> {
            FheInt32 decrypted = context.track(context.expander.get(i, FheInt32.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).intValue());
          }
          case FheInt64 -> {
            FheInt64 decrypted = context.track(context.expander.get(i, FheInt64.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).longValue());
          }
          case FheUint256 -> {
            FheUint256 decrypted = context.track(context.expander.get(i, FheUint256.class));
            assertThat(decrypted.decrypt(clientKey).getValue()).isEqualTo((BigInteger) expected);
          }
          default -> throw new IllegalArgumentException("Unsupported kind: " + kind);
        }
      }
    } else if (context.compressedList != null) {
      for (int i = 0; i < originalInputs.size(); i++) {
        Object expected = originalInputs.get(i);
        FheTypes kind = context.compressedList.getKindOf(i);
        switch (kind) {
          case FheBool -> {
            FheBool decrypted = context.track(context.compressedList.get(i, FheBool.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(expected);
          }
          case FheUint8 -> {
            FheUint8 decrypted = context.track(context.compressedList.get(i, FheUint8.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).byteValue());
          }
          case FheInt32 -> {
            FheInt32 decrypted = context.track(context.compressedList.get(i, FheInt32.class));
            assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).intValue());
          }
          case FheUint256 -> {
            FheUint256 decrypted = context.track(context.compressedList.get(i, FheUint256.class));
            assertThat(decrypted.decrypt(clientKey).getValue()).isEqualTo((BigInteger) expected);
          }
          default -> throw new IllegalArgumentException("Unsupported kind: " + kind);
        }
      }
    } else {
      throw new IllegalStateException("No list available to verify");
    }
  }

  @When("I compress and build a list with values:")
  public void iCompressAndBuildAListWithValues(DataTable dataTable) {
    ClientKey clientKey = context.keySet.getClientKey();
    originalInputs.clear();

    try (CompressedCiphertextListBuilder builder = new CompressedCiphertextListBuilder()) {
      for (Map.Entry<String, String> entry : dataTable.asMap(String.class, String.class).entrySet()) {
        String type = entry.getKey();
        String valueStr = entry.getValue();
        switch (type) {
          case "boolean" -> {
            boolean val = Boolean.parseBoolean(valueStr);
            originalInputs.add(val);
            FheBool encrypted = context.track(FheBool.encrypt(val, clientKey));
            builder.push(encrypted);
          }
          case "byte" -> {
            byte val = Byte.parseByte(valueStr);
            originalInputs.add(val);
            FheUint8 encrypted = context.track(FheUint8.encrypt(val, clientKey));
            builder.push(encrypted);
          }
          case "int" -> {
            int val = Integer.parseInt(valueStr);
            originalInputs.add(val);
            FheInt32 encrypted = context.track(FheInt32.encrypt(val, clientKey));
            builder.push(encrypted);
          }
          case "U256" -> {
            BigInteger val = new BigInteger(valueStr);
            originalInputs.add(val);
            FheUint256 encrypted = context.track(FheUint256.encrypt(U256.valueOf(val), clientKey));
            builder.push(encrypted);
          }
          default -> throw new IllegalArgumentException("Unsupported type: " + type);
        }
      }
      context.compressedList = context.track(builder.build());
    }
  }

  @When("I serialize and deserialize the compressed ciphertext list")
  public void iSerializeAndDeserializeTheCompressedCiphertextList() {
    try (DynamicBuffer serialized = context.compressedList.serialize()) {
      compressedListSerializedBytes = serialized.toByteArray();
    }
    assertThat(compressedListSerializedBytes).isNotEmpty();

    try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(compressedListSerializedBytes)) {
      context.compressedList = context.track(CompressedCiphertextList.deserialize(buffer));
    }
  }

  @Then("the unpacked list has {int} elements of kinds {word}, {word}, {word}, and {word} respectively")
  public void theUnpackedListHasElementsOfKinds(int count, String k1, String k2, String k3, String k4) {
    assertThat(context.compressedList.size()).isEqualTo(count);

    String cleanK1 = k1.replace(",", "");
    String cleanK2 = k2.replace(",", "");
    String cleanK3 = k3.replace(",", "");
    String cleanK4 = k4.replace(",", "");

    assertThat(context.compressedList.getKindOf(0).name()).isEqualTo(cleanK1);
    assertThat(context.compressedList.getKindOf(1).name()).isEqualTo(cleanK2);
    assertThat(context.compressedList.getKindOf(2).name()).isEqualTo(cleanK3);
    assertThat(context.compressedList.getKindOf(3).name()).isEqualTo(cleanK4);
  }

  @When("I build a proven compact ciphertext list with:")
  public void iBuildAProvenCompactCiphertextListWith(DataTable dataTable) {
    context.compactPublicKey = context.track(new CompactPublicKey(context.keySet.getClientKey()));
    CompactCiphertextListBuilder builder = new CompactCiphertextListBuilder(context.compactPublicKey);

    originalInputs.clear();
    for (Map.Entry<String, String> entry : dataTable.asMap(String.class, String.class).entrySet()) {
      String type = entry.getKey();
      String valueStr = entry.getValue();
      switch (type) {
        case "boolean" -> {
          boolean val = Boolean.parseBoolean(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "byte" -> {
          byte val = Byte.parseByte(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        case "int" -> {
          int val = Integer.parseInt(valueStr);
          originalInputs.add(val);
          builder.push(val);
        }
        default -> throw new IllegalArgumentException("Unsupported type: " + type);
      }
    }

    byte[] metadata = (originalInputs.size() == 3) ? new byte[]{1, 2, 3, 4} : new byte[]{9, 8, 7};
    context.provenList = context.track(builder.buildWithProofPacked(context.crs, metadata, ZkComputeLoad.PROOF));
  }

  @When("I serialize and deserialize the proven compact ciphertext list")
  public void iSerializeAndDeserializeTheProvenCompactCiphertextList() {
    try (DynamicBuffer serialized = context.provenList.serialize()) {
      provenListSerializedBytes = serialized.toByteArray();
    }
    assertThat(provenListSerializedBytes).isNotEmpty();

    try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(provenListSerializedBytes)) {
      context.provenList = context.track(ProvenCompactCiphertextList.deserialize(buffer));
    }
  }

  @When("I deserialize the list with conformance parameters")
  public void iDeserializeTheListWithConformanceParameters() {
    if (provenListSerializedBytes == null && context.provenList != null) {
      try (DynamicBuffer serialized = context.provenList.serialize()) {
        provenListSerializedBytes = serialized.toByteArray();
      }
    }
    try (DynamicBuffer buffer = DynamicBuffer.fromByteArray(provenListSerializedBytes)) {
      context.provenList = context.track(ProvenCompactCiphertextList.deserializeConformant(buffer, context.compactPublicKey, context.crs));
    }
  }

  @Then("verifying and expanding the list with metadata {string} succeeds")
  public void verifyingAndExpandingTheListWithMetadataSucceeds(String metadataStr) {
    byte[] metadata = parseMetadata(metadataStr);
    context.expander = context.track(context.provenList.verifyAndExpand(context.crs, context.compactPublicKey, metadata));
    assertThat(context.expander).isNotNull();
  }

  @Then("verifying and expanding the conformant list with metadata {string} succeeds")
  public void verifyingAndExpandingTheConformantListWithMetadataSucceeds(String metadataStr) {
    byte[] metadata = parseMetadata(metadataStr);
    context.expander = context.track(context.provenList.verifyAndExpand(context.crs, context.compactPublicKey, metadata));
    assertThat(context.expander).isNotNull();
  }

  @Then("the decrypted elements match the original values")
  public void theDecryptedElementsMatchTheOriginalValues() {
    ClientKey clientKey = context.keySet.getClientKey();
    for (int i = 0; i < originalInputs.size(); i++) {
      Object expected = originalInputs.get(i);
      FheTypes kind = context.expander.getKindOf(i);
      switch (kind) {
        case FheBool -> {
          FheBool decrypted = context.track(context.expander.get(i, FheBool.class));
          assertThat(decrypted.decrypt(clientKey)).isEqualTo(expected);
        }
        case FheInt8 -> {
          FheInt8 decrypted = context.track(context.expander.get(i, FheInt8.class));
          assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).byteValue());
        }
        case FheInt32 -> {
          FheInt32 decrypted = context.track(context.expander.get(i, FheInt32.class));
          assertThat(decrypted.decrypt(clientKey)).isEqualTo(((Number) expected).intValue());
        }
        default -> throw new IllegalArgumentException("Unsupported kind: " + kind);
      }
    }
  }

  private byte[] parseMetadata(String metadataStr) {
    String[] parts = metadataStr.replace("\"", "").split(",");
    byte[] metadata = new byte[parts.length];
    for (int i = 0; i < parts.length; i++) {
      metadata[i] = Byte.parseByte(parts[i].trim());
    }
    return metadata;
  }
}
