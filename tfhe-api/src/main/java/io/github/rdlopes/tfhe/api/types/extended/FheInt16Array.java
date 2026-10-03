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
public final class FheInt16Array extends AbstractFheArray<FheInt16, FheInt16Array>
    implements FheArray<FheInt16, FheInt16Array> {

  public FheInt16Array(Collection<FheInt16> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint16_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint16_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int16_sum; }
  @Override protected FheInt16            newElement()       { return new FheInt16(); }
  @Override protected FheInt16Array       newArray(List<FheInt16> elements) { return new FheInt16Array(elements); }

  public static FheInt16Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt16Array(values.stream().map(v -> FheInt16.encrypt(v, clientKey)).toList());
  }
  public static FheInt16Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt16Array(values.stream().map(v -> FheInt16.encrypt(v, publicKey)).toList());
  }
  public static FheInt16Array encrypt(Collection<Short> values) {
    return new FheInt16Array(values.stream().map(FheInt16::encrypt).toList());
  }
}
