package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.tfhe_threading_context_run$func;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class TfheThreadingContext extends NativePointer implements AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(TfheThreadingContext.class);

  public TfheThreadingContext(long numThreads) {
    super(context -> {
      tfhe_threading_context_destroy(context);
      return 0;
    });
    logger.trace("init - numThreads: {}", numThreads);
    execute(() -> tfhe_threading_context_create(numThreads, getAddress()));
  }

  public void setServerKey(ServerKey serverKey) {
    logger.trace("setServerKey");
    execute(() -> tfhe_threading_context_set_server_key(getValue(), serverKey.getValue()));
  }

  public void run(Runnable task) {
    logger.trace("run");
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment stub = tfhe_threading_context_run$func.allocate(_ -> {
        try {
          task.run();
          return 0; // Success
        } catch (Exception e) {
          logger.error("Error running task in threading context", e);
          return 1; // Error
        }
      }, arena);

      execute(() -> tfhe_threading_context_run(getValue(), stub, MemorySegment.NULL));
    }
  }

  @Override
  public void close() {
    destroy();
  }
}
