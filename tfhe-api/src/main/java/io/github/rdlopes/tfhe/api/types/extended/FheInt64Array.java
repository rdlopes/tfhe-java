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
public final class FheInt64Array extends AbstractFheArray<FheInt64, FheInt64Array>
    implements FheArray<FheInt64, FheInt64Array> {

  public FheInt64Array(Collection<FheInt64> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint64_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint64_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int64_sum; }
  @Override protected FheInt64            newElement()       { return new FheInt64(); }
  @Override protected FheInt64Array       newArray(List<FheInt64> elements) { return new FheInt64Array(elements); }

  public static FheInt64Array encrypt(Collection<Long> values, ClientKey clientKey) {
    return new FheInt64Array(values.stream().map(v -> FheInt64.encrypt(v, clientKey)).toList());
  }
  public static FheInt64Array encrypt(Collection<Long> values, PublicKey publicKey) {
    return new FheInt64Array(values.stream().map(v -> FheInt64.encrypt(v, publicKey)).toList());
  }
  public static FheInt64Array encrypt(Collection<Long> values) {
    return new FheInt64Array(values.stream().map(FheInt64::encrypt).toList());
  }
}
