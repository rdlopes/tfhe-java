package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.AbstractFheUnsignedInteger;
import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U2048;
import io.github.rdlopes.tfhe.core.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.FheRegistry;

/// Encrypted unsigned 2048-bit integer (`uint2048_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheUint2048 extends AbstractFheUnsignedInteger<U2048, FheUint2048, CompressedFheUint2048>
    implements FheUnsignedInteger<U2048, FheUint2048, CompressedFheUint2048> {

  static {
    FheRegistry.registerFactory(FheUint2048.class, FheUint2048::new);
  }

  static final FheTypeHandles<U2048> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(U2048::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint2048_destroy,
          TfheHeader::fhe_uint2048_clone,
          TfheHeader::fhe_uint2048_compress,
          TfheHeader::fhe_uint2048_safe_serialize,
          TfheHeader::fhe_uint2048_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint2048_try_encrypt_with_client_key_u2048,
          TfheHeader::fhe_uint2048_try_encrypt_with_public_key_u2048,
          TfheHeader::fhe_uint2048_try_encrypt_trivial_u2048,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint2048_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint2048_bitand,       TfheHeader::fhe_uint2048_bitor,      TfheHeader::fhe_uint2048_bitxor,
          TfheHeader::fhe_uint2048_not,
          TfheHeader::fhe_uint2048_bitand_assign, TfheHeader::fhe_uint2048_bitor_assign, TfheHeader::fhe_uint2048_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint2048_eq,     TfheHeader::fhe_uint2048_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint2048_lt,          TfheHeader::fhe_uint2048_le,
          TfheHeader::fhe_uint2048_gt,          TfheHeader::fhe_uint2048_ge,
          TfheHeader::fhe_uint2048_min,         TfheHeader::fhe_uint2048_max,
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint2048_add,          TfheHeader::fhe_uint2048_sub,        TfheHeader::fhe_uint2048_mul,
          TfheHeader::fhe_uint2048_div,          TfheHeader::fhe_uint2048_rem,
          TfheHeader::fhe_uint2048_overflowing_add, TfheHeader::fhe_uint2048_overflowing_sub, TfheHeader::fhe_uint2048_overflowing_mul,
          TfheHeader::fhe_uint2048_div_rem,
          TfheHeader::fhe_uint2048_add_assign,   TfheHeader::fhe_uint2048_sub_assign,  TfheHeader::fhe_uint2048_mul_assign,
          TfheHeader::fhe_uint2048_div_assign,   TfheHeader::fhe_uint2048_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint2048_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint2048_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint2048_ilog2,
          TfheHeader::fhe_uint2048_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint2048_shl,           TfheHeader::fhe_uint2048_shr,
          TfheHeader::fhe_uint2048_rotate_left,   TfheHeader::fhe_uint2048_rotate_right,
          TfheHeader::fhe_uint2048_shl_assign,    TfheHeader::fhe_uint2048_shr_assign,
          TfheHeader::fhe_uint2048_rotate_left_assign, TfheHeader::fhe_uint2048_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint2048_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint2048_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint2048_leading_ones,  TfheHeader::fhe_uint2048_leading_zeros,
          TfheHeader::fhe_uint2048_trailing_ones, TfheHeader::fhe_uint2048_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint2048,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint2048,
          TfheHeader::fhe_uint2048_if_then_else));

  FheUint2048() { super(TfheHeader::fhe_uint2048_destroy); }

  @Override protected FheTypeHandles<U2048> handles()      { return HANDLES; }
  @Override protected FheUint2048             newInstance()   { return new FheUint2048(); }
  @Override protected CompressedFheUint2048   newCompressed() { return new CompressedFheUint2048(); }

  public static FheUint2048 encrypt(U2048 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint2048::new);
  }
  public static FheUint2048 encrypt(U2048 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint2048::new);
  }
  public static FheUint2048 encrypt(U2048 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint2048::new);
  }
  public static FheUint2048 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint2048::new);
  }
  public static FheUint2048 ifThenElse(FheBool condition, FheUint2048 thenValue, FheUint2048 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint2048::new);
  }

}
