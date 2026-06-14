package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

public final class FheUint10Array extends AbstractFheArray<FheUint10, FheUint10Array>
    implements FheArray<FheUint10, FheUint10Array> {

  public FheUint10Array(Collection<FheUint10> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint10_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint10_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint10_sum; }
  @Override protected FheUint10            newElement()       { return new FheUint10(); }
  @Override protected FheUint10Array       newArray(List<FheUint10> elements) { return new FheUint10Array(elements); }

  public static FheUint10Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheUint10Array(values.stream().map(v -> FheUint10.encrypt(v, clientKey)).toList());
  }
  public static FheUint10Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheUint10Array(values.stream().map(v -> FheUint10.encrypt(v, publicKey)).toList());
  }
  public static FheUint10Array encrypt(Collection<Short> values) {
    return new FheUint10Array(values.stream().map(FheUint10::encrypt).toList());
  }
}
