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
public final class FheUint4Array extends AbstractFheArray<FheUint4, FheUint4Array>
    implements FheArray<FheUint4, FheUint4Array> {

  public FheUint4Array(Collection<FheUint4> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint4_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint4_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_uint4_sum; }
  @Override protected FheUint4             newElement()       { return new FheUint4(); }
  @Override protected FheUint4Array        newArray(List<FheUint4> elements) { return new FheUint4Array(elements); }

  public static FheUint4Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheUint4Array(values.stream().map(v -> FheUint4.encrypt(v, clientKey)).toList());
  }
  public static FheUint4Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheUint4Array(values.stream().map(v -> FheUint4.encrypt(v, publicKey)).toList());
  }
  public static FheUint4Array encrypt(Collection<Byte> values) {
    return new FheUint4Array(values.stream().map(FheUint4::encrypt).toList());
  }
}
