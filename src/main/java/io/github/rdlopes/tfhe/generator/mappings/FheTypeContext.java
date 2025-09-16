package io.github.rdlopes.tfhe.generator.mappings;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.generator.templates.SourceContext;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FheTypeContext extends SourceContext {
  private final String nativePrefix;
  private final boolean signed;
  private final int bitSize;
  private final Class<?> dataClass;
  private final Class<?> valueClass;

  public FheTypeContext(String packageName, String className, Class<?> superClass, String nativePrefix, int bitSize) {
    super(packageName, className, superClass);
    this.nativePrefix = nativePrefix;
    this.signed = FheTypeMapper.isSigned(nativePrefix);
    this.bitSize = bitSize;
    int bitLength = FheTypeMapper.bitLength(bitSize);
    this.dataClass = FheTypeMapper.dataClass(nativePrefix, bitSize);
    this.valueClass = FheTypeMapper.valueClass(bitLength, signed);
  }

  public String getNativePrefix() {
    return nativePrefix;
  }

  public Class<?> getValueClass() {
    return valueClass;
  }

  public Class<?> getDataClass() {
    return dataClass;
  }

  public int getBitSize() {
    return bitSize;
  }

  public boolean isSigned() {
    return signed;
  }

  public boolean isHighBitSize() {
    return this.dataClass.equals(BigInteger.class);
  }

  public String getCompressedClassName() {
    return "Compressed" + getClassName();
  }

  @Override
  public Collection<Class<?>> getInterfaces() {
    Collection<Class<?>> interfaces = new ArrayList<>(List.of(
      FheEncryption.class,
      FhePublicEncryption.class,
      FheTrivialEncryption.class,
      FheDecryption.class,
      FheCompression.class,
      FheSerialization.class,
      FheSafeSerialization.class,
      FheCloneable.class,
      FheEquality.class,
      FheScalarEquality.class,
      FheLogic.class,
      FheLogicAssign.class,
      FheScalarLogic.class,
      FheScalarLogicAssign.class
    ));

    if (nativePrefix.startsWith("fhe_int")
      || nativePrefix.startsWith("fhe_uint")) {
      interfaces.add(FheBitwise.class);
      interfaces.add(FheBitwiseAssign.class);
      interfaces.add(FheScalarBitwise.class);
      interfaces.add(FheScalarBitwiseAssign.class);
      interfaces.add(FheIfThenElse.class);
      interfaces.add(FheArithmetics.class);
      interfaces.add(FheArithmeticsAssign.class);
      interfaces.add(FheScalarArithmetics.class);
      interfaces.add(FheScalarArithmeticsAssign.class);
      interfaces.add(FheComparison.class);
      interfaces.add(FheScalarComparison.class);
    }

    return interfaces;
  }

  @Override
  public Collection<String> getImports() {
    Collection<String> imports = new ArrayList<>(super.getImports());

    imports.add(DynamicBuffer.class.getName());
    imports.add(DynamicBufferView.class.getName());

    if (isHighBitSize()) {
      imports.add(getDataClass().getName());
      imports.add(getValueClass().getName());
    }

    return imports;
  }

}
