package io.github.rdlopes.tfhe.examples.voting.client;

import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKeyEncryptionParameters;
import io.github.rdlopes.tfhe.api.keys.Config;
import io.github.rdlopes.tfhe.api.keys.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.use_dedicated_compact_public_key_parameters;

/// Performs the one-time cryptographic setup for the election:
/// generates keys, creates the Common Reference String (CRS),
/// and initializes the public-key infrastructure.
public final class ElectionSetup implements AutoCloseable {

  private static final CompactPublicKeyEncryptionParameters PKE_PARAMS =
      CompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;

  /// Maximum number of boolean choices per ballot.
  public static final int CHOICES_PER_BALLOT = 3;

  private final CompactPkeCrs crs;
  private final KeySet keySet;
  private final CompactPublicKey compactPublicKey;
  private final PublicKey publicKey;

  /// Initializes the election cryptographic setup.
  public ElectionSetup() {
    Config crsConfig;
    try (ConfigBuilder builder = new ConfigBuilder()) {
      execute(() -> use_dedicated_compact_public_key_parameters(
          builder.getAddress(), PKE_PARAMS.address()
      ));
      crsConfig = builder.build();
    }
    this.crs = new CompactPkeCrs(crsConfig, CHOICES_PER_BALLOT);
    this.keySet = KeySet.builder().useCompactKeyEncryptionParameters(PKE_PARAMS).build();
    this.compactPublicKey = new CompactPublicKey(keySet.getClientKey());
    this.publicKey = new PublicKey(keySet.getClientKey());
    keySet.getServerKey().use();
  }

  /// Returns the ZK Common Reference String.
  public CompactPkeCrs crs() { return crs; }
  /// Returns the full key set.
  public KeySet keySet() { return keySet; }
  /// Returns the compact public key used for ballot encryption.
  public CompactPublicKey compactPublicKey() { return compactPublicKey; }
  /// Returns the standard public key used for tally initialization.
  public PublicKey publicKey() { return publicKey; }

  @Override
  public void close() {
    publicKey.close();
    compactPublicKey.close();
    keySet.close();
    crs.close();
  }
}
