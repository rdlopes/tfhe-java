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
public final class FheInt12Array extends AbstractFheArray<FheInt12, FheInt12Array>
    implements FheArray<FheInt12, FheInt12Array> {

  public FheInt12Array(Collection<FheInt12> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint12_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint12_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int12_sum; }
  @Override protected FheInt12            newElement()       { return new FheInt12(); }
  @Override protected FheInt12Array       newArray(List<FheInt12> elements) { return new FheInt12Array(elements); }

  public static FheInt12Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt12Array(values.stream().map(v -> FheInt12.encrypt(v, clientKey)).toList());
  }
  public static FheInt12Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt12Array(values.stream().map(v -> FheInt12.encrypt(v, publicKey)).toList());
  }
  public static FheInt12Array encrypt(Collection<Short> values) {
    return new FheInt12Array(values.stream().map(FheInt12::encrypt).toList());
  }
}
