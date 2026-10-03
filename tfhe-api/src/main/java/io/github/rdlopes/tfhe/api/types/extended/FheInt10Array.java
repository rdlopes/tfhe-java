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
public final class FheInt10Array extends AbstractFheArray<FheInt10, FheInt10Array>
    implements FheArray<FheInt10, FheInt10Array> {

  public FheInt10Array(Collection<FheInt10> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint10_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint10_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int10_sum; }
  @Override protected FheInt10            newElement()       { return new FheInt10(); }
  @Override protected FheInt10Array       newArray(List<FheInt10> elements) { return new FheInt10Array(elements); }

  public static FheInt10Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt10Array(values.stream().map(v -> FheInt10.encrypt(v, clientKey)).toList());
  }
  public static FheInt10Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt10Array(values.stream().map(v -> FheInt10.encrypt(v, publicKey)).toList());
  }
  public static FheInt10Array encrypt(Collection<Short> values) {
    return new FheInt10Array(values.stream().map(FheInt10::encrypt).toList());
  }
}
