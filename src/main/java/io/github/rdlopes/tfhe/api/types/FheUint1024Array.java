package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U1024;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public final class FheUint1024Array extends NativeArray implements FheArray<FheUint1024, FheUint1024Array> {

  public FheUint1024Array(Collection<FheUint1024> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheUint1024Array other) {
    int L = (int) getSize();
    int M = (int) other.getSize();
    if (M > L) {
      return FheBool.encrypt(false);
    }
    List<FheUint1024> a = this.getElements();
    List<FheUint1024> b = other.getElements();
    FheBool result = FheBool.encrypt(false);
    for (int offset = 0; offset <= L - M; offset++) {
      FheBool eq = a.get(offset).equalTo(b.get(0));
      for (int j = 1; j < M; j++) {
        FheBool eq2 = a.get(offset + j).equalTo(b.get(j));
        eq.bitAndAssign(eq2);
        eq2.destroy();
      }
      result.bitOrAssign(eq);
      eq.destroy();
    }
    return result;
  }

  @Override
  public FheBool equalsArray(FheUint1024Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheUint1024> a = this.getElements();
    List<FheUint1024> b = other.getElements();
    if (a.isEmpty()) {
      return FheBool.encrypt(true);
    }
    FheBool result = a.get(0).equalTo(b.get(0));
    for (int i = 1; i < a.size(); i++) {
      FheBool eq = a.get(i).equalTo(b.get(i));
      result.bitAndAssign(eq);
      eq.destroy();
    }
    return result;
  }

  @Override
  public FheUint1024 sum() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint1024_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheUint1024Array add(FheUint1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint1024> a = this.getElements(); List<FheUint1024> b = other.getElements();
    List<FheUint1024> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheUint1024Array(r);
  }

  @Override
  public FheUint1024Array subtract(FheUint1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint1024> a = this.getElements(); List<FheUint1024> b = other.getElements();
    List<FheUint1024> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheUint1024Array(r);
  }

  public static FheUint1024Array encrypt(Collection<U1024> values, ClientKey clientKey) {
    return new FheUint1024Array(values.stream().map(v -> FheUint1024.encrypt(v, clientKey)).toList());
  }
  public static FheUint1024Array encrypt(Collection<U1024> values, PublicKey publicKey) {
    return new FheUint1024Array(values.stream().map(v -> FheUint1024.encrypt(v, publicKey)).toList());
  }
  public static FheUint1024Array encrypt(Collection<U1024> values) {
    return new FheUint1024Array(values.stream().map(FheUint1024::encrypt).toList());
  }
}
