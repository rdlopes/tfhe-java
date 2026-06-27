package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.I1024;
import io.github.rdlopes.tfhe.core.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.FheRegistry;

/// Encrypted signed 1024-bit integer (`int1024_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheInt1024 extends AbstractFheType<I1024, FheInt1024, CompressedFheInt1024>
  implements FheSignedInteger<I1024, FheInt1024, CompressedFheInt1024> {

  static {
    FheRegistry.registerFactory(FheInt1024.class, FheInt1024::new);
  }

  static final FheTypeHandles<I1024> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(I1024::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int1024_destroy,
          TfheHeader::fhe_int1024_clone,
          TfheHeader::fhe_int1024_compress,
          TfheHeader::fhe_int1024_safe_serialize,
          TfheHeader::fhe_int1024_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int1024_try_encrypt_with_client_key_i1024,
          TfheHeader::fhe_int1024_try_encrypt_with_public_key_i1024,
          TfheHeader::fhe_int1024_try_encrypt_trivial_i1024,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int1024_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int1024_bitand,       TfheHeader::fhe_int1024_bitor,      TfheHeader::fhe_int1024_bitxor,
          TfheHeader::fhe_int1024_not,
          TfheHeader::fhe_int1024_bitand_assign, TfheHeader::fhe_int1024_bitor_assign, TfheHeader::fhe_int1024_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int1024_eq,     TfheHeader::fhe_int1024_ne,
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int1024_lt,          TfheHeader::fhe_int1024_le,
          TfheHeader::fhe_int1024_gt,          TfheHeader::fhe_int1024_ge,
          TfheHeader::fhe_int1024_min,         TfheHeader::fhe_int1024_max,
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int1024_add,          TfheHeader::fhe_int1024_sub,        TfheHeader::fhe_int1024_mul,
          TfheHeader::fhe_int1024_div,          TfheHeader::fhe_int1024_rem,
          TfheHeader::fhe_int1024_overflowing_add, TfheHeader::fhe_int1024_overflowing_sub, TfheHeader::fhe_int1024_overflowing_mul,
          TfheHeader::fhe_int1024_div_rem,
          TfheHeader::fhe_int1024_add_assign,   TfheHeader::fhe_int1024_sub_assign,  TfheHeader::fhe_int1024_mul_assign,
          TfheHeader::fhe_int1024_div_assign,   TfheHeader::fhe_int1024_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int1024_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int1024_neg,
          TfheHeader::fhe_int1024_abs,          // non-null: signed
          TfheHeader::fhe_int1024_ilog2,
          TfheHeader::fhe_int1024_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int1024_shl,           TfheHeader::fhe_int1024_shr,
          TfheHeader::fhe_int1024_rotate_left,   TfheHeader::fhe_int1024_rotate_right,
          TfheHeader::fhe_int1024_shl_assign,    TfheHeader::fhe_int1024_shr_assign,
          TfheHeader::fhe_int1024_rotate_left_assign, TfheHeader::fhe_int1024_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int1024_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int1024_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int1024_leading_ones,  TfheHeader::fhe_int1024_leading_zeros,
          TfheHeader::fhe_int1024_trailing_ones, TfheHeader::fhe_int1024_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int1024,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int1024,
          TfheHeader::fhe_int1024_if_then_else));

  FheInt1024() { super(TfheHeader::fhe_int1024_destroy); }

  @Override protected FheTypeHandles<I1024> handles()      { return HANDLES; }
  @Override protected FheInt1024             newInstance()   { return new FheInt1024(); }
  @Override protected CompressedFheInt1024   newCompressed() { return new CompressedFheInt1024(); }

  public static FheInt1024 encrypt(I1024 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt1024::new);
  }
  public static FheInt1024 encrypt(I1024 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt1024::new);
  }
  public static FheInt1024 encrypt(I1024 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt1024::new);
  }
  public static FheInt1024 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt1024::new);
  }
  public static FheInt1024 ifThenElse(FheBool condition, FheInt1024 thenValue, FheInt1024 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt1024::new);
  }

}
