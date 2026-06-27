package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.core.ffm.FheOps;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

@Generated
public final class FheUint6Array extends AbstractFheArray<FheUint6, FheUint6Array>
    implements FheArray<FheUint6, FheUint6Array> {

  public FheUint6Array(Collection<FheUint6> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint6_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint6_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint6_sum; }
  @Override protected FheUint6             newElement()       { return new FheUint6(); }
  @Override protected FheUint6Array        newArray(List<FheUint6> elements) { return new FheUint6Array(elements); }

  public static FheUint6Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheUint6Array(values.stream().map(v -> FheUint6.encrypt(v, clientKey)).toList());
  }
  public static FheUint6Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheUint6Array(values.stream().map(v -> FheUint6.encrypt(v, publicKey)).toList());
  }
  public static FheUint6Array encrypt(Collection<Byte> values) {
    return new FheUint6Array(values.stream().map(FheUint6::encrypt).toList());
  }
}
