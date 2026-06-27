package io.github.rdlopes.tfhe.api.keys;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public enum CompactPublicKeyEncryptionParameters {
  SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(loadLibraryAndGet(() -> SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128())),
  SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64(SHORTINT_V0_11_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64()),
  SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(SHORTINT_V1_0_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()),
  SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(SHORTINT_V1_1_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()),
  SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(SHORTINT_V1_2_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()),
  SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(SHORTINT_V1_3_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128()),
  SHORTINT_V1_4_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128(SHORTINT_V1_4_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128());

  private final MemorySegment address;

  CompactPublicKeyEncryptionParameters(MemorySegment address) {
    this.address = address;
  }

  public MemorySegment address() {
    return address;
  }

  private static MemorySegment loadLibraryAndGet(java.util.function.Supplier<MemorySegment> supplier) {
    io.github.rdlopes.tfhe.core.ffm.NativeLibrary.load();
    return supplier.get();
  }
}
