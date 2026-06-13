package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.*;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;
import static java.lang.foreign.MemoryLayout.PathElement.groupElement;

public class TfheHeaderExtension {
  TfheHeaderExtension() {
    // Should not be called directly
  }

  public static MemorySegment SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  public static MemorySegment SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  public static MemorySegment SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  public static MemorySegment SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  public static MemorySegment SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  public static MemorySegment SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    return SymbolLookup.loaderLookup()
                       .find("SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128")
                       .orElseThrow(() -> new RuntimeException("Symbol SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128 not found"))
                       .reinterpret(CompressionParameters.layout()
                                                         .byteSize());
  }

  @SuppressWarnings("unused")
  public static class CompressionParameters {

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
                                                             C_LONG_LONG.withName("br_level"),
                                                             C_LONG_LONG.withName("br_base_log"),
                                                             C_LONG_LONG.withName("packing_ks_level"),
                                                             C_LONG_LONG.withName("packing_ks_base_log"),
                                                             C_LONG_LONG.withName("packing_ks_polynomial_size"),
                                                             C_LONG_LONG.withName("packing_ks_glwe_dimension"),
                                                             C_LONG_LONG.withName("lwe_per_glwe"),
                                                             C_LONG_LONG.withName("storage_log_modulus"),
                                                             DynamicDistribution.layout()
                                                                                .withName("packing_ks_key_noise_distribution"))
                                                           .withName("CompressionParameters");
    private static final ValueLayout br_level$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("br_level"));
    private static final long br_level$OFFSET = $LAYOUT.byteOffset(groupElement("br_level"));
    private static final ValueLayout br_base_log$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("br_base_log"));
    private static final long br_base_log$OFFSET = $LAYOUT.byteOffset(groupElement("br_base_log"));
    private static final ValueLayout packing_ks_level$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("packing_ks_level"));
    private static final long packing_ks_level$OFFSET = $LAYOUT.byteOffset(groupElement("packing_ks_level"));
    private static final ValueLayout packing_ks_base_log$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("packing_ks_base_log"));
    private static final long packing_ks_base_log$OFFSET = $LAYOUT.byteOffset(groupElement("packing_ks_base_log"));
    private static final ValueLayout packing_ks_polynomial_size$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("packing_ks_polynomial_size"));
    private static final long packing_ks_polynomial_size$OFFSET = $LAYOUT.byteOffset(groupElement("packing_ks_polynomial_size"));
    private static final ValueLayout packing_ks_glwe_dimension$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("packing_ks_glwe_dimension"));
    private static final long packing_ks_glwe_dimension$OFFSET = $LAYOUT.byteOffset(groupElement("packing_ks_glwe_dimension"));
    private static final ValueLayout lwe_per_glwe$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("lwe_per_glwe"));
    private static final long lwe_per_glwe$OFFSET = $LAYOUT.byteOffset(groupElement("lwe_per_glwe"));
    private static final ValueLayout storage_log_modulus$LAYOUT = (ValueLayout) $LAYOUT.select(groupElement("storage_log_modulus"));
    private static final long storage_log_modulus$OFFSET = $LAYOUT.byteOffset(groupElement("storage_log_modulus"));
    private static final GroupLayout packing_ks_key_noise_distribution$LAYOUT = (GroupLayout) $LAYOUT.select(groupElement("packing_ks_key_noise_distribution"));
    private static final long packing_ks_key_noise_distribution$OFFSET = $LAYOUT.byteOffset(groupElement("packing_ks_key_noise_distribution"));

    private static long getLongOrInt(ValueLayout layout, long offset, MemorySegment struct) {
      if (layout instanceof ValueLayout.OfLong ofLong) {
        return struct.get(ofLong, offset);
      } else {
        return struct.get((ValueLayout.OfInt) layout, offset);
      }
    }

    private static void setLongOrInt(ValueLayout layout, long offset, MemorySegment struct, long value) {
      if (layout instanceof ValueLayout.OfLong ofLong) {
        struct.set(ofLong, offset, value);
      } else {
        struct.set((ValueLayout.OfInt) layout, offset, (int) value);
      }
    }

    CompressionParameters() {
    }

    public static GroupLayout layout() {
      return $LAYOUT;
    }

    public static ValueLayout br_level$layout() {
      return br_level$LAYOUT;
    }

    public static long br_level$offset() {
      return br_level$OFFSET;
    }

    public static long br_level(MemorySegment struct) {
      return getLongOrInt(br_level$LAYOUT, br_level$OFFSET, struct);
    }

    public static void br_level(MemorySegment struct, long fieldValue) {
      setLongOrInt(br_level$LAYOUT, br_level$OFFSET, struct, fieldValue);
    }

    public static ValueLayout br_base_log$layout() {
      return br_base_log$LAYOUT;
    }

    public static long br_base_log$offset() {
      return br_base_log$OFFSET;
    }

    public static long br_base_log(MemorySegment struct) {
      return getLongOrInt(br_base_log$LAYOUT, br_base_log$OFFSET, struct);
    }

    public static void br_base_log(MemorySegment struct, long fieldValue) {
      setLongOrInt(br_base_log$LAYOUT, br_base_log$OFFSET, struct, fieldValue);
    }

    public static ValueLayout packing_ks_level$layout() {
      return packing_ks_level$LAYOUT;
    }

    public static long packing_ks_level$offset() {
      return packing_ks_level$OFFSET;
    }

    public static long packing_ks_level(MemorySegment struct) {
      return getLongOrInt(packing_ks_level$LAYOUT, packing_ks_level$OFFSET, struct);
    }

    public static void packing_ks_level(MemorySegment struct, long fieldValue) {
      setLongOrInt(packing_ks_level$LAYOUT, packing_ks_level$OFFSET, struct, fieldValue);
    }

    public static ValueLayout packing_ks_base_log$layout() {
      return packing_ks_base_log$LAYOUT;
    }

    public static long packing_ks_base_log$offset() {
      return packing_ks_base_log$OFFSET;
    }

    public static long packing_ks_base_log(MemorySegment struct) {
      return getLongOrInt(packing_ks_base_log$LAYOUT, packing_ks_base_log$OFFSET, struct);
    }

    public static void packing_ks_base_log(MemorySegment struct, long fieldValue) {
      setLongOrInt(packing_ks_base_log$LAYOUT, packing_ks_base_log$OFFSET, struct, fieldValue);
    }

    public static ValueLayout packing_ks_polynomial_size$layout() {
      return packing_ks_polynomial_size$LAYOUT;
    }

    public static long packing_ks_polynomial_size$offset() {
      return packing_ks_polynomial_size$OFFSET;
    }

    public static long packing_ks_polynomial_size(MemorySegment struct) {
      return getLongOrInt(packing_ks_polynomial_size$LAYOUT, packing_ks_polynomial_size$OFFSET, struct);
    }

    public static void packing_ks_polynomial_size(MemorySegment struct, long fieldValue) {
      setLongOrInt(packing_ks_polynomial_size$LAYOUT, packing_ks_polynomial_size$OFFSET, struct, fieldValue);
    }

    public static ValueLayout packing_ks_glwe_dimension$layout() {
      return packing_ks_glwe_dimension$LAYOUT;
    }

    public static long packing_ks_glwe_dimension$offset() {
      return packing_ks_glwe_dimension$OFFSET;
    }

    public static long packing_ks_glwe_dimension(MemorySegment struct) {
      return getLongOrInt(packing_ks_glwe_dimension$LAYOUT, packing_ks_glwe_dimension$OFFSET, struct);
    }

    public static void packing_ks_glwe_dimension(MemorySegment struct, long fieldValue) {
      setLongOrInt(packing_ks_glwe_dimension$LAYOUT, packing_ks_glwe_dimension$OFFSET, struct, fieldValue);
    }

    public static ValueLayout lwe_per_glwe$layout() {
      return lwe_per_glwe$LAYOUT;
    }

    public static long lwe_per_glwe$offset() {
      return lwe_per_glwe$OFFSET;
    }

    public static long lwe_per_glwe(MemorySegment struct) {
      return getLongOrInt(lwe_per_glwe$LAYOUT, lwe_per_glwe$OFFSET, struct);
    }

    public static void lwe_per_glwe(MemorySegment struct, long fieldValue) {
      setLongOrInt(lwe_per_glwe$LAYOUT, lwe_per_glwe$OFFSET, struct, fieldValue);
    }

    public static ValueLayout storage_log_modulus$layout() {
      return storage_log_modulus$LAYOUT;
    }

    public static long storage_log_modulus$offset() {
      return storage_log_modulus$OFFSET;
    }

    public static long storage_log_modulus(MemorySegment struct) {
      return getLongOrInt(storage_log_modulus$LAYOUT, storage_log_modulus$OFFSET, struct);
    }

    public static void storage_log_modulus(MemorySegment struct, long fieldValue) {
      setLongOrInt(storage_log_modulus$LAYOUT, storage_log_modulus$OFFSET, struct, fieldValue);
    }

    public static GroupLayout packing_ks_key_noise_distribution$layout() {
      return packing_ks_key_noise_distribution$LAYOUT;
    }

    public static long packing_ks_key_noise_distribution$offset() {
      return packing_ks_key_noise_distribution$OFFSET;
    }

    public static MemorySegment packing_ks_key_noise_distribution(MemorySegment struct) {
      return struct.asSlice(packing_ks_key_noise_distribution$OFFSET, packing_ks_key_noise_distribution$LAYOUT.byteSize());
    }

    public static void packing_ks_key_noise_distribution(MemorySegment struct, MemorySegment fieldValue) {
      MemorySegment.copy(fieldValue, 0, struct, packing_ks_key_noise_distribution$OFFSET, packing_ks_key_noise_distribution$LAYOUT.byteSize());
    }

    public static MemorySegment allocate(SegmentAllocator allocator) {
      return allocator.allocate(layout());
    }
  }

}
