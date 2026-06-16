package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.Fhe;
import io.github.rdlopes.tfhe.api.types.FheBool;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.AbstractFheUnsignedInteger;
import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U1024;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted unsigned 1024-bit integer (`uint1024_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheUint1024 extends AbstractFheUnsignedInteger<U1024, FheUint1024, CompressedFheUint1024>
    implements FheUnsignedInteger<U1024, FheUint1024, CompressedFheUint1024> {

  static final FheTypeHandles<U1024> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(U1024::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint1024_destroy,
          TfheHeader::fhe_uint1024_clone,
          TfheHeader::fhe_uint1024_compress,
          TfheHeader::fhe_uint1024_safe_serialize,
          TfheHeader::fhe_uint1024_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint1024_try_encrypt_with_client_key_u1024,
          TfheHeader::fhe_uint1024_try_encrypt_with_public_key_u1024,
          TfheHeader::fhe_uint1024_try_encrypt_trivial_u1024,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint1024_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint1024_bitand,       TfheHeader::fhe_uint1024_bitor,      TfheHeader::fhe_uint1024_bitxor,
          TfheHeader::fhe_uint1024_not,
          TfheHeader::fhe_uint1024_bitand_assign, TfheHeader::fhe_uint1024_bitor_assign, TfheHeader::fhe_uint1024_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint1024_eq,     TfheHeader::fhe_uint1024_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint1024_lt,          TfheHeader::fhe_uint1024_le,
          TfheHeader::fhe_uint1024_gt,          TfheHeader::fhe_uint1024_ge,
          TfheHeader::fhe_uint1024_min,         TfheHeader::fhe_uint1024_max,
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint1024_add,          TfheHeader::fhe_uint1024_sub,        TfheHeader::fhe_uint1024_mul,
          TfheHeader::fhe_uint1024_div,          TfheHeader::fhe_uint1024_rem,
          TfheHeader::fhe_uint1024_overflowing_add, TfheHeader::fhe_uint1024_overflowing_sub, TfheHeader::fhe_uint1024_overflowing_mul,
          TfheHeader::fhe_uint1024_div_rem,
          TfheHeader::fhe_uint1024_add_assign,   TfheHeader::fhe_uint1024_sub_assign,  TfheHeader::fhe_uint1024_mul_assign,
          TfheHeader::fhe_uint1024_div_assign,   TfheHeader::fhe_uint1024_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint1024_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint1024_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint1024_ilog2,
          TfheHeader::fhe_uint1024_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint1024_shl,           TfheHeader::fhe_uint1024_shr,
          TfheHeader::fhe_uint1024_rotate_left,   TfheHeader::fhe_uint1024_rotate_right,
          TfheHeader::fhe_uint1024_shl_assign,    TfheHeader::fhe_uint1024_shr_assign,
          TfheHeader::fhe_uint1024_rotate_left_assign, TfheHeader::fhe_uint1024_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint1024_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint1024_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint1024_leading_ones,  TfheHeader::fhe_uint1024_leading_zeros,
          TfheHeader::fhe_uint1024_trailing_ones, TfheHeader::fhe_uint1024_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint1024,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint1024,
          TfheHeader::fhe_uint1024_if_then_else));

  static {
    FheRegistry.registerFactory(FheUint1024.class, FheUint1024::new);
    FheRegistry.registerHandles(FheUint1024.class, HANDLES);
  }

  FheUint1024() { super(TfheHeader::fhe_uint1024_destroy); }

  @Override protected FheTypeHandles<U1024> handles()      { return HANDLES; }
  @Override protected FheUint1024             newInstance()   { return new FheUint1024(); }
  @Override protected CompressedFheUint1024   newCompressed() { return new CompressedFheUint1024(); }

  public static FheUint1024 encrypt(U1024 clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheUint1024.class);
  }
  public static FheUint1024 encrypt(U1024 clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheUint1024.class);
  }
  public static FheUint1024 encrypt(U1024 clearValue) {
    return Fhe.encrypt(clearValue, FheUint1024.class);
  }
  public static FheUint1024 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheUint1024.class);
  }
  public static FheUint1024 ifThenElse(FheBool condition, FheUint1024 thenValue, FheUint1024 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }

}
