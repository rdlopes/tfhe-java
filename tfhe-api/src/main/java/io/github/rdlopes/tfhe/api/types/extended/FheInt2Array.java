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
public final class FheInt2Array extends AbstractFheArray<FheInt2, FheInt2Array>
    implements FheArray<FheInt2, FheInt2Array> {

  public FheInt2Array(Collection<FheInt2> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint2_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint2_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int2_sum; }
  @Override protected FheInt2             newElement()       { return new FheInt2(); }
  @Override protected FheInt2Array        newArray(List<FheInt2> elements) { return new FheInt2Array(elements); }

  public static FheInt2Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt2Array(values.stream().map(v -> FheInt2.encrypt(v, clientKey)).toList());
  }
  public static FheInt2Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt2Array(values.stream().map(v -> FheInt2.encrypt(v, publicKey)).toList());
  }
  public static FheInt2Array encrypt(Collection<Byte> values) {
    return new FheInt2Array(values.stream().map(FheInt2::encrypt).toList());
  }
}
