package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

public final class FheUint12Array extends AbstractFheArray<FheUint12, FheUint12Array>
    implements FheArray<FheUint12, FheUint12Array> {

  public FheUint12Array(Collection<FheUint12> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint12_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint12_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint12_sum; }
  @Override protected FheUint12            newElement()       { return new FheUint12(); }
  @Override protected FheUint12Array       newArray(List<FheUint12> elements) { return new FheUint12Array(elements); }

  public static FheUint12Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheUint12Array(values.stream().map(v -> FheUint12.encrypt(v, clientKey)).toList());
  }
  public static FheUint12Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheUint12Array(values.stream().map(v -> FheUint12.encrypt(v, publicKey)).toList());
  }
  public static FheUint12Array encrypt(Collection<Short> values) {
    return new FheUint12Array(values.stream().map(FheUint12::encrypt).toList());
  }
}
