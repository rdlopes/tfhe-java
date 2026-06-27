package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.core.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int2_sum;

@Generated
public final class FheInt2Array extends NativeArray implements FheArray<FheInt2, FheInt2Array> {

  public FheInt2Array(Collection<FheInt2> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt2Array other) {
    List<FheUint2> lhsU = this.<FheInt2>getElements().stream().map(e -> e.castInto(FheUint2.class)).toList();
    List<FheUint2> rhsU = other.<FheInt2>getElements().stream().map(e -> e.castInto(FheUint2.class)).toList();
    try (FheUint2Array lhsArr = new FheUint2Array(lhsU);
         FheUint2Array rhsArr = new FheUint2Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint2::destroy);
      rhsU.forEach(FheUint2::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt2Array other) {
    List<FheUint2> lhsU = this.<FheInt2>getElements().stream().map(e -> e.castInto(FheUint2.class)).toList();
    List<FheUint2> rhsU = other.<FheInt2>getElements().stream().map(e -> e.castInto(FheUint2.class)).toList();
    try (FheUint2Array lhsArr = new FheUint2Array(lhsU);
         FheUint2Array rhsArr = new FheUint2Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint2::destroy);
      rhsU.forEach(FheUint2::destroy);
      return result;
    }
  }

  @Override
  public FheInt2 sum() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int2_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt2Array add(FheInt2Array other) {
    sizeCheck(other);
    List<FheInt2> a = this.getElements();
    List<FheInt2> b = other.getElements();
    List<FheInt2> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt2Array(r);
  }

  @Override
  public FheInt2Array subtract(FheInt2Array other) {
    sizeCheck(other);
    List<FheInt2> a = this.getElements();
    List<FheInt2> b = other.getElements();
    List<FheInt2> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt2Array(r);
  }

  public static FheInt2Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt2Array(values.stream().map(v -> FheInt2.encrypt(v, clientKey)).toList());
  }
  public static FheInt2Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt2Array(values.stream().map(v -> FheInt2.encrypt(v, publicKey)).toList());
  }
  public static FheInt2Array encrypt(Collection<Byte> values) {
    return new FheInt2Array(values.stream().map(FheInt2::encrypt).toList());
  }

  private void sizeCheck(FheInt2Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
