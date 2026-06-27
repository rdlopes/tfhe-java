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
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int14_sum;

@Generated
public final class FheInt14Array extends NativeArray implements FheArray<FheInt14, FheInt14Array> {

  public FheInt14Array(Collection<FheInt14> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt14Array other) {
    List<FheUint14> lhsU = this.<FheInt14>getElements().stream().map(e -> e.castInto(FheUint14.class)).toList();
    List<FheUint14> rhsU = other.<FheInt14>getElements().stream().map(e -> e.castInto(FheUint14.class)).toList();
    try (FheUint14Array lhsArr = new FheUint14Array(lhsU);
         FheUint14Array rhsArr = new FheUint14Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint14::destroy);
      rhsU.forEach(FheUint14::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt14Array other) {
    List<FheUint14> lhsU = this.<FheInt14>getElements().stream().map(e -> e.castInto(FheUint14.class)).toList();
    List<FheUint14> rhsU = other.<FheInt14>getElements().stream().map(e -> e.castInto(FheUint14.class)).toList();
    try (FheUint14Array lhsArr = new FheUint14Array(lhsU);
         FheUint14Array rhsArr = new FheUint14Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint14::destroy);
      rhsU.forEach(FheUint14::destroy);
      return result;
    }
  }

  @Override
  public FheInt14 sum() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int14_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt14Array add(FheInt14Array other) {
    sizeCheck(other);
    List<FheInt14> a = this.getElements();
    List<FheInt14> b = other.getElements();
    List<FheInt14> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt14Array(r);
  }

  @Override
  public FheInt14Array subtract(FheInt14Array other) {
    sizeCheck(other);
    List<FheInt14> a = this.getElements();
    List<FheInt14> b = other.getElements();
    List<FheInt14> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt14Array(r);
  }

  public static FheInt14Array encrypt(Collection<Short> values, ClientKey clientKey) {
    return new FheInt14Array(values.stream().map(v -> FheInt14.encrypt(v, clientKey)).toList());
  }
  public static FheInt14Array encrypt(Collection<Short> values, PublicKey publicKey) {
    return new FheInt14Array(values.stream().map(v -> FheInt14.encrypt(v, publicKey)).toList());
  }
  public static FheInt14Array encrypt(Collection<Short> values) {
    return new FheInt14Array(values.stream().map(FheInt14::encrypt).toList());
  }

  private void sizeCheck(FheInt14Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
