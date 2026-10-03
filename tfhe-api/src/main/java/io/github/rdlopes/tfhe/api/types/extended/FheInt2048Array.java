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
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheInt2048> a = this.getElements();
    List<FheInt2048> b = other.getElements();
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
  public FheBool equalsArray(FheInt2048Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheInt2048> a = this.getElements();
    List<FheInt2048> b = other.getElements();
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
