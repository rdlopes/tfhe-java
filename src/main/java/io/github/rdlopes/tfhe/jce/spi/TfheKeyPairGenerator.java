package io.github.rdlopes.tfhe.jce.spi;

import io.github.rdlopes.tfhe.api.*;

import java.lang.foreign.Arena;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class TfheKeyPairGenerator extends KeyPairGeneratorSpi {
  private int keySize;
  private SecureRandom random;
  private AlgorithmParameterSpec params;

  @Override
  public void initialize(int keySize, SecureRandom random) {
    this.keySize = keySize;
    this.random = random;
  }

  @Override
  public void initialize(AlgorithmParameterSpec params, SecureRandom random) throws InvalidAlgorithmParameterException {
    initialize(keySize, random);
    this.params = params;
  }

  @Override
  public KeyPair generateKeyPair() {
    try (Arena arena = Arena.ofConfined()) {
      ConfigBuilder configBuilder = new ConfigBuilder(arena);
      Config config = configBuilder.build();

      ClientKey clientKey = new ClientKey(arena, config);
      CompressedServerKey compressedServerKey = new CompressedServerKey(arena, clientKey);
      CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey(arena, clientKey);

      TfhePrivateKey privateKey = new TfhePrivateKey(clientKey.getBytes());
      TfhePublicKey publicKey = new TfhePublicKey(compressedCompactPublicKey.getBytes(), compressedServerKey.getBytes());

      return new KeyPair(publicKey, privateKey);
    }
  }

}
