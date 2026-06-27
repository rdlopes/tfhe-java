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
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int64_sum;

@Generated
public final class FheInt64Array extends NativeArray implements FheArray<FheInt64, FheInt64Array> {

  public FheInt64Array(Collection<FheInt64> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt64Array other) {
    List<FheUint64> lhsU = this.<FheInt64>getElements().stream().map(e -> e.castInto(FheUint64.class)).toList();
    List<FheUint64> rhsU = other.<FheInt64>getElements().stream().map(e -> e.castInto(FheUint64.class)).toList();
    try (FheUint64Array lhsArr = new FheUint64Array(lhsU);
         FheUint64Array rhsArr = new FheUint64Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint64::destroy);
      rhsU.forEach(FheUint64::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt64Array other) {
    List<FheUint64> lhsU = this.<FheInt64>getElements().stream().map(e -> e.castInto(FheUint64.class)).toList();
    List<FheUint64> rhsU = other.<FheInt64>getElements().stream().map(e -> e.castInto(FheUint64.class)).toList();
    try (FheUint64Array lhsArr = new FheUint64Array(lhsU);
         FheUint64Array rhsArr = new FheUint64Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint64::destroy);
      rhsU.forEach(FheUint64::destroy);
      return result;
    }
  }

  @Override
  public FheInt64 sum() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt64Array add(FheInt64Array other) {
    sizeCheck(other);
    List<FheInt64> a = this.getElements();
    List<FheInt64> b = other.getElements();
    List<FheInt64> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt64Array(r);
  }

  @Override
  public FheInt64Array subtract(FheInt64Array other) {
    sizeCheck(other);
    List<FheInt64> a = this.getElements();
    List<FheInt64> b = other.getElements();
    List<FheInt64> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt64Array(r);
  }

  public static FheInt64Array encrypt(Collection<Long> values, ClientKey clientKey) {
    return new FheInt64Array(values.stream().map(v -> FheInt64.encrypt(v, clientKey)).toList());
  }
  public static FheInt64Array encrypt(Collection<Long> values, PublicKey publicKey) {
    return new FheInt64Array(values.stream().map(v -> FheInt64.encrypt(v, publicKey)).toList());
  }
  public static FheInt64Array encrypt(Collection<Long> values) {
    return new FheInt64Array(values.stream().map(FheInt64::encrypt).toList());
  }

  private void sizeCheck(FheInt64Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
