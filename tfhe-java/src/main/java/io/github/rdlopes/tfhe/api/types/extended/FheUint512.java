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
import io.github.rdlopes.tfhe.api.values.extended.U512;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted unsigned 512-bit integer (`uint512_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheUint512 extends AbstractFheUnsignedInteger<U512, FheUint512, CompressedFheUint512>
    implements FheUnsignedInteger<U512, FheUint512, CompressedFheUint512> {

  static final FheTypeHandles<U512> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(U512::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint512_destroy,
          TfheHeader::fhe_uint512_clone,
          TfheHeader::fhe_uint512_compress,
          TfheHeader::fhe_uint512_safe_serialize,
          TfheHeader::fhe_uint512_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint512_try_encrypt_with_client_key_u512,
          TfheHeader::fhe_uint512_try_encrypt_with_public_key_u512,
          TfheHeader::fhe_uint512_try_encrypt_trivial_u512,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint512_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint512_bitand,       TfheHeader::fhe_uint512_bitor,      TfheHeader::fhe_uint512_bitxor,
          TfheHeader::fhe_uint512_not,
          TfheHeader::fhe_uint512_bitand_assign, TfheHeader::fhe_uint512_bitor_assign, TfheHeader::fhe_uint512_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint512_eq,     TfheHeader::fhe_uint512_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint512_lt,          TfheHeader::fhe_uint512_le,
          TfheHeader::fhe_uint512_gt,          TfheHeader::fhe_uint512_ge,
          TfheHeader::fhe_uint512_min,         TfheHeader::fhe_uint512_max,
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint512_add,          TfheHeader::fhe_uint512_sub,        TfheHeader::fhe_uint512_mul,
          TfheHeader::fhe_uint512_div,          TfheHeader::fhe_uint512_rem,
          TfheHeader::fhe_uint512_overflowing_add, TfheHeader::fhe_uint512_overflowing_sub, TfheHeader::fhe_uint512_overflowing_mul,
          TfheHeader::fhe_uint512_div_rem,
          TfheHeader::fhe_uint512_add_assign,   TfheHeader::fhe_uint512_sub_assign,  TfheHeader::fhe_uint512_mul_assign,
          TfheHeader::fhe_uint512_div_assign,   TfheHeader::fhe_uint512_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint512_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint512_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint512_ilog2,
          TfheHeader::fhe_uint512_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint512_shl,           TfheHeader::fhe_uint512_shr,
          TfheHeader::fhe_uint512_rotate_left,   TfheHeader::fhe_uint512_rotate_right,
          TfheHeader::fhe_uint512_shl_assign,    TfheHeader::fhe_uint512_shr_assign,
          TfheHeader::fhe_uint512_rotate_left_assign, TfheHeader::fhe_uint512_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint512_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint512_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint512_leading_ones,  TfheHeader::fhe_uint512_leading_zeros,
          TfheHeader::fhe_uint512_trailing_ones, TfheHeader::fhe_uint512_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint512,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint512,
          TfheHeader::fhe_uint512_if_then_else));

  static {
    FheRegistry.registerFactory(FheUint512.class, FheUint512::new);
    FheRegistry.registerHandles(FheUint512.class, HANDLES);
  }

  FheUint512() { super(TfheHeader::fhe_uint512_destroy); }

  @Override protected FheTypeHandles<U512> handles()      { return HANDLES; }
  @Override protected FheUint512             newInstance()   { return new FheUint512(); }
  @Override protected CompressedFheUint512   newCompressed() { return new CompressedFheUint512(); }

  public static FheUint512 encrypt(U512 clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheUint512.class);
  }
  public static FheUint512 encrypt(U512 clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheUint512.class);
  }
  public static FheUint512 encrypt(U512 clearValue) {
    return Fhe.encrypt(clearValue, FheUint512.class);
  }
  public static FheUint512 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheUint512.class);
  }
  public static FheUint512 ifThenElse(FheBool condition, FheUint512 thenValue, FheUint512 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }

}
