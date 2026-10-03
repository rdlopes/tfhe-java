package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I512;
import io.github.rdlopes.tfhe.core.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int512_sum;

@Generated
public final class FheInt512Array extends NativeArray implements FheArray<FheInt512, FheInt512Array> {

  public FheInt512Array(Collection<FheInt512> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt512Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheInt512> a = this.getElements();
    List<FheInt512> b = other.getElements();
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
  public FheBool equalsArray(FheInt512Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheInt512> a = this.getElements();
    List<FheInt512> b = other.getElements();
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
