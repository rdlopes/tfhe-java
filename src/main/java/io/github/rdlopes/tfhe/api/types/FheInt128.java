package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I128;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted signed 128-bit integer (`int128_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
public final class FheInt128 extends AbstractFheType<I128, FheInt128, CompressedFheInt128>
  implements FheSignedInteger<I128, FheInt128, CompressedFheInt128> {

  static {
    FheRegistry.registerFactory(FheInt128.class, FheInt128::new);
  }

  static final FheTypeHandles<I128> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(I128::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int128_destroy,
          TfheHeader::fhe_int128_clone,
          TfheHeader::fhe_int128_compress,
          TfheHeader::fhe_int128_safe_serialize,
          TfheHeader::fhe_int128_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int128_try_encrypt_with_client_key_i128,
          TfheHeader::fhe_int128_try_encrypt_with_public_key_i128,
          TfheHeader::fhe_int128_try_encrypt_trivial_i128,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int128_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int128_bitand,       TfheHeader::fhe_int128_bitor,      TfheHeader::fhe_int128_bitxor,
          TfheHeader::fhe_int128_not,
          TfheHeader::fhe_int128_bitand_assign, TfheHeader::fhe_int128_bitor_assign, TfheHeader::fhe_int128_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int128_eq,     TfheHeader::fhe_int128_ne,
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int128_lt,          TfheHeader::fhe_int128_le,
          TfheHeader::fhe_int128_gt,          TfheHeader::fhe_int128_ge,
          TfheHeader::fhe_int128_min,         TfheHeader::fhe_int128_max,
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int128_add,          TfheHeader::fhe_int128_sub,        TfheHeader::fhe_int128_mul,
          TfheHeader::fhe_int128_div,          TfheHeader::fhe_int128_rem,
          TfheHeader::fhe_int128_overflowing_add, TfheHeader::fhe_int128_overflowing_sub, TfheHeader::fhe_int128_overflowing_mul,
          TfheHeader::fhe_int128_div_rem,
          TfheHeader::fhe_int128_add_assign,   TfheHeader::fhe_int128_sub_assign,  TfheHeader::fhe_int128_mul_assign,
          TfheHeader::fhe_int128_div_assign,   TfheHeader::fhe_int128_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int128_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int128_neg,
          TfheHeader::fhe_int128_abs,          // non-null: signed
          TfheHeader::fhe_int128_ilog2,
          TfheHeader::fhe_int128_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int128_shl,           TfheHeader::fhe_int128_shr,
          TfheHeader::fhe_int128_rotate_left,   TfheHeader::fhe_int128_rotate_right,
          TfheHeader::fhe_int128_shl_assign,    TfheHeader::fhe_int128_shr_assign,
          TfheHeader::fhe_int128_rotate_left_assign, TfheHeader::fhe_int128_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int128_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int128_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int128_leading_ones,  TfheHeader::fhe_int128_leading_zeros,
          TfheHeader::fhe_int128_trailing_ones, TfheHeader::fhe_int128_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int128,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int128,
          TfheHeader::fhe_int128_if_then_else));

  FheInt128() { super(TfheHeader::fhe_int128_destroy); }

  @Override protected FheTypeHandles<I128> handles()      { return HANDLES; }
  @Override protected FheInt128             newInstance()   { return new FheInt128(); }
  @Override protected CompressedFheInt128   newCompressed() { return new CompressedFheInt128(); }

  public static FheInt128 encrypt(I128 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt128::new);
  }
  public static FheInt128 encrypt(I128 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt128::new);
  }
  public static FheInt128 encrypt(I128 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt128::new);
  }
  public static FheInt128 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt128::new);
  }
  public static FheInt128 ifThenElse(FheBool condition, FheInt128 thenValue, FheInt128 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt128::new);
  }

}
