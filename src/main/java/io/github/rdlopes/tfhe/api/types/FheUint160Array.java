package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.U256;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_uint160_sum;

public final class FheUint160Array extends NativeArray implements FheArray<FheUint160, FheUint160Array> {

  public FheUint160Array(Collection<FheUint160> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheUint160Array other) {
    int lhsLen = (int) getSize();
    int rhsLen = (int) other.getSize();
    if (rhsLen > lhsLen) {
      return FheBool.encrypt(false);
    }
    List<FheUint160> a = this.getElements();
    List<FheUint160> b = other.getElements();
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
  public FheBool equalsArray(FheUint160Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
    List<FheUint160> a = this.getElements();
    List<FheUint160> b = other.getElements();
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
  public FheUint160 sum() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint160_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheUint160Array add(FheUint160Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint160> a = this.getElements();
    List<FheUint160> b = other.getElements();
    List<FheUint160> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheUint160Array(r);
  }

  @Override
  public FheUint160Array subtract(FheUint160Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheUint160> a = this.getElements();
    List<FheUint160> b = other.getElements();
    List<FheUint160> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheUint160Array(r);
  }

  public static FheUint160Array encrypt(Collection<U256> values, ClientKey clientKey) {
    return new FheUint160Array(values.stream().map(v -> FheUint160.encrypt(v, clientKey)).toList());
  }
  public static FheUint160Array encrypt(Collection<U256> values, PublicKey publicKey) {
    return new FheUint160Array(values.stream().map(v -> FheUint160.encrypt(v, publicKey)).toList());
  }
  public static FheUint160Array encrypt(Collection<U256> values) {
    return new FheUint160Array(values.stream().map(FheUint160::encrypt).toList());
  }
}
