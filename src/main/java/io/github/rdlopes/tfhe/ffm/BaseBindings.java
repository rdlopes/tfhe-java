package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.utils.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public abstract class BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(BaseBindings.class);

  public static final long SERDE_MAX_SIZE = Long.MAX_VALUE;

  static {
    NativeLibrary.load();
    System.setProperty("jextract.trace.downcalls", String.valueOf(logger.isDebugEnabled()));
    tfhe_error_disable_automatic_prints();
  }

  protected static void executeWithErrorHandling(Supplier<Integer> call) throws NativeCallException {
    logger.trace("nativeCall - call: {}", call);

    int result = call.get();

    if (result != 0) {
      MemorySegment lastErrorPtr = tfhe_error_get_last();
      String lastError = lastErrorPtr.getString(0);
      tfhe_error_clear();
      throw new NativeCallException(result, lastError);
    }
  }

  public static MemorySegment addressValue(MemorySegment address) {
    logger.trace("addressValue - address: {}", address);
    return address.get(C_POINTER, 0);
  }

  public static MemorySegment allocateData(long size) {
    logger.trace("allocateData - size: {}", size);
    return LIBRARY_ARENA.allocate(size);
  }

}
