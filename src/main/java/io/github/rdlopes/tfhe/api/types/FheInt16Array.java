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

public final class FheInt16Array extends NativeArray implements FheArray<FheInt16, FheInt16Array> {

  public FheInt16Array(Collection<FheInt16> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt16Array other) {
    List<FheUint16> lhsU = this.<FheInt16>getElements().stream().map(e -> e.castInto(FheUint16.class)).toList();
    List<FheUint16> rhsU = other.<FheInt16>getElements().stream().map(e -> e.castInto(FheUint16.class)).toList();
    FheBool result = new FheUint16Array(lhsU).containsArray(new FheUint16Array(rhsU));
    lhsU.forEach(FheUint16::destroy);
    rhsU.forEach(FheUint16::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt16Array other) {
    List<FheUint16> lhsU = this.<FheInt16>getElements().stream().map(e -> e.castInto(FheUint16.class)).toList();
    List<FheUint16> rhsU = other.<FheInt16>getElements().stream().map(e -> e.castInto(FheUint16.class)).toList();
    FheBool result = new FheUint16Array(lhsU).equalsArray(new FheUint16Array(rhsU));
    lhsU.forEach(FheUint16::destroy);
    rhsU.forEach(FheUint16::destroy);
    return result;
  }

  @Override
  public FheInt16 sum() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int16_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt16Array add(FheInt16Array other) {
    sizeCheck(other);
    List<FheInt16> a = this.getElements();
    List<FheInt16> b = other.getElements();
    List<FheInt16> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt16Array(r);
  }

  @Override
  public FheInt16Array subtract(FheInt16Array other) {
    sizeCheck(other);
    List<FheInt16> a = this.getElements();
    List<FheInt16> b = other.getElements();
    List<FheInt16> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt16Array(r);
  }

  public static FheInt16Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt16Array(values.stream().map(v -> FheInt16.encrypt(v, clientKey)).toList());
  }
  public static FheInt16Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt16Array(values.stream().map(v -> FheInt16.encrypt(v, publicKey)).toList());
  }
  public static FheInt16Array encrypt(Collection<Short> values) {
    return new FheInt16Array(values.stream().map(FheInt16::encrypt).toList());
  }

  private void sizeCheck(FheInt16Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
