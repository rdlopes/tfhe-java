package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U2048;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_uint2048_sum;

public final class FheUint2048Array extends NativeArray implements FheArray<FheUint2048, FheUint2048Array> {

  public FheUint2048Array(Collection<FheUint2048> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheUint2048Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheUint2048> a = this.getElements();
    List<FheUint2048> b = other.getElements();
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
  public FheBool equalsArray(FheUint2048Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheUint2048> a = this.getElements();
    List<FheUint2048> b = other.getElements();
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
  public FheUint2048 sum() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint2048_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheUint2048Array add(FheUint2048Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint2048> a = this.getElements();
    List<FheUint2048> b = other.getElements();
    List<FheUint2048> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheUint2048Array(r);
  }

  @Override
  public FheUint2048Array subtract(FheUint2048Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint2048> a = this.getElements();
    List<FheUint2048> b = other.getElements();
    List<FheUint2048> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheUint2048Array(r);
  }

  public static FheUint2048Array encrypt(Collection<U2048> values, ClientKey clientKey) {
    return new FheUint2048Array(values.stream().map(v -> FheUint2048.encrypt(v, clientKey)).toList());
  }
  public static FheUint2048Array encrypt(Collection<U2048> values, PublicKey publicKey) {
    return new FheUint2048Array(values.stream().map(v -> FheUint2048.encrypt(v, publicKey)).toList());
  }
  public static FheUint2048Array encrypt(Collection<U2048> values) {
    return new FheUint2048Array(values.stream().map(FheUint2048::encrypt).toList());
  }
}
