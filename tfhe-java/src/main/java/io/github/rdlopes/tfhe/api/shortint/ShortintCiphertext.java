package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

@SuppressWarnings({"java:S2975", "java:S1182"})
public class ShortintCiphertext extends NativePointer {

  public ShortintCiphertext() {
    super(TfheHeader::shortint_destroy_ciphertext);
  }

  public long getDegree() {
    try (Arena arena = Arena.ofConfined()) {
      java.lang.foreign.MemorySegment result = arena.allocate(ValueLayout.JAVA_LONG);
      execute(() -> shortint_ciphertext_get_degree(getValue(), result));
      return result.get(ValueLayout.JAVA_LONG, 0);
    }
  }

  public void setDegree(long degree) {
    execute(() -> shortint_ciphertext_set_degree(getValue(), degree));
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_ciphertext(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public static ShortintCiphertext deserialize(DynamicBuffer dynamicBuffer) {
    ShortintCiphertext deserialized = new ShortintCiphertext();
    execute(() -> shortint_deserialize_ciphertext(dynamicBuffer.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ShortintCiphertext clone() {
    return deserialize(serialize());
  }

  // ==========================================
  // Smart Arithmetic Operations
  // ==========================================

  public ShortintCiphertext smartAdd(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_add(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartAddAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_add_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartSub(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_sub(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartSubAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_sub_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartMul(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_mul(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartMulAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_mul_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartDiv(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_div(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartDivAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_div_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartNeg(ShortintServerKey serverKey) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_neg(serverKey.getValue(), getValue(), result.getAddress()));
    return result;
  }

  public void smartNegAssign(ShortintServerKey serverKey) {
    execute(() -> shortint_server_key_smart_neg_assign(serverKey.getValue(), getValue()));
  }

  // ==========================================
  // Smart Scalar Arithmetic Operations
  // ==========================================

  public ShortintCiphertext smartScalarAdd(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_add(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void smartScalarAddAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_smart_scalar_add_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext smartScalarSub(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_sub(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void smartScalarSubAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_smart_scalar_sub_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext smartScalarMul(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_mul(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void smartScalarMulAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_smart_scalar_mul_assign(serverKey.getValue(), getValue(), other));
  }

  // ==========================================
  // Unchecked Arithmetic Operations
  // ==========================================

  public ShortintCiphertext uncheckedAdd(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_add(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedAddAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_add_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedSub(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_sub(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedSubAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_sub_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedMul(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_mul(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedMulAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_mul_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedDiv(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_div(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedDivAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_div_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedNeg(ShortintServerKey serverKey) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_neg(serverKey.getValue(), getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedNegAssign(ShortintServerKey serverKey) {
    execute(() -> shortint_server_key_unchecked_neg_assign(serverKey.getValue(), getValue()));
  }

  // ==========================================
  // Unchecked Scalar Arithmetic Operations
  // ==========================================

  public ShortintCiphertext uncheckedScalarAdd(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_add(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void uncheckedScalarAddAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_unchecked_scalar_add_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext uncheckedScalarSub(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_sub(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void uncheckedScalarSubAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_unchecked_scalar_sub_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext uncheckedScalarMul(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_mul(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void uncheckedScalarMulAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_unchecked_scalar_mul_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext uncheckedScalarDiv(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_div(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void uncheckedScalarDivAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_unchecked_scalar_div_assign(serverKey.getValue(), getValue(), other));
  }

  public ShortintCiphertext uncheckedScalarMod(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_mod(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public void uncheckedScalarModAssign(ShortintServerKey serverKey, byte other) {
    execute(() -> shortint_server_key_unchecked_scalar_mod_assign(serverKey.getValue(), getValue(), other));
  }

  // ==========================================
  // Smart Bitwise Operations
  // ==========================================

  public ShortintCiphertext smartBitAnd(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_bitand(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartBitAndAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_bitand_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartBitOr(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_bitor(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartBitOrAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_bitor_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext smartBitXor(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_bitxor(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void smartBitXorAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_smart_bitxor_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  // ==========================================
  // Unchecked Bitwise Operations
  // ==========================================

  public ShortintCiphertext uncheckedBitAnd(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_bitand(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedBitAndAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_bitand_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedBitOr(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_bitor(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedBitOrAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_bitor_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  public ShortintCiphertext uncheckedBitXor(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_bitxor(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public void uncheckedBitXorAssign(ShortintServerKey serverKey, ShortintCiphertext other) {
    execute(() -> shortint_server_key_unchecked_bitxor_assign(serverKey.getValue(), getValue(), other.getValue()));
  }

  // ==========================================
  // Smart Shift Operations
  // ==========================================

  public ShortintCiphertext smartScalarLeftShift(ShortintServerKey serverKey, byte shift) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_left_shift(serverKey.getValue(), getValue(), shift, result.getAddress()));
    return result;
  }

  public void smartScalarLeftShiftAssign(ShortintServerKey serverKey, byte shift) {
    execute(() -> shortint_server_key_smart_scalar_left_shift_assign(serverKey.getValue(), getValue(), shift));
  }

  public ShortintCiphertext smartScalarRightShift(ShortintServerKey serverKey, byte shift) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_right_shift(serverKey.getValue(), getValue(), shift, result.getAddress()));
    return result;
  }

  public void smartScalarRightShiftAssign(ShortintServerKey serverKey, byte shift) {
    execute(() -> shortint_server_key_smart_scalar_right_shift_assign(serverKey.getValue(), getValue(), shift));
  }

  // ==========================================
  // Unchecked Shift Operations
  // ==========================================

  public ShortintCiphertext uncheckedScalarLeftShift(ShortintServerKey serverKey, byte shift) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_left_shift(serverKey.getValue(), getValue(), shift, result.getAddress()));
    return result;
  }

  public void uncheckedScalarLeftShiftAssign(ShortintServerKey serverKey, byte shift) {
    execute(() -> shortint_server_key_unchecked_scalar_left_shift_assign(serverKey.getValue(), getValue(), shift));
  }

  public ShortintCiphertext uncheckedScalarRightShift(ShortintServerKey serverKey, byte shift) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_scalar_right_shift(serverKey.getValue(), getValue(), shift, result.getAddress()));
    return result;
  }

  public void uncheckedScalarRightShiftAssign(ShortintServerKey serverKey, byte shift) {
    execute(() -> shortint_server_key_unchecked_scalar_right_shift_assign(serverKey.getValue(), getValue(), shift));
  }

  // ==========================================
  // Comparisons
  // ==========================================

  public ShortintCiphertext smartEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarEqual(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_equal(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartNotEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_not_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarNotEqual(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_not_equal(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartLess(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_less(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarLess(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_less(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartLessOrEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_less_or_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarLessOrEqual(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_less_or_equal(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartGreater(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_greater(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarGreater(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_greater(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartGreaterOrEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_greater_or_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext smartScalarGreaterOrEqual(ShortintServerKey serverKey, byte other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_smart_scalar_greater_or_equal(serverKey.getValue(), getValue(), other, result.getAddress()));
    return result;
  }

  // ==========================================
  // Unchecked Comparisons
  // ==========================================

  public ShortintCiphertext uncheckedEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext uncheckedNotEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_not_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext uncheckedLess(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_less(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext uncheckedLessOrEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_less_or_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext uncheckedGreater(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_greater(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext uncheckedGreaterOrEqual(ShortintServerKey serverKey, ShortintCiphertext other) {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_server_key_unchecked_greater_or_equal(serverKey.getValue(), getValue(), other.getValue(), result.getAddress()));
    return result;
  }
}
