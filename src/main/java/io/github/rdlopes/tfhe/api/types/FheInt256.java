package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I256;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted signed 256-bit integer (`int256_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
public final class FheInt256 extends AbstractFheType<I256, FheInt256, CompressedFheInt256>
  implements FheSignedInteger<I256, FheInt256, CompressedFheInt256> {

  static {
    FheRegistry.registerFactory(FheInt256.class, FheInt256::new);
  }

  static final FheTypeHandles<I256> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(I256::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int256_destroy,
          TfheHeader::fhe_int256_clone,
          TfheHeader::fhe_int256_compress,
          TfheHeader::fhe_int256_safe_serialize,
          TfheHeader::fhe_int256_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int256_try_encrypt_with_client_key_i256,
          TfheHeader::fhe_int256_try_encrypt_with_public_key_i256,
          TfheHeader::fhe_int256_try_encrypt_trivial_i256,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int256_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int256_bitand,       TfheHeader::fhe_int256_bitor,      TfheHeader::fhe_int256_bitxor,
          TfheHeader::fhe_int256_not,
          TfheHeader::fhe_int256_bitand_assign, TfheHeader::fhe_int256_bitor_assign, TfheHeader::fhe_int256_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int256_eq,     TfheHeader::fhe_int256_ne,
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int256_lt,          TfheHeader::fhe_int256_le,
          TfheHeader::fhe_int256_gt,          TfheHeader::fhe_int256_ge,
          TfheHeader::fhe_int256_min,         TfheHeader::fhe_int256_max,
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int256_add,          TfheHeader::fhe_int256_sub,        TfheHeader::fhe_int256_mul,
          TfheHeader::fhe_int256_div,          TfheHeader::fhe_int256_rem,
          TfheHeader::fhe_int256_overflowing_add, TfheHeader::fhe_int256_overflowing_sub, TfheHeader::fhe_int256_overflowing_mul,
          TfheHeader::fhe_int256_div_rem,
          TfheHeader::fhe_int256_add_assign,   TfheHeader::fhe_int256_sub_assign,  TfheHeader::fhe_int256_mul_assign,
          TfheHeader::fhe_int256_div_assign,   TfheHeader::fhe_int256_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int256_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int256_neg,
          TfheHeader::fhe_int256_abs,          // non-null: signed
          TfheHeader::fhe_int256_ilog2,
          TfheHeader::fhe_int256_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int256_shl,           TfheHeader::fhe_int256_shr,
          TfheHeader::fhe_int256_rotate_left,   TfheHeader::fhe_int256_rotate_right,
          TfheHeader::fhe_int256_shl_assign,    TfheHeader::fhe_int256_shr_assign,
          TfheHeader::fhe_int256_rotate_left_assign, TfheHeader::fhe_int256_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int256_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int256_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int256_leading_ones,  TfheHeader::fhe_int256_leading_zeros,
          TfheHeader::fhe_int256_trailing_ones, TfheHeader::fhe_int256_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int256,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int256,
          TfheHeader::fhe_int256_if_then_else));

  FheInt256() { super(TfheHeader::fhe_int256_destroy); }

  @Override protected FheTypeHandles<I256> handles()      { return HANDLES; }
  @Override protected FheInt256             newInstance()   { return new FheInt256(); }
  @Override protected CompressedFheInt256   newCompressed() { return new CompressedFheInt256(); }

  public static FheInt256 encrypt(I256 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt256::new);
  }
  public static FheInt256 encrypt(I256 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt256::new);
  }
  public static FheInt256 encrypt(I256 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt256::new);
  }
  public static FheInt256 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt256::new);
  }
  public static FheInt256 ifThenElse(FheBool condition, FheInt256 thenValue, FheInt256 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt256::new);
  }

}
