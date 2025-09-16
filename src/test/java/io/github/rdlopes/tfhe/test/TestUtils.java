package io.github.rdlopes.tfhe.test;

import java.math.BigInteger;

public class TestUtils {

  public static BigInteger rotateLeft(BigInteger value, int shift, int bitSize) {
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger topBits = value.shiftRight(bitSize - shift);
    return value.shiftLeft(shift)
                .or(topBits)
                .and(mask);
  }

  public static BigInteger rotateRight(BigInteger value, int shift, int bitSize) {
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger bottomBits = value.and(BigInteger.ONE.shiftLeft(shift)
                                                    .subtract(BigInteger.ONE));
    return value.shiftRight(shift)
                .or(bottomBits.shiftLeft(bitSize - shift))
                .and(mask);
  }

  public static byte rotateLeft(byte value, int shift, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int effectiveShift = shift % bitSize;
    int topBits = unsignedValue >>> (bitSize - effectiveShift);
    return (byte) (((unsignedValue << effectiveShift) | topBits) & mask);
  }

  public static byte rotateRight(byte value, int shift, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int effectiveShift = shift % bitSize;
    int bottomBits = unsignedValue & ((1 << effectiveShift) - 1);
    return (byte) (((unsignedValue >>> effectiveShift) | (bottomBits << (bitSize - effectiveShift))) & mask);
  }

  public static short rotateLeft(short value, int shift, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int effectiveShift = shift % bitSize;
    int topBits = unsignedValue >>> (bitSize - effectiveShift);
    return (short) (((unsignedValue << effectiveShift) | topBits) & mask);
  }

  public static short rotateRight(short value, int shift, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int effectiveShift = shift % bitSize;
    int bottomBits = unsignedValue & ((1 << effectiveShift) - 1);
    return (short) (((unsignedValue >>> effectiveShift) | (bottomBits << (bitSize - effectiveShift))) & mask);
  }

