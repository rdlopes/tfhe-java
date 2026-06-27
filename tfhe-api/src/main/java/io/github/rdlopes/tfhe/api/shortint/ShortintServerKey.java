package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.BivariateLookupTableCallback;
import io.github.rdlopes.tfhe.core.ffm.LookupTableCallback;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public class ShortintServerKey extends NativePointer {

  ShortintServerKey() {
    super(TfheHeader::shortint_destroy_server_key);
  }

  public static ShortintServerKey generate(ShortintClientKey clientKey) {
    ShortintServerKey serverKey = new ShortintServerKey();
    execute(() -> shortint_gen_server_key(clientKey.getValue(), serverKey.getAddress()));
    return serverKey;
  }

  public static ShortintServerKey deserialize(DynamicBuffer dynamicBuffer) {
    ShortintServerKey serverKey = new ShortintServerKey();
    execute(() -> shortint_deserialize_server_key(dynamicBuffer.getAddress(), serverKey.getAddress()));
    return serverKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_server_key(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public ShortintCiphertext createTrivial(long value) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_create_trivial(getValue(), value, result.getAddress()));
    return result;
  }

  // ==========================================
  // Programmable Bootstrapping (PBS)
  // ==========================================

  public ShortintPbsLookupTable generateLookupTable(LongUnaryOperator operator) {
    ShortintPbsLookupTable lut = new ShortintPbsLookupTable();
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment callbackStub = LookupTableCallback.allocate(operator::applyAsLong, arena);
      execute(() -> shortint_server_key_generate_pbs_lookup_table(getValue(), callbackStub, lut.getAddress()));
    }
    return lut;
  }

  public ShortintBivariatePbsLookupTable generateBivariateLookupTable(LongBinaryOperator operator) {
    ShortintBivariatePbsLookupTable lut = new ShortintBivariatePbsLookupTable();
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment callbackStub = BivariateLookupTableCallback.allocate(operator::applyAsLong, arena);
      execute(() -> shortint_server_key_generate_bivariate_pbs_lookup_table(getValue(), callbackStub, lut.getAddress()));
    }
    return lut;
  }

  public ShortintCiphertext programmableBootstrap(ShortintCiphertext ct, ShortintPbsLookupTable lut) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_programmable_bootstrap(getValue(), lut.getValue(), ct.getValue(), result.getAddress()));
    return result;
  }

  public void programmableBootstrapAssign(ShortintCiphertext ct, ShortintPbsLookupTable lut) {
    execute(() -> shortint_server_key_programmable_bootstrap_assign(getValue(), lut.getValue(), ct.getValue()));
  }

  public ShortintCiphertext bivariateProgrammableBootstrap(ShortintCiphertext ctLeft, ShortintCiphertext ctRight, ShortintBivariatePbsLookupTable lut) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_bivariate_programmable_bootstrap(getValue(), lut.getValue(), ctLeft.getValue(), ctRight.getValue(), result.getAddress()));
    return result;
  }

  public void bivariateProgrammableBootstrapAssign(ShortintCiphertext ctLeftAndResult, ShortintCiphertext ctRight, ShortintBivariatePbsLookupTable lut) {
    execute(() -> shortint_server_key_bivariate_programmable_bootstrap_assign(getValue(), lut.getValue(), ctLeftAndResult.getValue(), ctRight.getValue()));
  }

  // ==========================================
  // Symmetric arithmetic helpers
  // ==========================================

  public ShortintCiphertext smartAdd(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartAdd(this, rhs);
  }

  public void smartAddAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartAddAssign(this, rhs);
  }

  public ShortintCiphertext smartSub(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartSub(this, rhs);
  }

  public void smartSubAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartSubAssign(this, rhs);
  }

  public ShortintCiphertext smartMul(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartMul(this, rhs);
  }

  public void smartMulAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartMulAssign(this, rhs);
  }

  public ShortintCiphertext smartDiv(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartDiv(this, rhs);
  }

  public void smartDivAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartDivAssign(this, rhs);
  }

  public ShortintCiphertext smartNeg(ShortintCiphertext lhs) {
    return lhs.smartNeg(this);
  }

  public void smartNegAssign(ShortintCiphertext lhsAndResult) {
    lhsAndResult.smartNegAssign(this);
  }

  public ShortintCiphertext smartScalarAdd(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarAdd(this, rhs);
  }

  public void smartScalarAddAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.smartScalarAddAssign(this, rhs);
  }

  public ShortintCiphertext smartScalarSub(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarSub(this, rhs);
  }

  public void smartScalarSubAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.smartScalarSubAssign(this, rhs);
  }

  public ShortintCiphertext smartScalarMul(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarMul(this, rhs);
  }

  public void smartScalarMulAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.smartScalarMulAssign(this, rhs);
  }

  // ==========================================
  // Unchecked symmetric helpers
  // ==========================================

  public ShortintCiphertext uncheckedAdd(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedAdd(this, rhs);
  }

  public void uncheckedAddAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedAddAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedSub(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedSub(this, rhs);
  }

  public void uncheckedSubAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedSubAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedMul(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedMul(this, rhs);
  }

  public void uncheckedMulAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedMulAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedDiv(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedDiv(this, rhs);
  }

  public void uncheckedDivAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedDivAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedNeg(ShortintCiphertext lhs) {
    return lhs.uncheckedNeg(this);
  }

  public void uncheckedNegAssign(ShortintCiphertext lhsAndResult) {
    lhsAndResult.uncheckedNegAssign(this);
  }

  public ShortintCiphertext uncheckedScalarAdd(ShortintCiphertext lhs, byte rhs) {
    return lhs.uncheckedScalarAdd(this, rhs);
  }

  public void uncheckedScalarAddAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.uncheckedScalarAddAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedScalarSub(ShortintCiphertext lhs, byte rhs) {
    return lhs.uncheckedScalarSub(this, rhs);
  }

  public void uncheckedScalarSubAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.uncheckedScalarSubAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedScalarMul(ShortintCiphertext lhs, byte rhs) {
    return lhs.uncheckedScalarMul(this, rhs);
  }

  public void uncheckedScalarMulAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.uncheckedScalarMulAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedScalarDiv(ShortintCiphertext lhs, byte rhs) {
    return lhs.uncheckedScalarDiv(this, rhs);
  }

  public void uncheckedScalarDivAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.uncheckedScalarDivAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedScalarMod(ShortintCiphertext lhs, byte rhs) {
    return lhs.uncheckedScalarMod(this, rhs);
  }

  public void uncheckedScalarModAssign(ShortintCiphertext lhsAndResult, byte rhs) {
    lhsAndResult.uncheckedScalarModAssign(this, rhs);
  }

  // ==========================================
  // Smart Bitwise symmetric helpers
  // ==========================================

  public ShortintCiphertext smartBitAnd(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartBitAnd(this, rhs);
  }

  public void smartBitAndAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartBitAndAssign(this, rhs);
  }

  public ShortintCiphertext smartBitOr(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartBitOr(this, rhs);
  }

  public void smartBitOrAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartBitOrAssign(this, rhs);
  }

  public ShortintCiphertext smartBitXor(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartBitXor(this, rhs);
  }

  public void smartBitXorAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.smartBitXorAssign(this, rhs);
  }

  // ==========================================
  // Unchecked Bitwise symmetric helpers
  // ==========================================

  public ShortintCiphertext uncheckedBitAnd(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedBitAnd(this, rhs);
  }

  public void uncheckedBitAndAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedBitAndAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedBitOr(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedBitOr(this, rhs);
  }

  public void uncheckedBitOrAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedBitOrAssign(this, rhs);
  }

  public ShortintCiphertext uncheckedBitXor(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedBitXor(this, rhs);
  }

  public void uncheckedBitXorAssign(ShortintCiphertext lhsAndResult, ShortintCiphertext rhs) {
    lhsAndResult.uncheckedBitXorAssign(this, rhs);
  }

  // ==========================================
  // Shift symmetric helpers
  // ==========================================

  public ShortintCiphertext smartScalarLeftShift(ShortintCiphertext lhs, byte shift) {
    return lhs.smartScalarLeftShift(this, shift);
  }

  public void smartScalarLeftShiftAssign(ShortintCiphertext lhsAndResult, byte shift) {
    lhsAndResult.smartScalarLeftShiftAssign(this, shift);
  }

  public ShortintCiphertext smartScalarRightShift(ShortintCiphertext lhs, byte shift) {
    return lhs.smartScalarRightShift(this, shift);
  }

  public void smartScalarRightShiftAssign(ShortintCiphertext lhsAndResult, byte shift) {
    lhsAndResult.smartScalarRightShiftAssign(this, shift);
  }

  public ShortintCiphertext uncheckedScalarLeftShift(ShortintCiphertext lhs, byte shift) {
    return lhs.uncheckedScalarLeftShift(this, shift);
  }

  public void uncheckedScalarLeftShiftAssign(ShortintCiphertext lhsAndResult, byte shift) {
    lhsAndResult.uncheckedScalarLeftShiftAssign(this, shift);
  }

  public ShortintCiphertext uncheckedScalarRightShift(ShortintCiphertext lhs, byte shift) {
    return lhs.uncheckedScalarRightShift(this, shift);
  }

  public void uncheckedScalarRightShiftAssign(ShortintCiphertext lhsAndResult, byte shift) {
    lhsAndResult.uncheckedScalarRightShiftAssign(this, shift);
  }

  // ==========================================
  // Comparison symmetric helpers
  // ==========================================

  public ShortintCiphertext smartEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartEqual(this, rhs);
  }

  public ShortintCiphertext smartScalarEqual(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarEqual(this, rhs);
  }

  public ShortintCiphertext smartNotEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartNotEqual(this, rhs);
  }

  public ShortintCiphertext smartScalarNotEqual(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarNotEqual(this, rhs);
  }

  public ShortintCiphertext smartLess(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartLess(this, rhs);
  }

  public ShortintCiphertext smartScalarLess(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarLess(this, rhs);
  }

  public ShortintCiphertext smartLessOrEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartLessOrEqual(this, rhs);
  }

  public ShortintCiphertext smartScalarLessOrEqual(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarLessOrEqual(this, rhs);
  }

  public ShortintCiphertext smartGreater(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartGreater(this, rhs);
  }

  public ShortintCiphertext smartScalarGreater(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarGreater(this, rhs);
  }

  public ShortintCiphertext smartGreaterOrEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.smartGreaterOrEqual(this, rhs);
  }

  public ShortintCiphertext smartScalarGreaterOrEqual(ShortintCiphertext lhs, byte rhs) {
    return lhs.smartScalarGreaterOrEqual(this, rhs);
  }

  // ==========================================
  // Unchecked Comparison symmetric helpers
  // ==========================================

  public ShortintCiphertext uncheckedEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedEqual(this, rhs);
  }

  public ShortintCiphertext uncheckedNotEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedNotEqual(this, rhs);
  }

  public ShortintCiphertext uncheckedLess(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedLess(this, rhs);
  }

  public ShortintCiphertext uncheckedLessOrEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedLessOrEqual(this, rhs);
  }

  public ShortintCiphertext uncheckedGreater(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedGreater(this, rhs);
  }

  public ShortintCiphertext uncheckedGreaterOrEqual(ShortintCiphertext lhs, ShortintCiphertext rhs) {
    return lhs.uncheckedGreaterOrEqual(this, rhs);
  }
}
