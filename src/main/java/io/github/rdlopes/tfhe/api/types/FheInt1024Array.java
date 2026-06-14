package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.I1024;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class FheInt1024Array extends NativeArray implements FheArray<FheInt1024, FheInt1024Array> {

  public FheInt1024Array(Collection<FheInt1024> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt1024Array other) {
    List<FheUint1024> lhsU = this.<FheInt1024>getElements().stream().map(e -> e.castInto(FheUint1024.class)).toList();
    List<FheUint1024> rhsU = other.<FheInt1024>getElements().stream().map(e -> e.castInto(FheUint1024.class)).toList();
    FheBool result = new FheUint1024Array(lhsU).containsArray(new FheUint1024Array(rhsU));
    lhsU.forEach(FheUint1024::destroy);
    rhsU.forEach(FheUint1024::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt1024Array other) {
    List<FheUint1024> lhsU = this.<FheInt1024>getElements().stream().map(e -> e.castInto(FheUint1024.class)).toList();
    List<FheUint1024> rhsU = other.<FheInt1024>getElements().stream().map(e -> e.castInto(FheUint1024.class)).toList();
    FheBool result = new FheUint1024Array(lhsU).equalsArray(new FheUint1024Array(rhsU));
    lhsU.forEach(FheUint1024::destroy);
    rhsU.forEach(FheUint1024::destroy);
    return result;
  }

  @Override
  public FheInt1024 sum() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int1024_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt1024Array add(FheInt1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt1024> a = this.getElements(); List<FheInt1024> b = other.getElements();
    List<FheInt1024> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt1024Array(r);
  }

  @Override
  public FheInt1024Array subtract(FheInt1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt1024> a = this.getElements(); List<FheInt1024> b = other.getElements();
    List<FheInt1024> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt1024Array(r);
  }

  public static FheInt1024Array encrypt(Collection<I1024> values, ClientKey clientKey) {
    return new FheInt1024Array(values.stream().map(v -> FheInt1024.encrypt(v, clientKey)).toList());
  }
  public static FheInt1024Array encrypt(Collection<I1024> values, PublicKey publicKey) {
    return new FheInt1024Array(values.stream().map(v -> FheInt1024.encrypt(v, publicKey)).toList());
  }
  public static FheInt1024Array encrypt(Collection<I1024> values) {
    return new FheInt1024Array(values.stream().map(FheInt1024::encrypt).toList());
  }
}
