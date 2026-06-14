package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U256;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Encrypted unsigned 256-bit integer ({@code uint256_t}).
 *
 * <p>All FHE operations are implemented in {@link AbstractFheType}. This class
 * supplies only the static {@link #HANDLES} metadata record and type-specific
 * factory methods.
 */
public final class FheUint256 extends AbstractFheUnsignedInteger<U256, FheUint256, CompressedFheUint256>
    implements FheUnsignedInteger<U256, FheUint256, CompressedFheUint256> {

  static {
    FheRegistry.registerFactory(FheUint256.class, FheUint256::new);
  }

  static final FheTypeHandles<U256> HANDLES = new FheTypeHandles<>(
      new FheValueKind.Wide<>(U256::new),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint256_destroy,
          TfheHeader::fhe_uint256_clone,
          TfheHeader::fhe_uint256_compress,
          TfheHeader::fhe_uint256_safe_serialize,
          TfheHeader::fhe_uint256_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint256_try_encrypt_with_client_key_u256,
          TfheHeader::fhe_uint256_try_encrypt_with_public_key_u256,
          TfheHeader::fhe_uint256_try_encrypt_trivial_u256,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint256_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint256_bitand,       TfheHeader::fhe_uint256_bitor,      TfheHeader::fhe_uint256_bitxor,
          TfheHeader::fhe_uint256_not,
          TfheHeader::fhe_uint256_bitand_assign, TfheHeader::fhe_uint256_bitor_assign, TfheHeader::fhe_uint256_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint256_eq,     TfheHeader::fhe_uint256_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint256_lt,          TfheHeader::fhe_uint256_le,
          TfheHeader::fhe_uint256_gt,          TfheHeader::fhe_uint256_ge,
          TfheHeader::fhe_uint256_min,         TfheHeader::fhe_uint256_max,
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint256_add,          TfheHeader::fhe_uint256_sub,        TfheHeader::fhe_uint256_mul,
          TfheHeader::fhe_uint256_div,          TfheHeader::fhe_uint256_rem,
          TfheHeader::fhe_uint256_overflowing_add, TfheHeader::fhe_uint256_overflowing_sub, TfheHeader::fhe_uint256_overflowing_mul,
          TfheHeader::fhe_uint256_div_rem,
          TfheHeader::fhe_uint256_add_assign,   TfheHeader::fhe_uint256_sub_assign,  TfheHeader::fhe_uint256_mul_assign,
          TfheHeader::fhe_uint256_div_assign,   TfheHeader::fhe_uint256_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint256_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint256_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint256_ilog2,
          TfheHeader::fhe_uint256_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint256_shl,           TfheHeader::fhe_uint256_shr,
          TfheHeader::fhe_uint256_rotate_left,   TfheHeader::fhe_uint256_rotate_right,
          TfheHeader::fhe_uint256_shl_assign,    TfheHeader::fhe_uint256_shr_assign,
          TfheHeader::fhe_uint256_rotate_left_assign, TfheHeader::fhe_uint256_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint256_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint256_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint256_leading_ones,  TfheHeader::fhe_uint256_leading_zeros,
          TfheHeader::fhe_uint256_trailing_ones, TfheHeader::fhe_uint256_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint256,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint256,
          TfheHeader::fhe_uint256_if_then_else));

  FheUint256() { super(TfheHeader::fhe_uint256_destroy); }

  @Override protected FheTypeHandles<U256> handles()      { return HANDLES; }
  @Override protected FheUint256             newInstance()   { return new FheUint256(); }
  @Override protected CompressedFheUint256   newCompressed() { return new CompressedFheUint256(); }

  public static FheUint256 encrypt(U256 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint256::new);
  }
  public static FheUint256 encrypt(U256 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint256::new);
  }
  public static FheUint256 encrypt(U256 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint256::new);
  }
  public static FheUint256 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint256::new);
  }
  public static FheUint256 ifThenElse(FheBool condition, FheUint256 thenValue, FheUint256 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint256::new);
  }

}
