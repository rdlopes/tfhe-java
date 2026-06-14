package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class FheInt10Array extends NativeArray implements FheArray<FheInt10, FheInt10Array> {

  public FheInt10Array(Collection<FheInt10> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt10Array other) {
    List<FheUint10> lhsU = this.<FheInt10>getElements().stream().map(e -> e.castInto(FheUint10.class)).toList();
    List<FheUint10> rhsU = other.<FheInt10>getElements().stream().map(e -> e.castInto(FheUint10.class)).toList();
    FheBool result = new FheUint10Array(lhsU).containsArray(new FheUint10Array(rhsU));
    lhsU.forEach(FheUint10::destroy);
    rhsU.forEach(FheUint10::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt10Array other) {
    List<FheUint10> lhsU = this.<FheInt10>getElements().stream().map(e -> e.castInto(FheUint10.class)).toList();
    List<FheUint10> rhsU = other.<FheInt10>getElements().stream().map(e -> e.castInto(FheUint10.class)).toList();
    FheBool result = new FheUint10Array(lhsU).equalsArray(new FheUint10Array(rhsU));
    lhsU.forEach(FheUint10::destroy);
    rhsU.forEach(FheUint10::destroy);
    return result;
  }

  @Override
  public FheInt10 sum() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int10_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt10Array add(FheInt10Array other) {
    sizeCheck(other);
    List<FheInt10> a = this.getElements();
    List<FheInt10> b = other.getElements();
    List<FheInt10> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt10Array(r);
  }

  @Override
  public FheInt10Array subtract(FheInt10Array other) {
    sizeCheck(other);
    List<FheInt10> a = this.getElements();
    List<FheInt10> b = other.getElements();
    List<FheInt10> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt10Array(r);
  }

  public static FheInt10Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt10Array(values.stream().map(v -> FheInt10.encrypt(v, clientKey)).toList());
  }
  public static FheInt10Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt10Array(values.stream().map(v -> FheInt10.encrypt(v, publicKey)).toList());
  }
  public static FheInt10Array encrypt(Collection<Short> values) {
    return new FheInt10Array(values.stream().map(FheInt10::encrypt).toList());
  }

  private void sizeCheck(FheInt10Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
