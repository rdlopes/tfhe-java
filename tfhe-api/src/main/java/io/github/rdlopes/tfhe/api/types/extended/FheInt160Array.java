package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I256;
import io.github.rdlopes.tfhe.core.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.fhe_int160_sum;

@Generated
public final class FheInt160Array extends NativeArray implements FheArray<FheInt160, FheInt160Array> {

  public FheInt160Array(Collection<FheInt160> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt160Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheInt160> a = this.getElements();
    List<FheInt160> b = other.getElements();
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
  public FheBool equalsArray(FheInt160Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheInt160> a = this.getElements();
    List<FheInt160> b = other.getElements();
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
  public FheInt160 sum() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int160_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt160Array add(FheInt160Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt160> a = this.getElements();
    List<FheInt160> b = other.getElements();
    List<FheInt160> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt160Array(r);
  }

  @Override
  public FheInt160Array subtract(FheInt160Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt160> a = this.getElements();
    List<FheInt160> b = other.getElements();
    List<FheInt160> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt160Array(r);
  }

  public static FheInt160Array encrypt(Collection<I256> values, ClientKey clientKey) {
    return new FheInt160Array(values.stream().map(v -> FheInt160.encrypt(v, clientKey)).toList());
  }
  public static FheInt160Array encrypt(Collection<I256> values, PublicKey publicKey) {
    return new FheInt160Array(values.stream().map(v -> FheInt160.encrypt(v, publicKey)).toList());
  }
  public static FheInt160Array encrypt(Collection<I256> values) {
    return new FheInt160Array(values.stream().map(FheInt160::encrypt).toList());
  }
}
