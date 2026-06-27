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
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int12_sum;

@Generated
public final class FheInt12Array extends NativeArray implements FheArray<FheInt12, FheInt12Array> {

  public FheInt12Array(Collection<FheInt12> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt12Array other) {
    List<FheUint12> lhsU = this.<FheInt12>getElements().stream().map(e -> e.castInto(FheUint12.class)).toList();
    List<FheUint12> rhsU = other.<FheInt12>getElements().stream().map(e -> e.castInto(FheUint12.class)).toList();
    try (FheUint12Array lhsArr = new FheUint12Array(lhsU);
         FheUint12Array rhsArr = new FheUint12Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint12::destroy);
      rhsU.forEach(FheUint12::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt12Array other) {
    List<FheUint12> lhsU = this.<FheInt12>getElements().stream().map(e -> e.castInto(FheUint12.class)).toList();
    List<FheUint12> rhsU = other.<FheInt12>getElements().stream().map(e -> e.castInto(FheUint12.class)).toList();
    try (FheUint12Array lhsArr = new FheUint12Array(lhsU);
         FheUint12Array rhsArr = new FheUint12Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint12::destroy);
      rhsU.forEach(FheUint12::destroy);
      return result;
    }
  }

  @Override
  public FheInt12 sum() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt12Array add(FheInt12Array other) {
    sizeCheck(other);
    List<FheInt12> a = this.getElements();
    List<FheInt12> b = other.getElements();
    List<FheInt12> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt12Array(r);
  }

  @Override
  public FheInt12Array subtract(FheInt12Array other) {
    sizeCheck(other);
    List<FheInt12> a = this.getElements();
    List<FheInt12> b = other.getElements();
    List<FheInt12> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt12Array(r);
  }

  public static FheInt12Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt12Array(values.stream().map(v -> FheInt12.encrypt(v, clientKey)).toList());
  }
  public static FheInt12Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt12Array(values.stream().map(v -> FheInt12.encrypt(v, publicKey)).toList());
  }
  public static FheInt12Array encrypt(Collection<Short> values) {
    return new FheInt12Array(values.stream().map(FheInt12::encrypt).toList());
  }

  private void sizeCheck(FheInt12Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
