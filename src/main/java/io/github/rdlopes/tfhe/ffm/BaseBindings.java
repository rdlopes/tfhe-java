package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.utils.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public abstract class BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(BaseBindings.class);

  static {
    NativeLibrary.load();
    System.setProperty("jextract.trace.downcalls", String.valueOf(logger.isTraceEnabled()));
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

  public static MemorySegment pointerValue(MemorySegment pointer) {
    logger.trace("pointerValue - pointer: {}", pointer);
    return pointer.get(C_POINTER, 0);
  }

}
