package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.core.ffm.FheOps;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.Generated;

import java.util.Collection;
import java.util.List;

/// Array of encrypted signed 8-bit integers.
@Generated
public final class FheInt8Array extends AbstractFheArray<FheInt8, FheInt8Array>
    implements FheArray<FheInt8, FheInt8Array> {

  public FheInt8Array(Collection<FheInt8> elements) {
    super(elements);
  }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint8_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint8_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int8_sum; }
  @Override protected FheInt8             newElement()       { return new FheInt8(); }
  @Override protected FheInt8Array        newArray(List<FheInt8> elements) { return new FheInt8Array(elements); }

  public static FheInt8Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt8Array(values.stream().map(v -> FheInt8.encrypt(v, clientKey)).toList());
  }

  public static FheInt8Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt8Array(values.stream().map(v -> FheInt8.encrypt(v, publicKey)).toList());
  }

  public static FheInt8Array encrypt(Collection<Byte> values) {
    return new FheInt8Array(values.stream().map(FheInt8::encrypt).toList());
  }
}
