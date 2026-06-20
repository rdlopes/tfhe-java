package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.extended.I256;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int256_sum;

@Generated
public final class FheInt256Array extends NativeArray implements FheArray<FheInt256, FheInt256Array> {

  public FheInt256Array(Collection<FheInt256> elements) { super(elements); }

  @Override
  public FheBool containsArray(FheInt256Array other) {
    List<FheUint256> lhsU = this.<FheInt256>getElements().stream().map(e -> e.castInto(FheUint256.class)).toList();
    List<FheUint256> rhsU = other.<FheInt256>getElements().stream().map(e -> e.castInto(FheUint256.class)).toList();
    try (FheUint256Array lhsArr = new FheUint256Array(lhsU);
         FheUint256Array rhsArr = new FheUint256Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint256::destroy);
      rhsU.forEach(FheUint256::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt256Array other) {
    List<FheUint256> lhsU = this.<FheInt256>getElements().stream().map(e -> e.castInto(FheUint256.class)).toList();
    List<FheUint256> rhsU = other.<FheInt256>getElements().stream().map(e -> e.castInto(FheUint256.class)).toList();
    try (FheUint256Array lhsArr = new FheUint256Array(lhsU);
         FheUint256Array rhsArr = new FheUint256Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint256::destroy);
      rhsU.forEach(FheUint256::destroy);
      return result;
    }
  }

  @Override
  public FheInt256 sum() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt256Array add(FheInt256Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt256> a = this.getElements();
    List<FheInt256> b = other.getElements();
    List<FheInt256> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt256Array(r);
  }

  @Override
  public FheInt256Array subtract(FheInt256Array other) {
    if (getSize() != other.getSize()) throw new IllegalArgumentException("Array sizes must match");
    List<FheInt256> a = this.getElements();
    List<FheInt256> b = other.getElements();
    List<FheInt256> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt256Array(r);
  }

  public static FheInt256Array encrypt(Collection<I256> values, ClientKey clientKey) {
    return new FheInt256Array(values.stream().map(v -> FheInt256.encrypt(v, clientKey)).toList());
  }
  public static FheInt256Array encrypt(Collection<I256> values, PublicKey publicKey) {
    return new FheInt256Array(values.stream().map(v -> FheInt256.encrypt(v, publicKey)).toList());
  }
  public static FheInt256Array encrypt(Collection<I256> values) {
    return new FheInt256Array(values.stream().map(FheInt256::encrypt).toList());
  }
}
