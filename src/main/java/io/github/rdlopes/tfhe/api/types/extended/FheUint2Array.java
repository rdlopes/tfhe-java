package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

@Generated
public final class FheUint2Array extends AbstractFheArray<FheUint2, FheUint2Array>
    implements FheArray<FheUint2, FheUint2Array> {

  public FheUint2Array(Collection<FheUint2> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint2_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint2_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint2_sum; }
  @Override protected FheUint2             newElement()       { return new FheUint2(); }
  @Override protected FheUint2Array        newArray(List<FheUint2> elements) { return new FheUint2Array(elements); }

  public static FheUint2Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheUint2Array(values.stream().map(v -> FheUint2.encrypt(v, clientKey)).toList());
  }
  public static FheUint2Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheUint2Array(values.stream().map(v -> FheUint2.encrypt(v, publicKey)).toList());
  }
  public static FheUint2Array encrypt(Collection<Byte> values) {
    return new FheUint2Array(values.stream().map(FheUint2::encrypt).toList());
  }
}
