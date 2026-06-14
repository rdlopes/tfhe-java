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

public final class FheInt6Array extends NativeArray implements FheArray<FheInt6, FheInt6Array> {

  public FheInt6Array(Collection<FheInt6> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt6Array other) {
    List<FheUint6> lhsU = this.<FheInt6>getElements().stream().map(e -> e.castInto(FheUint6.class)).toList();
    List<FheUint6> rhsU = other.<FheInt6>getElements().stream().map(e -> e.castInto(FheUint6.class)).toList();
    FheBool result = new FheUint6Array(lhsU).containsArray(new FheUint6Array(rhsU));
    lhsU.forEach(FheUint6::destroy);
    rhsU.forEach(FheUint6::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt6Array other) {
    List<FheUint6> lhsU = this.<FheInt6>getElements().stream().map(e -> e.castInto(FheUint6.class)).toList();
    List<FheUint6> rhsU = other.<FheInt6>getElements().stream().map(e -> e.castInto(FheUint6.class)).toList();
    FheBool result = new FheUint6Array(lhsU).equalsArray(new FheUint6Array(rhsU));
    lhsU.forEach(FheUint6::destroy);
    rhsU.forEach(FheUint6::destroy);
    return result;
  }

  @Override
  public FheInt6 sum() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int6_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt6Array add(FheInt6Array other) {
    sizeCheck(other);
    List<FheInt6> a = this.getElements();
    List<FheInt6> b = other.getElements();
    List<FheInt6> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt6Array(r);
  }

  @Override
  public FheInt6Array subtract(FheInt6Array other) {
    sizeCheck(other);
    List<FheInt6> a = this.getElements();
    List<FheInt6> b = other.getElements();
    List<FheInt6> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt6Array(r);
  }

  public static FheInt6Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt6Array(values.stream().map(v -> FheInt6.encrypt(v, clientKey)).toList());
  }
  public static FheInt6Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt6Array(values.stream().map(v -> FheInt6.encrypt(v, publicKey)).toList());
  }
  public static FheInt6Array encrypt(Collection<Byte> values) {
    return new FheInt6Array(values.stream().map(FheInt6::encrypt).toList());
  }

  private void sizeCheck(FheInt6Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
