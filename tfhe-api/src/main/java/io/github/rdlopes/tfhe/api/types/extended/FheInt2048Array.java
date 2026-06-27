package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I2048;
import io.github.rdlopes.tfhe.core.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int2048_sum;

@Generated
public final class FheInt2048Array extends NativeArray implements FheArray<FheInt2048, FheInt2048Array> {

  public FheInt2048Array(Collection<FheInt2048> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt2048Array other) {
    List<FheUint2048> lhsU = this.<FheInt2048>getElements().stream().map(e -> e.castInto(FheUint2048.class)).toList();
    List<FheUint2048> rhsU = other.<FheInt2048>getElements().stream().map(e -> e.castInto(FheUint2048.class)).toList();
    try (FheUint2048Array lhsArr = new FheUint2048Array(lhsU);
         FheUint2048Array rhsArr = new FheUint2048Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint2048::destroy);
      rhsU.forEach(FheUint2048::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt2048Array other) {
    List<FheUint2048> lhsU = this.<FheInt2048>getElements().stream().map(e -> e.castInto(FheUint2048.class)).toList();
    List<FheUint2048> rhsU = other.<FheInt2048>getElements().stream().map(e -> e.castInto(FheUint2048.class)).toList();
    try (FheUint2048Array lhsArr = new FheUint2048Array(lhsU);
         FheUint2048Array rhsArr = new FheUint2048Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint2048::destroy);
      rhsU.forEach(FheUint2048::destroy);
      return result;
    }
  }

  @Override
  public FheInt2048 sum() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int2048_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt2048Array add(FheInt2048Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt2048> a = this.getElements();
    List<FheInt2048> b = other.getElements();
    List<FheInt2048> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt2048Array(r);
  }

  @Override
  public FheInt2048Array subtract(FheInt2048Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt2048> a = this.getElements();
    List<FheInt2048> b = other.getElements();
    List<FheInt2048> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt2048Array(r);
  }

  public static FheInt2048Array encrypt(Collection<I2048> values, ClientKey clientKey) {
    return new FheInt2048Array(values.stream().map(v -> FheInt2048.encrypt(v, clientKey)).toList());
  }
  public static FheInt2048Array encrypt(Collection<I2048> values, PublicKey publicKey) {
    return new FheInt2048Array(values.stream().map(v -> FheInt2048.encrypt(v, publicKey)).toList());
  }
  public static FheInt2048Array encrypt(Collection<I2048> values) {
    return new FheInt2048Array(values.stream().map(FheInt2048::encrypt).toList());
  }
}
