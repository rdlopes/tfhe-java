package io.github.rdlopes.tfhe.jce.spi;

import io.github.rdlopes.tfhe.ffm.*;

import java.lang.foreign.Arena;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class TfheKeyPairGenerator extends KeyPairGeneratorSpi {
  private int keySize;

  @Override
  public void initialize(int keySize, SecureRandom random) {
    this.keySize = keySize;
  }

  @Override
  public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
    initialize(keySize, random);
  }

  @Override
  public KeyPair generateKeyPair() {
    try (Arena arena = Arena.ofConfined()) {
      ConfigBuilder configBuilder = new ConfigBuilder(arena);
      Config config = configBuilder.build();

      ClientKey clientKey = config.generateClientKey();
      CompressedServerKey compressedServerKey = new CompressedServerKey(arena, clientKey);
      CompressedCompactPublicKey compressedCompactPublicKey = clientKey.generateCompressedCompactPublicKey();

      TfhePrivateKey privateKey = new TfhePrivateKey(clientKey.getBytes());
      TfhePublicKey publicKey = new TfhePublicKey(compressedCompactPublicKey.getBytes(), compressedServerKey.getBytes());

      return new KeyPair(publicKey, privateKey);
    }
  }

}
