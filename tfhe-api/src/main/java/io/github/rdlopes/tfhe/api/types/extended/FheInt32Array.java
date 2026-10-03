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
public final class FheInt32Array extends AbstractFheArray<FheInt32, FheInt32Array>
    implements FheArray<FheInt32, FheInt32Array> {

  public FheInt32Array(Collection<FheInt32> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint32_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint32_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int32_sum; }
  @Override protected FheInt32            newElement()       { return new FheInt32(); }
  @Override protected FheInt32Array       newArray(List<FheInt32> elements) { return new FheInt32Array(elements); }

  public static FheInt32Array encrypt(Collection<Integer> values, ClientKey clientKey) {
    return new FheInt32Array(values.stream().map(v -> FheInt32.encrypt(v, clientKey)).toList());
  }
  public static FheInt32Array encrypt(Collection<Integer> values, PublicKey publicKey) {
    return new FheInt32Array(values.stream().map(v -> FheInt32.encrypt(v, publicKey)).toList());
  }
  public static FheInt32Array encrypt(Collection<Integer> values) {
    return new FheInt32Array(values.stream().map(FheInt32::encrypt).toList());
  }
}
