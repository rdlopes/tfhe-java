package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.fhe_int8_sum;

/// Array of encrypted signed 8-bit integers.
///
/// Signed arrays do not have native `containsArray`/`equalsArray` operations.
/// These are implemented by casting each element to [FheUint8] and delegating
/// to [FheUint8Array], exactly as in the original boilerplate.
@Generated
public final class FheInt8Array extends NativeArray implements FheArray<FheInt8, FheInt8Array> {

  public FheInt8Array(Collection<FheInt8> elements) {
    super(elements);
  }

  // ── FheArray — containsArray / equalsArray via unsigned cast ─────────────────

  @Override
  public FheBool containsArray(FheInt8Array other) {
    List<FheUint8> lhsU = this.<FheInt8>getElements().stream().map(e -> e.castInto(FheUint8.class)).toList();
    List<FheUint8> rhsU = other.<FheInt8>getElements().stream().map(e -> e.castInto(FheUint8.class)).toList();
    try (FheUint8Array lhsArr = new FheUint8Array(lhsU);
         FheUint8Array rhsArr = new FheUint8Array(rhsU)) {
      FheBool result = lhsArr.containsArray(rhsArr);
      lhsU.forEach(FheUint8::destroy);
      rhsU.forEach(FheUint8::destroy);
      return result;
    }
  }

  @Override
  public FheBool equalsArray(FheInt8Array other) {
    List<FheUint8> lhsU = this.<FheInt8>getElements().stream().map(e -> e.castInto(FheUint8.class)).toList();
    List<FheUint8> rhsU = other.<FheInt8>getElements().stream().map(e -> e.castInto(FheUint8.class)).toList();
    try (FheUint8Array lhsArr = new FheUint8Array(lhsU);
         FheUint8Array rhsArr = new FheUint8Array(rhsU)) {
      FheBool result = lhsArr.equalsArray(rhsArr);
      lhsU.forEach(FheUint8::destroy);
      rhsU.forEach(FheUint8::destroy);
      return result;
    }
  }

  // ── FheArray — sum ────────────────────────────────────────────────────────────

  @Override
  public FheInt8 sum() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int8_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  // ── FheArray — element-wise add / subtract ────────────────────────────────────

  @Override
  public FheInt8Array add(FheInt8Array other) {
    sizeCheck(other);
    List<FheInt8> a = this.getElements();
    List<FheInt8> b = other.getElements();
    List<FheInt8> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).add(b.get(i)));
    return new FheInt8Array(r);
  }

  @Override
  public FheInt8Array subtract(FheInt8Array other) {
    sizeCheck(other);
    List<FheInt8> a = this.getElements();
    List<FheInt8> b = other.getElements();
    List<FheInt8> r = new ArrayList<>(a.size());
    for (int i = 0; i < a.size(); i++) r.add(a.get(i).subtract(b.get(i)));
    return new FheInt8Array(r);
  }

  // ── Static factories ──────────────────────────────────────────────────────────

  public static FheInt8Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    return new FheInt8Array(values.stream().map(v -> FheInt8.encrypt(v, clientKey)).toList());
  }

  public static FheInt8Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    return new FheInt8Array(values.stream().map(v -> FheInt8.encrypt(v, publicKey)).toList());
  }

  public static FheInt8Array encrypt(Collection<Byte> values) {
    return new FheInt8Array(values.stream().map(FheInt8::encrypt).toList());
  }

  // ── Helper ────────────────────────────────────────────────────────────────────

  private void sizeCheck(FheInt8Array other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
