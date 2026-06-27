package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.U256;
import io.github.rdlopes.tfhe.core.ffm.FheOps;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

@Generated
public final class FheUint256Array extends AbstractFheArray<FheUint256, FheUint256Array>
    implements FheArray<FheUint256, FheUint256Array> {

  public FheUint256Array(Collection<FheUint256> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint256_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint256_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint256_sum; }
  @Override protected FheUint256           newElement()       { return new FheUint256(); }
  @Override protected FheUint256Array      newArray(List<FheUint256> elements) { return new FheUint256Array(elements); }

  public static FheUint256Array encrypt(Collection<U256> values, ClientKey clientKey) {
    return new FheUint256Array(values.stream().map(v -> FheUint256.encrypt(v, clientKey)).toList());
  }
  public static FheUint256Array encrypt(Collection<U256> values, PublicKey publicKey) {
    return new FheUint256Array(values.stream().map(v -> FheUint256.encrypt(v, publicKey)).toList());
  }
  public static FheUint256Array encrypt(Collection<U256> values) {
    return new FheUint256Array(values.stream().map(FheUint256::encrypt).toList());
  }
}
