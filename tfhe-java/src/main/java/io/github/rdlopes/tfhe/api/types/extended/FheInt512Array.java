package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I512;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int512_sum;

@Generated
public final class FheInt512Array extends NativeArray implements FheArray<FheInt512, FheInt512Array> {

  public FheInt512Array(Collection<FheInt512> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt512Array other) {
    List<FheUint512> lhsU = this.<FheInt512>getElements().stream().map(e -> e.castInto(FheUint512.class)).toList();
    List<FheUint512> rhsU = other.<FheInt512>getElements().stream().map(e -> e.castInto(FheUint512.class)).toList();
    try (FheUint512Array lhsArr = new FheUint512Array(lhsU);
         FheUint512Array rhsArr = new FheUint512Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint512::destroy);
      rhsU.forEach(FheUint512::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt512Array other) {
    List<FheUint512> lhsU = this.<FheInt512>getElements().stream().map(e -> e.castInto(FheUint512.class)).toList();
    List<FheUint512> rhsU = other.<FheInt512>getElements().stream().map(e -> e.castInto(FheUint512.class)).toList();
    try (FheUint512Array lhsArr = new FheUint512Array(lhsU);
         FheUint512Array rhsArr = new FheUint512Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint512::destroy);
      rhsU.forEach(FheUint512::destroy);
      return result;
    }
  }

  @Override
  public FheInt512 sum() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int512_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt512Array add(FheInt512Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt512> a = this.getElements();
    List<FheInt512> b = other.getElements();
    List<FheInt512> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt512Array(r);
  }

  @Override
  public FheInt512Array subtract(FheInt512Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt512> a = this.getElements();
    List<FheInt512> b = other.getElements();
    List<FheInt512> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt512Array(r);
  }

  public static FheInt512Array encrypt(Collection<I512> values, ClientKey clientKey) {
    return new FheInt512Array(values.stream().map(v -> FheInt512.encrypt(v, clientKey)).toList());
  }
  public static FheInt512Array encrypt(Collection<I512> values, PublicKey publicKey) {
    return new FheInt512Array(values.stream().map(v -> FheInt512.encrypt(v, publicKey)).toList());
  }
  public static FheInt512Array encrypt(Collection<I512> values) {
    return new FheInt512Array(values.stream().map(FheInt512::encrypt).toList());
  }
}
