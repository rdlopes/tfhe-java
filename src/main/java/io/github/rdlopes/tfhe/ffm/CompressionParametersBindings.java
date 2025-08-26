package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeaderExtension.LIBRARY_ARENA;

public class CompressionParametersBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(CompressionParametersBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(TfheHeaderExtension.CompressionParameters.layout());
  }

  // Predefined compression parameters
  public static MemorySegment SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    logger.trace("SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128");
    return TfheHeaderExtension.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
  }

  public static MemorySegment SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64() {
    logger.trace("SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64");
    return TfheHeaderExtension.SHORTINT_V0_11_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M64();
  }

  public static MemorySegment SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    logger.trace("SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128");
    return TfheHeaderExtension.SHORTINT_V1_0_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
  }

  public static MemorySegment SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    logger.trace("SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128");
    return TfheHeaderExtension.SHORTINT_V1_1_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
  }

  public static MemorySegment SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    logger.trace("SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128");
    return TfheHeaderExtension.SHORTINT_V1_2_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
  }

  public static MemorySegment SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128() {
    logger.trace("SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128");
    return TfheHeaderExtension.SHORTINT_V1_3_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128();
  }

}
