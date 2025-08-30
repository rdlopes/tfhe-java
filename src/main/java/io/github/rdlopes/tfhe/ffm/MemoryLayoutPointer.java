package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.core.NativeCallException;
import io.github.rdlopes.tfhe.utils.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.ref.Cleaner;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public abstract class MemoryLayoutPointer<L extends MemoryLayout> {
  protected static final Cleaner CLEANER = Cleaner.create();
  protected static final Arena ARENA = LIBRARY_ARENA;
  private static final Logger logger = LoggerFactory.getLogger(MemoryLayoutPointer.class);

  static {
    NativeLibrary.load();
    System.setProperty("jextract.trace.downcalls", String.valueOf(logger.isDebugEnabled()));
    tfhe_error_disable_automatic_prints();
  }

  private final MemorySegment address;
  private final L layout;

  public MemoryLayoutPointer(MemorySegment address, L layout, Consumer<MemorySegment> cleaner) {
    this.address = address;
    this.layout = layout;
    if (cleaner != null) {
      CLEANER.register(this, () -> cleaner.accept(address));
    }
  }

  /**
   * Executes a native call and handles the error.
   * Cannot be reentrant as the native error is overwritten.
   *
   * @param call native call to be executed
   * @throws NativeCallException if the native call fails
   */
  protected static synchronized void executeWithErrorHandling(Supplier<Integer> call) throws NativeCallException {
    logger.trace("nativeCall - call: {}", call);

    int result = call.get();

    if (result != 0) {
      MemorySegment lastErrorPtr = tfhe_error_get_last();
      String lastError = lastErrorPtr.getString(0);
      if (!lastError.equals("no error")) throw new NativeCallException(result, lastError);
    }
  }

  public MemorySegment getAddress() {
    return address;
  }

  public L getLayout() {
    return layout;
  }

}
