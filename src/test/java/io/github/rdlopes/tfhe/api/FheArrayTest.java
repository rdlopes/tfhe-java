package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.types.FheUint128;
import io.github.rdlopes.tfhe.api.types.FheUint128Array;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.FheUint8Array;
import io.github.rdlopes.tfhe.api.values.U128;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FheArrayTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
    keySet.getServerKey()
          .use();
  }

  @Test
  void testFheUint8ArraySum_lowBitSize() {
    List<Byte> values = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4);
    FheUint8Array array = FheUint8Array.encrypt(values, keySet.getClientKey());

    FheUint8 sum = array.sum();

    assertThat(sum).isNotNull();
    Byte decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum).isEqualTo((byte) 10);
  }

  @Test
  void testFheUint8ArraySum_emptyArray() {
    List<Byte> values = List.of();
    FheUint8Array array = FheUint8Array.encrypt(values, keySet.getClientKey());

    FheUint8 sum = array.sum();

    assertThat(sum).isNotNull();
    Byte decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum).isEqualTo((byte) 0);
  }

  @Test
  void testFheUint8ArraySum_singleElement() {
    List<Byte> values = List.of((byte) 42);
    FheUint8Array array = FheUint8Array.encrypt(values, keySet.getClientKey());

    FheUint8 sum = array.sum();

    assertThat(sum).isNotNull();
    Byte decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum).isEqualTo((byte) 42);
  }

  @Test
  void testFheUint8ArraySum_withPublicKey() {
    PublicKey publicKey = new PublicKey(keySet.getClientKey());
    List<Byte> values = List.of((byte) 5, (byte) 10, (byte) 15);
    FheUint8Array array = FheUint8Array.encrypt(values, publicKey);

    FheUint8 sum = array.sum();

    assertThat(sum).isNotNull();
    Byte decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum).isEqualTo((byte) 30);
  }

  @Test
  @Tag("intensive")
  void testFheUint128ArraySum_highBitSize() {
    List<U128> values = List.of(
      U128.valueOf(BigInteger.valueOf(100)),
      U128.valueOf(BigInteger.valueOf(200)),
      U128.valueOf(BigInteger.valueOf(300))
    );
    FheUint128Array array = FheUint128Array.encrypt(values, keySet.getClientKey());

    FheUint128 sum = array.sum();

    assertThat(sum).isNotNull();
    U128 decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum.getValue()).isEqualTo(BigInteger.valueOf(600));
  }

  @Test
  @Tag("intensive")
  void testFheUint128ArraySum_emptyArray() {
    List<U128> values = List.of();
    FheUint128Array array = FheUint128Array.encrypt(values, keySet.getClientKey());

    FheUint128 sum = array.sum();

    assertThat(sum).isNotNull();
    U128 decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum.getValue()).isEqualTo(BigInteger.ZERO);
  }

  @Test
  @Tag("intensive")
  void testFheUint128ArraySum_singleElement() {
    U128 value = U128.valueOf(BigInteger.valueOf(1000));
    List<U128> values = List.of(value);
    FheUint128Array array = FheUint128Array.encrypt(values, keySet.getClientKey());

    FheUint128 sum = array.sum();

    assertThat(sum).isNotNull();
    U128 decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum.getValue()).isEqualTo(BigInteger.valueOf(1000));
  }

  @Test
  @Tag("intensive")
  void testFheUint128ArraySum_withPublicKey() {
    PublicKey publicKey = new PublicKey(keySet.getClientKey());
    List<U128> values = List.of(
      U128.valueOf(BigInteger.valueOf(50)),
      U128.valueOf(BigInteger.valueOf(75)),
      U128.valueOf(BigInteger.valueOf(25))
    );
    FheUint128Array array = FheUint128Array.encrypt(values, publicKey);

    FheUint128 sum = array.sum();

    assertThat(sum).isNotNull();
    U128 decryptedSum = sum.decrypt(keySet.getClientKey());
    assertThat(decryptedSum.getValue()).isEqualTo(BigInteger.valueOf(150));
  }
}
