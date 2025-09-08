package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.utils.TfheLibrary;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.ref.Cleaner;
import java.util.Optional;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.tfhe_error_disable_automatic_prints;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.LIBRARY_ARENA;
import static java.lang.ref.Cleaner.Cleanable;
import static java.lang.ref.Cleaner.create;

public class NativePointer {
  public static final Long BUFFER_MAX_SIZE = Long.MAX_VALUE;
  private static final Logger logger = LoggerFactory.getLogger(NativePointer.class);
  private static final Cleaner CLEANER = create();

  static {
    TfheLibrary.load();
    System.setProperty("jextract.trace.downcalls", String.valueOf(logger.isTraceEnabled()));
    tfhe_error_disable_automatic_prints();
  }

  private final MemorySegment address;
  private final Cleanable cleanable;

  protected NativePointer(MemorySegment address, @Nullable Function<MemorySegment, Integer> destroyer) {
    this.address = address;
    this.cleanable = destroyer != null
      ? CLEANER.register(this, new NativeHandle(getClass(), address, destroyer))
      : null;
  }

  protected NativePointer(Function<SegmentAllocator, MemorySegment> allocator, @Nullable Function<MemorySegment, Integer> destroyer) {
    this(allocator.apply(LIBRARY_ARENA), destroyer);
  }

  protected NativePointer(Function<SegmentAllocator, MemorySegment> allocator) {
    this(allocator.apply(LIBRARY_ARENA), null);
  }

  public MemorySegment getAddress() {
    return address;
  }

  protected static @NonNull Function<MemorySegment, Integer> valueDestroyerOf(@Nullable Function<MemorySegment, Integer> destroyer) {
    return address -> Optional.ofNullable(destroyer)
                              .map(d -> d.apply(address.get(C_POINTER, 0)))
                              .orElse(0);
  }

  protected void destroy() {
    if (cleanable != null) {
      cleanable.clean();
    }
  }
}
