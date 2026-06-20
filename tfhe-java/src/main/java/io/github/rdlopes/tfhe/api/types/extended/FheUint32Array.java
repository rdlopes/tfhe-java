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
public final class FheUint32Array extends AbstractFheArray<FheUint32, FheUint32Array>
    implements FheArray<FheUint32, FheUint32Array> {

  public FheUint32Array(Collection<FheUint32> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint32_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint32_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint32_sum; }
  @Override protected FheUint32            newElement()       { return new FheUint32(); }
  @Override protected FheUint32Array       newArray(List<FheUint32> elements) { return new FheUint32Array(elements); }

  public static FheUint32Array encrypt(Collection<Integer> values, ClientKey clientKey) {
    return new FheUint32Array(values.stream().map(v -> FheUint32.encrypt(v, clientKey)).toList());
  }
  public static FheUint32Array encrypt(Collection<Integer> values, PublicKey publicKey) {
    return new FheUint32Array(values.stream().map(v -> FheUint32.encrypt(v, publicKey)).toList());
  }
  public static FheUint32Array encrypt(Collection<Integer> values) {
    return new FheUint32Array(values.stream().map(FheUint32::encrypt).toList());
  }
}
