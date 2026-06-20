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
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int32_sum;

@Generated
public final class FheInt32Array extends NativeArray implements FheArray<FheInt32, FheInt32Array> {

  public FheInt32Array(Collection<FheInt32> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt32Array other) {
    List<FheUint32> lhsU = this.<FheInt32>getElements().stream().map(e -> e.castInto(FheUint32.class)).toList();
    List<FheUint32> rhsU = other.<FheInt32>getElements().stream().map(e -> e.castInto(FheUint32.class)).toList();
    try (FheUint32Array lhsArr = new FheUint32Array(lhsU);
         FheUint32Array rhsArr = new FheUint32Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint32::destroy);
      rhsU.forEach(FheUint32::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt32Array other) {
    List<FheUint32> lhsU = this.<FheInt32>getElements().stream().map(e -> e.castInto(FheUint32.class)).toList();
    List<FheUint32> rhsU = other.<FheInt32>getElements().stream().map(e -> e.castInto(FheUint32.class)).toList();
    try (FheUint32Array lhsArr = new FheUint32Array(lhsU);
         FheUint32Array rhsArr = new FheUint32Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint32::destroy);
      rhsU.forEach(FheUint32::destroy);
      return result;
    }
  }

  @Override
  public FheInt32 sum() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int32_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt32Array add(FheInt32Array other) {
    sizeCheck(other);
    List<FheInt32> a = this.getElements();
    List<FheInt32> b = other.getElements();
    List<FheInt32> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt32Array(r);
  }

  @Override
  public FheInt32Array subtract(FheInt32Array other) {
    sizeCheck(other);
    List<FheInt32> a = this.getElements();
    List<FheInt32> b = other.getElements();
    List<FheInt32> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt32Array(r);
  }

  public static FheInt32Array encrypt(Collection<Integer> values, ClientKey clientKey) {
    return new FheInt32Array(values.stream().map(v -> FheInt32.encrypt(v, clientKey)).toList());
  }
  public static FheInt32Array encrypt(Collection<Integer> values, PublicKey publicKey) {
    return new FheInt32Array(values.stream().map(v -> FheInt32.encrypt(v, publicKey)).toList());
  }
  public static FheInt32Array encrypt(Collection<Integer> values) {
    return new FheInt32Array(values.stream().map(FheInt32::encrypt).toList());
  }

  private void sizeCheck(FheInt32Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
