package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheArray;
import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import java.util.Collection;
import java.util.List;

@Generated
public final class FheUint14Array extends AbstractFheArray<FheUint14, FheUint14Array>
    implements FheArray<FheUint14, FheUint14Array> {

  public FheUint14Array(Collection<FheUint14> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint14_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint14_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint14_sum; }
  @Override protected FheUint14            newElement()       { return new FheUint14(); }
  @Override protected FheUint14Array       newArray(List<FheUint14> elements) { return new FheUint14Array(elements); }

  public static FheUint14Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheUint14Array(values.stream().map(v -> FheUint14.encrypt(v, clientKey)).toList());
  }
  public static FheUint14Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheUint14Array(values.stream().map(v -> FheUint14.encrypt(v, publicKey)).toList());
  }
  public static FheUint14Array encrypt(Collection<Short> values) {
    return new FheUint14Array(values.stream().map(FheUint14::encrypt).toList());
  }
}
