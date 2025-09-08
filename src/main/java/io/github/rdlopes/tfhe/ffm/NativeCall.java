package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.api.calls.NativeCallException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.tfhe_error_get_last;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.C_BOOL;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.LIBRARY_ARENA;

public final class NativeCall {
  private static final Logger logger = LoggerFactory.getLogger(NativeCall.class);
  private static final String NO_ERROR_MESSAGE = "no error";

  private NativeCall() {
  }

  public static void execute(Supplier<Integer> supplier) {
    logger.trace("execute - supplier: {}", supplier);
    int result = supplier.get();
    if (result != 0) {
      MemorySegment errorMessageAddress = tfhe_error_get_last();
      String errorMessage = errorMessageAddress.getString(0);
      logger.warn("execute - result: {}, errorMessage: {}", result, errorMessage);
      if (!NO_ERROR_MESSAGE.equals(errorMessage)) throw new NativeCallException(result, errorMessage);
    }
  }

  public static void executeWithAddress(MemorySegment address, Function<MemorySegment, Integer> setter) {
    execute(() -> setter.apply(address));
  }

  public static @NonNull Boolean executeWithBoolean(Function<MemorySegment, Integer> setter) {
    MemorySegment memorySegment = LIBRARY_ARENA.allocate(C_BOOL);
    executeWithAddress(memorySegment, setter);
    return memorySegment.get(C_BOOL, 0);
  }

}
