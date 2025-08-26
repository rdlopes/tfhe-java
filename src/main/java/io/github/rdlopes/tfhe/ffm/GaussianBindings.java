package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.new_gaussian_from_std_dev;

public final class GaussianBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(GaussianBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(Gaussian.layout());
  }

  public static MemorySegment allocateFromStdDev(double stdDev) {
    logger.trace("allocateFromStdDev - stdDev: {}", stdDev);
    return new_gaussian_from_std_dev(LIBRARY_ARENA, stdDev);
  }

  public static void std(MemorySegment address, double stdDev) {
    logger.trace("std - address: {}, stdDev: {}", address, stdDev);
    Gaussian.std(address, stdDev);
  }

  public static double std(MemorySegment address) {
    logger.trace("std - address: {}", address);
    return Gaussian.std(address);
  }
}
