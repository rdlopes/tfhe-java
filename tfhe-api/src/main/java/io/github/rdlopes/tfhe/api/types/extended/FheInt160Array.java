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
    List<FheUint160> lhsU = this.<FheInt160>getElements().stream().map(e -> e.castInto(FheUint160.class)).toList();
    List<FheUint160> rhsU = other.<FheInt160>getElements().stream().map(e -> e.castInto(FheUint160.class)).toList();
    try (FheUint160Array lhsArr = new FheUint160Array(lhsU);
         FheUint160Array rhsArr = new FheUint160Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint160::destroy);
      rhsU.forEach(FheUint160::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt160Array other) {
    List<FheUint160> lhsU = this.<FheInt160>getElements().stream().map(e -> e.castInto(FheUint160.class)).toList();
    List<FheUint160> rhsU = other.<FheInt160>getElements().stream().map(e -> e.castInto(FheUint160.class)).toList();
    try (FheUint160Array lhsArr = new FheUint160Array(lhsU);
         FheUint160Array rhsArr = new FheUint160Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint160::destroy);
      rhsU.forEach(FheUint160::destroy);
      return result;
    }
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
