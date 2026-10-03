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
public final class FheInt4Array extends AbstractFheArray<FheInt4, FheInt4Array>
    implements FheArray<FheInt4, FheInt4Array> {

  public FheInt4Array(Collection<FheInt4> elements) { super(elements); }

  @Override protected FheOps.ArrayBinaryOp containsArrayOp() { return TfheHeader::fhe_uint4_array_contains_sub_slice; }
  @Override protected FheOps.ArrayBinaryOp equalsArrayOp()   { return TfheHeader::fhe_uint4_array_eq; }
  @Override protected FheOps.ArraySumOp    sumOp()           { return TfheHeader::fhe_int4_sum; }
  @Override protected FheInt4             newElement()       { return new FheInt4(); }
  @Override protected FheInt4Array        newArray(List<FheInt4> elements) { return new FheInt4Array(elements); }

  public static FheInt4Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt4Array(values.stream().map(v -> FheInt4.encrypt(v, clientKey)).toList());
  }
  public static FheInt4Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt4Array(values.stream().map(v -> FheInt4.encrypt(v, publicKey)).toList());
  }
  public static FheInt4Array encrypt(Collection<Byte> values) {
    return new FheInt4Array(values.stream().map(FheInt4::encrypt).toList());
  }
}
