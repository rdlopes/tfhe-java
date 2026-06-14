package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I2048;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Encrypted signed 2048-bit integer ({@code int2048_t}).
 *
 * <p>All FHE operations are implemented in {@link AbstractFheType}. This class
 * supplies only the static {@link #HANDLES} metadata record and type-specific
 * factory methods.
 */
public final class FheInt2048 extends AbstractFheType<I2048, FheInt2048, CompressedFheInt2048>
    implements FheInteger<I2048, FheInt2048, CompressedFheInt2048> {

  static {
    FheRegistry.registerFactory(FheInt2048.class, FheInt2048::new);
  }

  static final FheTypeHandles<I2048> HANDLES = new FheTypeHandles<>(
      new FheValueKind.Wide<>(I2048::new),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int2048_destroy,
          TfheHeader::fhe_int2048_clone,
          TfheHeader::fhe_int2048_compress,
          TfheHeader::fhe_int2048_safe_serialize,
          TfheHeader::fhe_int2048_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int2048_try_encrypt_with_client_key_i2048,
          TfheHeader::fhe_int2048_try_encrypt_with_public_key_i2048,
          TfheHeader::fhe_int2048_try_encrypt_trivial_i2048,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int2048_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int2048_bitand,       TfheHeader::fhe_int2048_bitor,      TfheHeader::fhe_int2048_bitxor,
          TfheHeader::fhe_int2048_not,
          TfheHeader::fhe_int2048_bitand_assign, TfheHeader::fhe_int2048_bitor_assign, TfheHeader::fhe_int2048_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int2048_eq,     TfheHeader::fhe_int2048_ne,
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int2048_lt,          TfheHeader::fhe_int2048_le,
          TfheHeader::fhe_int2048_gt,          TfheHeader::fhe_int2048_ge,
          TfheHeader::fhe_int2048_min,         TfheHeader::fhe_int2048_max,
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int2048_add,          TfheHeader::fhe_int2048_sub,        TfheHeader::fhe_int2048_mul,
          TfheHeader::fhe_int2048_div,          TfheHeader::fhe_int2048_rem,
          TfheHeader::fhe_int2048_overflowing_add, TfheHeader::fhe_int2048_overflowing_sub, TfheHeader::fhe_int2048_overflowing_mul,
          TfheHeader::fhe_int2048_div_rem,
          TfheHeader::fhe_int2048_add_assign,   TfheHeader::fhe_int2048_sub_assign,  TfheHeader::fhe_int2048_mul_assign,
          TfheHeader::fhe_int2048_div_assign,   TfheHeader::fhe_int2048_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int2048_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int2048_neg,
          TfheHeader::fhe_int2048_abs,          // non-null: signed
          TfheHeader::fhe_int2048_ilog2,
          TfheHeader::fhe_int2048_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int2048_shl,           TfheHeader::fhe_int2048_shr,
          TfheHeader::fhe_int2048_rotate_left,   TfheHeader::fhe_int2048_rotate_right,
          TfheHeader::fhe_int2048_shl_assign,    TfheHeader::fhe_int2048_shr_assign,
          TfheHeader::fhe_int2048_rotate_left_assign, TfheHeader::fhe_int2048_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int2048_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int2048_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int2048_leading_ones,  TfheHeader::fhe_int2048_leading_zeros,
          TfheHeader::fhe_int2048_trailing_ones, TfheHeader::fhe_int2048_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int2048,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int2048,
          TfheHeader::fhe_int2048_if_then_else));

  FheInt2048() { super(TfheHeader::fhe_int2048_destroy); }

  @Override protected FheTypeHandles<I2048> handles()      { return HANDLES; }
  @Override protected FheInt2048             newInstance()   { return new FheInt2048(); }
  @Override protected CompressedFheInt2048   newCompressed() { return new CompressedFheInt2048(); }

  public static FheInt2048 encrypt(I2048 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt2048::new);
  }
  public static FheInt2048 encrypt(I2048 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt2048::new);
  }
  public static FheInt2048 encrypt(I2048 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt2048::new);
  }
  public static FheInt2048 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt2048::new);
  }
  public static FheInt2048 ifThenElse(FheBool condition, FheInt2048 thenValue, FheInt2048 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt2048::new);
  }

}
