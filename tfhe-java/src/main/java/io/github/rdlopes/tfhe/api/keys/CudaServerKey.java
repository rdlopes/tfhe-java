package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.set_cuda_server_key;

/// Package-private holder for a native `CudaServerKey*` pointer.
/// Obtained via [CompressedServerKey#decompressToGpu()]; lifecycle is managed
/// by the owning [CompressedServerKey] or the caller.
final class CudaServerKey extends NativePointer {
  private static final Logger logger = LoggerFactory.getLogger(CudaServerKey.class);

  CudaServerKey() {
    super(TfheHeader::cuda_server_key_destroy);
  }

  /// Registers this CUDA key with the TFHE runtime on the current thread.
  void use() {
    logger.trace("use");
    execute(() -> set_cuda_server_key(getValue()));
  }
}
