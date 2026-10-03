package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I256;
import io.github.rdlopes.tfhe.core.ffm.FheOps;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

@Generated
public final class FheInt256Array extends AbstractFheArray<FheInt256, FheInt256Array>
    implements FheArray<FheInt256, FheInt256Array> {

  public FheInt256Array(Collection<FheInt256> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint256_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint256_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int256_sum; }
  @Override protected FheInt256           newElement()       { return new FheInt256(); }
  @Override protected FheInt256Array      newArray(List<FheInt256> elements) { return new FheInt256Array(elements); }

  public static FheInt256Array encrypt(Collection<I256> values, ClientKey clientKey) {
    return new FheInt256Array(values.stream().map(v -> FheInt256.encrypt(v, clientKey)).toList());
  }
  public static FheInt256Array encrypt(Collection<I256> values, PublicKey publicKey) {
    return new FheInt256Array(values.stream().map(v -> FheInt256.encrypt(v, publicKey)).toList());
  }
  public static FheInt256Array encrypt(Collection<I256> values) {
    return new FheInt256Array(values.stream().map(FheInt256::encrypt).toList());
  }
}
