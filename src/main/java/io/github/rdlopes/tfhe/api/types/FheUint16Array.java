package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

public final class FheUint16Array extends AbstractFheArray<FheUint16, FheUint16Array>
    implements FheArray<FheUint16, FheUint16Array> {

  public FheUint16Array(Collection<FheUint16> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint16_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint16_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint16_sum; }
  @Override protected FheUint16            newElement()       { return new FheUint16(); }
  @Override protected FheUint16Array       newArray(List<FheUint16> elements) { return new FheUint16Array(elements); }

  public static FheUint16Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheUint16Array(values.stream().map(v -> FheUint16.encrypt(v, clientKey)).toList());
  }
  public static FheUint16Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheUint16Array(values.stream().map(v -> FheUint16.encrypt(v, publicKey)).toList());
  }
  public static FheUint16Array encrypt(Collection<Short> values) {
    return new FheUint16Array(values.stream().map(FheUint16::encrypt).toList());
  }
}
