package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I1024;
import io.github.rdlopes.tfhe.core.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int1024_sum;

@Generated
public final class FheInt1024Array extends NativeArray implements FheArray<FheInt1024, FheInt1024Array> {

  public FheInt1024Array(Collection<FheInt1024> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt1024Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheInt1024> a = this.getElements();
    List<FheInt1024> b = other.getElements();
    FheBool result = FheBool.encrypt(false);
    for (int offset = 0; offset <= lhsLen - rhsLen; offset++) {
      FheBool eq = a.get(offset).equalTo(b.get(0));
      for (int j = 1; j < rhsLen; j++) {
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
  public FheBool equalsArray(FheInt1024Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheInt1024> a = this.getElements();
    List<FheInt1024> b = other.getElements();
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
  public FheInt1024 sum() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int1024_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt1024Array add(FheInt1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt1024> a = this.getElements();
    List<FheInt1024> b = other.getElements();
    List<FheInt1024> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt1024Array(r);
  }

  @Override
  public FheInt1024Array subtract(FheInt1024Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt1024> a = this.getElements();
    List<FheInt1024> b = other.getElements();
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
