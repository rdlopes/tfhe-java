package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int4_sum;

@Generated
public final class FheInt4Array extends NativeArray implements FheArray<FheInt4, FheInt4Array> {

  public FheInt4Array(Collection<FheInt4> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt4Array other) {
    List<FheUint4> lhsU = this.<FheInt4>getElements().stream().map(e -> e.castInto(FheUint4.class)).toList();
    List<FheUint4> rhsU = other.<FheInt4>getElements().stream().map(e -> e.castInto(FheUint4.class)).toList();
    try (FheUint4Array lhsArr = new FheUint4Array(lhsU);
         FheUint4Array rhsArr = new FheUint4Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint4::destroy);
      rhsU.forEach(FheUint4::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt4Array other) {
    List<FheUint4> lhsU = this.<FheInt4>getElements().stream().map(e -> e.castInto(FheUint4.class)).toList();
    List<FheUint4> rhsU = other.<FheInt4>getElements().stream().map(e -> e.castInto(FheUint4.class)).toList();
    try (FheUint4Array lhsArr = new FheUint4Array(lhsU);
         FheUint4Array rhsArr = new FheUint4Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint4::destroy);
      rhsU.forEach(FheUint4::destroy);
      return result;
    }
  }

  @Override
  public FheInt4 sum() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt4Array add(FheInt4Array other) {
    sizeCheck(other);
    List<FheInt4> a = this.getElements();
    List<FheInt4> b = other.getElements();
    List<FheInt4> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt4Array(r);
  }

  @Override
  public FheInt4Array subtract(FheInt4Array other) {
    sizeCheck(other);
    List<FheInt4> a = this.getElements();
    List<FheInt4> b = other.getElements();
    List<FheInt4> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt4Array(r);
  }

  public static FheInt4Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt4Array(values.stream().map(v -> FheInt4.encrypt(v, clientKey)).toList());
  }
  public static FheInt4Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt4Array(values.stream().map(v -> FheInt4.encrypt(v, publicKey)).toList());
  }
  public static FheInt4Array encrypt(Collection<Byte> values) {
    return new FheInt4Array(values.stream().map(FheInt4::encrypt).toList());
  }

  private void sizeCheck(FheInt4Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