  public static byte leadingZeros(byte value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return (byte) bitSize;
    }
    int leadingZeros = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      leadingZeros++;
    }
    return (byte) leadingZeros;
  }

  public static byte leadingOnes(byte value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int leadingOnes = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      leadingOnes++;
    }
    return (byte) leadingOnes;
  }

  public static byte trailingZeros(byte value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return (byte) bitSize;
    }
    int trailingZeros = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      trailingZeros++;
    }
    return (byte) trailingZeros;
  }

  public static byte trailingOnes(byte value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int trailingOnes = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      trailingOnes++;
    }
    return (byte) trailingOnes;
  }

  public static short leadingZeros(short value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return (short) bitSize;
    }
    int leadingZeros = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      leadingZeros++;
    }
    return (short) leadingZeros;
  }

  public static short leadingOnes(short value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int leadingOnes = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      leadingOnes++;
    }
    return (short) leadingOnes;
  }

  public static short trailingZeros(short value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return (short) bitSize;
    }
    int trailingZeros = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      trailingZeros++;
    }
    return (short) trailingZeros;
  }

  public static short trailingOnes(short value, int bitSize) {
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int trailingOnes = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      trailingOnes++;
    }
    return (short) trailingOnes;
  }

  public static int leadingZeros(int value, int bitSize) {
    if (bitSize > 32) {
      throw new IllegalArgumentException("bitSize cannot be greater than 32 for int");
    }
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return bitSize;
    }
    int leadingZeros = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      leadingZeros++;
    }
    return leadingZeros;
  }

  public static int leadingOnes(int value, int bitSize) {
    if (bitSize > 32) {
      throw new IllegalArgumentException("bitSize cannot be greater than 32 for int");
    }
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int leadingOnes = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      leadingOnes++;
    }
    return leadingOnes;
  }

  public static int trailingZeros(int value, int bitSize) {
    if (bitSize > 32) {
      throw new IllegalArgumentException("bitSize cannot be greater than 32 for int");
    }
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return bitSize;
    }
    int trailingZeros = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) != 0) {
        break;
      }
      trailingZeros++;
    }
    return trailingZeros;
  }

  public static int trailingOnes(int value, int bitSize) {
    if (bitSize > 32) {
      throw new IllegalArgumentException("bitSize cannot be greater than 32 for int");
    }
    int mask = (1 << bitSize) - 1;
    int unsignedValue = value & mask;
    int trailingOnes = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1 << i)) == 0) {
        break;
      }
      trailingOnes++;
    }
    return trailingOnes;
  }

  public static long leadingZeros(long value, int bitSize) {
    if (bitSize > 64) {
      throw new IllegalArgumentException("bitSize cannot be greater than 64 for long");
    }
    long mask = (1L << bitSize) - 1;
    long unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return bitSize;
    }
    int leadingZeros = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1L << i)) != 0) {
        break;
      }
      leadingZeros++;
    }
    return leadingZeros;
  }

  public static long leadingOnes(long value, int bitSize) {
    if (bitSize > 64) {
      throw new IllegalArgumentException("bitSize cannot be greater than 64 for long");
    }
    long mask = (1L << bitSize) - 1;
    long unsignedValue = value & mask;
    int leadingOnes = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if ((unsignedValue & (1L << i)) == 0) {
        break;
      }
      leadingOnes++;
    }
    return leadingOnes;
  }

  public static long trailingZeros(long value, int bitSize) {
    if (bitSize > 64) {
      throw new IllegalArgumentException("bitSize cannot be greater than 64 for long");
    }
    long mask = (1L << bitSize) - 1;
    long unsignedValue = value & mask;
    if (unsignedValue == 0) {
      return bitSize;
    }
    int trailingZeros = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1L << i)) != 0) {
        break;
      }
      trailingZeros++;
    }
    return trailingZeros;
  }

  public static long trailingOnes(long value, int bitSize) {
    if (bitSize > 64) {
      throw new IllegalArgumentException("bitSize cannot be greater than 64 for long");
    }
    long mask = (1L << bitSize) - 1;
    long unsignedValue = value & mask;
    int trailingOnes = 0;
    for (int i = 0; i < bitSize; i++) {
      if ((unsignedValue & (1L << i)) == 0) {
        break;
      }
      trailingOnes++;
    }
    return trailingOnes;
  }

  public static int leadingOnes(BigInteger value, int bitSize) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("BigInteger value must be non-negative");
    }
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger maskedValue = value.and(mask);
    int leadingOnes = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if (!maskedValue.testBit(i)) {
        break;
      }
      leadingOnes++;
    }
    return leadingOnes;
  }

  public static int trailingOnes(BigInteger value, int bitSize) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("BigInteger value must be non-negative");
    }
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger maskedValue = value.and(mask);
    int trailingOnes = 0;
    for (int i = 0; i < bitSize; i++) {
      if (!maskedValue.testBit(i)) {
        break;
      }
      trailingOnes++;
    }
    return trailingOnes;
  }

  public static int leadingZeros(BigInteger value, int bitSize) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("BigInteger value must be non-negative");
    }
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger maskedValue = value.and(mask);
    if (maskedValue.equals(BigInteger.ZERO)) {
      return bitSize;
    }
    int leadingZeros = 0;
    for (int i = bitSize - 1; i >= 0; i--) {
      if (maskedValue.testBit(i)) {
        break;
      }
      leadingZeros++;
    }
    return leadingZeros;
  }

  public static int trailingZeros(BigInteger value, int bitSize) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("BigInteger value must be non-negative");
    }
    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize)
                                    .subtract(BigInteger.ONE);
    BigInteger maskedValue = value.and(mask);
    if (maskedValue.equals(BigInteger.ZERO)) {
      return bitSize;
    }
    int trailingZeros = 0;
    for (int i = 0; i < bitSize; i++) {
      if (maskedValue.testBit(i)) {
        break;
      }
      trailingZeros++;
    }
    return trailingZeros;
  }

}
