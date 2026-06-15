package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class TfheThreadingContextTest {

  @Test
  @DisabledIfSystemProperty(named = "tfhe.gpu", matches = "true")
  void testThreadingContextExecution() {
    try (KeySet keySet = KeySet.builder().build()) {
      FheUint8 lhs = FheUint8.encrypt((byte) 10, keySet.getClientKey());
      FheUint8 rhs = FheUint8.encrypt((byte) 20, keySet.getClientKey());
      
      AtomicReference<FheUint8> sumRef = new AtomicReference<>();
      
      try (TfheThreadingContext context = new TfheThreadingContext(2)) {
        context.setServerKey(keySet.getServerKey());
        
        context.run(() -> {
          // This will run inside Rayon's thread pool
          FheUint8 sum = lhs.add(rhs);
          sumRef.set(sum);
        });
      }
      
      FheUint8 result = sumRef.get();
      assertThat(result).isNotNull();
      assertThat(result.decrypt(keySet.getClientKey())).isEqualTo((byte) 30);
    }
  }
}
