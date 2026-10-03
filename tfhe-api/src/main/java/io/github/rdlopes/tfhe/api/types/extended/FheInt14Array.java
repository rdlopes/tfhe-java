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
public final class FheInt14Array extends AbstractFheArray<FheInt14, FheInt14Array>
    implements FheArray<FheInt14, FheInt14Array> {

  public FheInt14Array(Collection<FheInt14> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint14_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint14_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int14_sum; }
  @Override protected FheInt14            newElement()       { return new FheInt14(); }
  @Override protected FheInt14Array       newArray(List<FheInt14> elements) { return new FheInt14Array(elements); }

  public static FheInt14Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt14Array(values.stream().map(v -> FheInt14.encrypt(v, clientKey)).toList());
  }
  public static FheInt14Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt14Array(values.stream().map(v -> FheInt14.encrypt(v, publicKey)).toList());
  }
  public static FheInt14Array encrypt(Collection<Short> values) {
    return new FheInt14Array(values.stream().map(FheInt14::encrypt).toList());
  }
}
