package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.I128;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int128_sum;

@Generated
public final class FheInt128Array extends NativeArray implements FheArray<FheInt128, FheInt128Array> {

  public FheInt128Array(Collection<FheInt128> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt128Array other) {
    List<FheUint128> lhsU = this.<FheInt128>getElements().stream().map(e -> e.castInto(FheUint128.class)).toList();
    List<FheUint128> rhsU = other.<FheInt128>getElements().stream().map(e -> e.castInto(FheUint128.class)).toList();
    try (FheUint128Array lhsArr = new FheUint128Array(lhsU);
         FheUint128Array rhsArr = new FheUint128Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint128::destroy);
      rhsU.forEach(FheUint128::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt128Array other) {
    List<FheUint128> lhsU = this.<FheInt128>getElements().stream().map(e -> e.castInto(FheUint128.class)).toList();
    List<FheUint128> rhsU = other.<FheInt128>getElements().stream().map(e -> e.castInto(FheUint128.class)).toList();
    try (FheUint128Array lhsArr = new FheUint128Array(lhsU);
         FheUint128Array rhsArr = new FheUint128Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint128::destroy);
      rhsU.forEach(FheUint128::destroy);
      return result;
    }
  }

  @Override
  public FheInt128 sum() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int128_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt128Array add(FheInt128Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt128> a = this.getElements();
    List<FheInt128> b = other.getElements();
    List<FheInt128> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt128Array(r);
  }

  @Override
  public FheInt128Array subtract(FheInt128Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt128> a = this.getElements();
    List<FheInt128> b = other.getElements();
    List<FheInt128> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt128Array(r);
  }

  public static FheInt128Array encrypt(Collection<I128> values, ClientKey clientKey) {
    return new FheInt128Array(values.stream().map(v -> FheInt128.encrypt(v, clientKey)).toList());
  }
  public static FheInt128Array encrypt(Collection<I128> values, PublicKey publicKey) {
    return new FheInt128Array(values.stream().map(v -> FheInt128.encrypt(v, publicKey)).toList());
  }
  public static FheInt128Array encrypt(Collection<I128> values) {
    return new FheInt128Array(values.stream().map(FheInt128::encrypt).toList());
  }
}
