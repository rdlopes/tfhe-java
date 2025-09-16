package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.api.calls.NativeCallException;
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
      logger.warn("execute - result: {}, errorMessage: {}", result, errorMessage);
      if (!NO_ERROR_MESSAGE.equals(errorMessage)) throw new NativeCallException(result, errorMessage);
    }
  }

  public static void executeWithAddress(MemorySegment address, Function<MemorySegment, Integer> setter) {
    execute(() -> setter.apply(address));
  }

  public static <R> R executeAndReturn(Class<R> returnType, Function<MemorySegment, Integer> setter) {

    ValueLayout layout = switch (returnType) {
      case Class<R> type when (type == Boolean.class || type == boolean.class) -> C_BOOL;
      case Class<R> type when (type == Byte.class || type == byte.class) -> C_CHAR;
      case Class<R> type when (type == Short.class || type == short.class) -> C_SHORT;
      case Class<R> type when (type == Integer.class || type == int.class) -> C_INT;
      case Class<R> type when (type == Long.class || type == long.class) -> C_LONG;
      default -> throw new IllegalArgumentException("Unsupported return type: " + returnType);
    };

    MemorySegment memorySegment = LIBRARY_ARENA.allocate(layout);
    executeWithAddress(memorySegment, setter);

    return (R) switch (returnType) {
      case Class<R> type when (type == Boolean.class || type == boolean.class) -> memorySegment.get(C_BOOL, 0);
      case Class<R> type when (type == Byte.class || type == byte.class) -> memorySegment.get(C_CHAR, 0);
      case Class<R> type when (type == Short.class || type == short.class) -> memorySegment.get(C_SHORT, 0);
      case Class<R> type when (type == Integer.class || type == int.class) -> memorySegment.get(C_INT, 0);
      case Class<R> _ -> memorySegment.get(C_LONG, 0);
    };
  }
}
