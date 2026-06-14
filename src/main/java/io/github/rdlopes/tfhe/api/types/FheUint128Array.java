package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U128;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

public final class FheUint128Array extends AbstractFheArray<FheUint128, FheUint128Array>
    implements FheArray<FheUint128, FheUint128Array> {

  public FheUint128Array(Collection<FheUint128> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint128_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint128_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint128_sum; }
  @Override protected FheUint128           newElement()       { return new FheUint128(); }
  @Override protected FheUint128Array      newArray(List<FheUint128> elements) { return new FheUint128Array(elements); }

  public static FheUint128Array encrypt(Collection<U128> values, ClientKey clientKey) {
    return new FheUint128Array(values.stream().map(v -> FheUint128.encrypt(v, clientKey)).toList());
  }
  public static FheUint128Array encrypt(Collection<U128> values, PublicKey publicKey) {
    return new FheUint128Array(values.stream().map(v -> FheUint128.encrypt(v, publicKey)).toList());
  }
  public static FheUint128Array encrypt(Collection<U128> values) {
    return new FheUint128Array(values.stream().map(FheUint128::encrypt).toList());
  }
}
