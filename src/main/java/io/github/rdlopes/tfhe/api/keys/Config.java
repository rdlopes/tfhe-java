package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.generate_keys;

public class Config extends NativePointer {
  private static final Logger logger = LoggerFactory.getLogger(Config.class);
  private final AtomicBoolean keysGenerated = new AtomicBoolean(false);

  Config() {
    logger.trace("init");

    super(null);
  }

  public void initialize(ClientKey clientKey, ServerKey serverKey) {
    logger.trace("initialize - clientKey: {}, serverKey: {}", clientKey, serverKey);

    if (keysGenerated.getAcquire()) {
      throw new IllegalStateException("Keys have already been generated for this Config instance");
    }
    execute(() -> generate_keys(getValue(), clientKey.getAddress(), serverKey.getAddress()));
    keysGenerated.setRelease(true);
  }

}
