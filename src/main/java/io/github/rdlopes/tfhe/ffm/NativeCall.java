package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.tfhe_error_get_last;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

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
      if (!NO_ERROR_MESSAGE.equals(errorMessage)) {
        logger.warn("execute - result: {}, errorMessage: {}", result, errorMessage);
        throw new NativeCallException(result, errorMessage);
      }
    }
  }

  public static void executeWithAddress(MemorySegment address, Function<MemorySegment, Integer> setter) {
    execute(() -> setter.apply(address));
  }

  public static <R> R executeAndReturn(Class<R> returnType, Function<MemorySegment, Integer> setter) {
    logger.trace("executeAndReturn - returnType: {}, setter: {}", returnType, setter);

    ValueLayout layout = switch (returnType) {
      case Class<?> type when (type == Boolean.class) -> C_BOOL;
      case Class<?> type when (type == Byte.class) -> C_CHAR;
      case Class<?> type when (type == Short.class) -> C_SHORT;
      case Class<?> type when (type == Integer.class) -> C_INT;
      case Class<?> type when (type == Long.class) -> C_LONG;
      default -> C_POINTER;
    };

    MemorySegment memorySegment = LIBRARY_ARENA.allocate(layout);
    execute(() -> setter.apply(memorySegment));

    var result = switch (returnType) {
      case Class<?> type when (type == Boolean.class) -> memorySegment.get(C_BOOL, 0);
      case Class<?> type when (type == Byte.class) -> memorySegment.get(C_CHAR, 0);
      case Class<?> type when (type == Short.class) -> memorySegment.get(C_SHORT, 0);
      case Class<?> type when (type == Integer.class) -> memorySegment.get(C_INT, 0);
      case Class<?> type when (type == Long.class) -> memorySegment.get(C_LONG, 0);
      case Class<?> _ -> memorySegment.get(C_POINTER, 0);
    };

    return returnType.cast(result);
  }
}
