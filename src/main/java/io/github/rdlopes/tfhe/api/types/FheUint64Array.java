package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

public final class FheUint64Array extends AbstractFheArray<FheUint64, FheUint64Array>
    implements FheArray<FheUint64, FheUint64Array> {

  public FheUint64Array(Collection<FheUint64> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint64_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint64_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint64_sum; }
  @Override protected FheUint64             newElement()       { return new FheUint64(); }
  @Override protected FheUint64Array        newArray(List<FheUint64> elements) { return new FheUint64Array(elements); }

  public static FheUint64Array encrypt(Collection<Long> values, ClientKey clientKey) {
    return new FheUint64Array(values.stream().map(v -> FheUint64.encrypt(v, clientKey)).toList());
  }
  public static FheUint64Array encrypt(Collection<Long> values, PublicKey publicKey) {
    return new FheUint64Array(values.stream().map(v -> FheUint64.encrypt(v, publicKey)).toList());
  }
  public static FheUint64Array encrypt(Collection<Long> values) {
    return new FheUint64Array(values.stream().map(FheUint64::encrypt).toList());
  }
}
