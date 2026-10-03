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
public final class FheInt6Array extends AbstractFheArray<FheInt6, FheInt6Array>
    implements FheArray<FheInt6, FheInt6Array> {

  public FheInt6Array(Collection<FheInt6> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint6_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint6_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int6_sum; }
  @Override protected FheInt6             newElement()       { return new FheInt6(); }
  @Override protected FheInt6Array        newArray(List<FheInt6> elements) { return new FheInt6Array(elements); }

  public static FheInt6Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt6Array(values.stream().map(v -> FheInt6.encrypt(v, clientKey)).toList());
  }
  public static FheInt6Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt6Array(values.stream().map(v -> FheInt6.encrypt(v, publicKey)).toList());
  }
  public static FheInt6Array encrypt(Collection<Byte> values) {
    return new FheInt6Array(values.stream().map(FheInt6::encrypt).toList());
  }
}
