package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.I128;
import io.github.rdlopes.tfhe.core.ffm.FheOps;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.Generated;

import java.util.Collection;
import java.util.List;

/// Array of encrypted signed 128-bit integers.
@Generated
public final class FheInt128Array extends AbstractFheArray<FheInt128, FheInt128Array>
    implements FheArray<FheInt128, FheInt128Array> {

  public FheInt128Array(Collection<FheInt128> elements) {
    super(elements);
  }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint128_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint128_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int128_sum; }
  @Override protected FheInt128            newElement()       { return new FheInt128(); }
  @Override protected FheInt128Array       newArray(List<FheInt128> elements) { return new FheInt128Array(elements); }

  public static FheInt128Array encrypt(Collection<I128> values, ClientKey clientKey) {
    return new FheInt128Array(values.stream().map(v -> FheInt128.encrypt(v, clientKey)).toList());
  }

  public static FheInt128Array encrypt(Collection<I128> values, PublicKey publicKey) {
    return new FheInt128Array(values.stream().map(v -> FheInt128.encrypt(v, publicKey)).toList());
  }

  public static FheInt128Array encrypt(Collection<I128> values) {
    return new FheInt128Array(values.stream().map(FheInt128::encrypt).toList());
  }
}
