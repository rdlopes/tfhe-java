package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class TfheMemoryAllocator extends TfheNativeBase {
  private static final Logger logger = LoggerFactory.getLogger(TfheMemoryAllocator.class);

  public static MemorySegment allocateConfig() {
    logger.trace("allocateConfig");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }

  public static MemorySegment allocateDynamicDistributionPayload() {
    logger.trace("allocateDynamicDistributionPayload");
    return LIBRARY_ARENA.allocate(DynamicDistributionPayload.layout());
  }

  public static MemorySegment allocateGaussian() {
    logger.trace("allocateGaussian");
    return LIBRARY_ARENA.allocate(Gaussian.layout());
  }

  public static MemorySegment allocateTUniform() {
    logger.trace("allocateTUniform");
    return LIBRARY_ARENA.allocate(TUniform.layout());
  }

  public static MemorySegment allocateShortintPBSParameters() {
    logger.trace("allocateShortintPBSParameters");
    return LIBRARY_ARENA.allocate(ShortintPBSParameters.layout());
  }

  public static MemorySegment allocateShortintCompactPublicKeyEncryptionParameters() {
    logger.trace("allocateShortintCompactPublicKeyEncryptionParameters");
    return LIBRARY_ARENA.allocate(ShortintCompactPublicKeyEncryptionParameters.layout());
  }

  public static MemorySegment allocateModulusSwitchNoiseReductionParams() {
    logger.trace("allocateModulusSwitchNoiseReductionParams");
    return LIBRARY_ARENA.allocate(ModulusSwitchNoiseReductionParams.layout());
  }

  public static MemorySegment allocateModulusSwitchType() {
    logger.trace("allocateModulusSwitchType");
    return LIBRARY_ARENA.allocate(ModulusSwitchType.layout());
  }

  public static MemorySegment allocateDynamicDistribution() {
    logger.trace("newDynamicDistribution");
    return LIBRARY_ARENA.allocate(DynamicDistribution.layout());
  }

  public static MemorySegment allocateGaussianFromStdDev(double stdDev) {
    logger.trace("newGaussianFromStdDev - stdDev: {}", stdDev);
    return TfheHeader.new_gaussian_from_std_dev(LIBRARY_ARENA, stdDev);
  }

  public static MemorySegment allocateTUniform(int boundLog2) {
    logger.trace("newTUniform - boundLog2: {}", boundLog2);
    return TfheHeader.new_t_uniform(LIBRARY_ARENA, boundLog2);
  }

  public static MemorySegment pointerValue(MemorySegment pointer) {
    logger.trace("pointerValue - pointer: {}", pointer);
    return pointer.get(C_POINTER, 0);
  }
}
