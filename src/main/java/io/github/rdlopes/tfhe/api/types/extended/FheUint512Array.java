package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.U512;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_uint512_sum;

@Generated
public final class FheUint512Array extends NativeArray implements FheArray<FheUint512, FheUint512Array> {

  public FheUint512Array(Collection<FheUint512> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheUint512Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheUint512> a = this.getElements();
    List<FheUint512> b = other.getElements();
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
  public FheBool equalsArray(FheUint512Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheUint512> a = this.getElements();
    List<FheUint512> b = other.getElements();
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
  public FheUint512 sum() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint512_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheUint512Array add(FheUint512Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint512> a = this.getElements();
    List<FheUint512> b = other.getElements();
    List<FheUint512> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheUint512Array(r);
  }

  @Override
  public FheUint512Array subtract(FheUint512Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint512> a = this.getElements();
    List<FheUint512> b = other.getElements();
    List<FheUint512> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheUint512Array(r);
  }

  public static FheUint512Array encrypt(Collection<U512> values, ClientKey clientKey) {
    return new FheUint512Array(values.stream().map(v -> FheUint512.encrypt(v, clientKey)).toList());
  }
  public static FheUint512Array encrypt(Collection<U512> values, PublicKey publicKey) {
    return new FheUint512Array(values.stream().map(v -> FheUint512.encrypt(v, publicKey)).toList());
  }
  public static FheUint512Array encrypt(Collection<U512> values) {
    return new FheUint512Array(values.stream().map(FheUint512::encrypt).toList());
  }
}
