package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U128;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Encrypted unsigned 128-bit integer ({@code uint128_t}).
 *
 * <p>All FHE operations are implemented in {@link AbstractFheType}. This class
 * supplies only the static {@link #HANDLES} metadata record and type-specific
 * factory methods.
 */
public final class FheUint128 extends AbstractFheUnsignedInteger<U128, FheUint128, CompressedFheUint128>
    implements FheUnsignedInteger<U128, FheUint128, CompressedFheUint128> {

  static {
    FheRegistry.registerFactory(FheUint128.class, FheUint128::new);
  }

  static final FheTypeHandles<U128> HANDLES = new FheTypeHandles<>(
      new FheValueKind.Wide<>(U128::new),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint128_destroy,
          TfheHeader::fhe_uint128_clone,
          TfheHeader::fhe_uint128_compress,
          TfheHeader::fhe_uint128_safe_serialize,
          TfheHeader::fhe_uint128_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint128_try_encrypt_with_client_key_u128,
          TfheHeader::fhe_uint128_try_encrypt_with_public_key_u128,
          TfheHeader::fhe_uint128_try_encrypt_trivial_u128,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint128_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint128_bitand,       TfheHeader::fhe_uint128_bitor,      TfheHeader::fhe_uint128_bitxor,
          TfheHeader::fhe_uint128_not,
          TfheHeader::fhe_uint128_bitand_assign, TfheHeader::fhe_uint128_bitor_assign, TfheHeader::fhe_uint128_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint128_eq,     TfheHeader::fhe_uint128_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint128_lt,          TfheHeader::fhe_uint128_le,
          TfheHeader::fhe_uint128_gt,          TfheHeader::fhe_uint128_ge,
          TfheHeader::fhe_uint128_min,         TfheHeader::fhe_uint128_max,
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint128_add,          TfheHeader::fhe_uint128_sub,        TfheHeader::fhe_uint128_mul,
          TfheHeader::fhe_uint128_div,          TfheHeader::fhe_uint128_rem,
          TfheHeader::fhe_uint128_overflowing_add, TfheHeader::fhe_uint128_overflowing_sub, TfheHeader::fhe_uint128_overflowing_mul,
          TfheHeader::fhe_uint128_div_rem,
          TfheHeader::fhe_uint128_add_assign,   TfheHeader::fhe_uint128_sub_assign,  TfheHeader::fhe_uint128_mul_assign,
          TfheHeader::fhe_uint128_div_assign,   TfheHeader::fhe_uint128_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint128_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint128_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint128_ilog2,
          TfheHeader::fhe_uint128_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint128_shl,           TfheHeader::fhe_uint128_shr,
          TfheHeader::fhe_uint128_rotate_left,   TfheHeader::fhe_uint128_rotate_right,
          TfheHeader::fhe_uint128_shl_assign,    TfheHeader::fhe_uint128_shr_assign,
          TfheHeader::fhe_uint128_rotate_left_assign, TfheHeader::fhe_uint128_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint128_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint128_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint128_leading_ones,  TfheHeader::fhe_uint128_leading_zeros,
          TfheHeader::fhe_uint128_trailing_ones, TfheHeader::fhe_uint128_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint128,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint128,
          TfheHeader::fhe_uint128_if_then_else));

  FheUint128() { super(TfheHeader::fhe_uint128_destroy); }

  @Override protected FheTypeHandles<U128> handles()      { return HANDLES; }
  @Override protected FheUint128             newInstance()   { return new FheUint128(); }
  @Override protected CompressedFheUint128   newCompressed() { return new CompressedFheUint128(); }

  public static FheUint128 encrypt(U128 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint128::new);
  }
  public static FheUint128 encrypt(U128 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint128::new);
  }
  public static FheUint128 encrypt(U128 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint128::new);
  }
  public static FheUint128 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint128::new);
  }
  public static FheUint128 ifThenElse(FheBool condition, FheUint128 thenValue, FheUint128 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint128::new);
  }

}
