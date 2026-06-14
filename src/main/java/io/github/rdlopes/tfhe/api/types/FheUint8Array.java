package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

/// Array of encrypted unsigned 8-bit integers.
public final class FheUint8Array extends AbstractFheArray<FheUint8, FheUint8Array>
    implements FheArray<FheUint8, FheUint8Array> {

  public FheUint8Array(Collection<FheUint8> elements) {
    super(elements);
  }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint8_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint8_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint8_sum; }
  @Override protected FheUint8             newElement()       { return new FheUint8(); }
  @Override protected FheUint8Array        newArray(List<FheUint8> elements) { return new FheUint8Array(elements); }

  public static FheUint8Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheUint8Array(values.stream().map(v -> FheUint8.encrypt(v, clientKey)).toList());
  }

  public static FheUint8Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheUint8Array(values.stream().map(v -> FheUint8.encrypt(v, publicKey)).toList());
  }

  public static FheUint8Array encrypt(Collection<Byte> values) {
    return new FheUint8Array(values.stream().map(FheUint8::encrypt).toList());
  }
}
