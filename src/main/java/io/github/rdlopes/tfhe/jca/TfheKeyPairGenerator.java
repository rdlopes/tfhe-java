package io.github.rdlopes.tfhe.jca;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedCompactPublicKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public final class TfheKeyPairGenerator extends KeyPairGeneratorSpi {
  private TfheParameterSpec parameterSpec = new TfheParameterSpec(false);

  @Override
  public void initialize(int keySize, SecureRandom random) {
  }

  @Override
  public void initialize(AlgorithmParameterSpec params, SecureRandom random) throws InvalidAlgorithmParameterException {
    if (Objects.requireNonNull(params) instanceof TfheParameterSpec paramsSpec) {
      this.parameterSpec = paramsSpec;
    } else {
      throw new InvalidAlgorithmParameterException("Only TfheParameterSpec is supported");
    }
  }

  @Override
  public KeyPair generateKeyPair() {
    ConfigBuilder configBuilder = new ConfigBuilder()
      .enableCompression(parameterSpec.compressionParameters());
    Config config = configBuilder.build();

    KeyPair keyPair;
    ClientKey clientKey = config.generateClientKey();
    DynamicBuffer clientKeyBuffer = clientKey.serialize();

    if (parameterSpec.isServerKey()) {
      CompressedServerKey compressedServerKey = clientKey.newCompressedServerKey();
      DynamicBuffer compressedServerKeyBuffer = compressedServerKey.serialize();
      keyPair = new KeyPair(
        new TfhePublicKey(
          compressedServerKeyBuffer.view()
                                   .toByteArray(),
          true,
          true),
        new TfhePrivateKey(
          clientKeyBuffer.view()
                         .toByteArray())
      );
      clientKeyBuffer.destroy();
      compressedServerKeyBuffer.destroy();
      clientKey.destroy();
      compressedServerKey.destroy();

    } else {
      CompressedCompactPublicKey compressedCompactPublicKey = clientKey.newCompressedCompactPublicKey();
      DynamicBuffer compressedCompactPublicKeyBuffer = compressedCompactPublicKey.serialize();
      keyPair = new KeyPair(
        new TfhePublicKey(
          compressedCompactPublicKeyBuffer.view()
                                          .toByteArray(),
          true,
          false),
        new TfhePrivateKey(
          clientKeyBuffer.view()
                         .toByteArray())
      );
      clientKeyBuffer.destroy();
      compressedCompactPublicKeyBuffer.destroy();
      clientKey.destroy();
      compressedCompactPublicKey.destroy();
    }

    return keyPair;
  }
}
