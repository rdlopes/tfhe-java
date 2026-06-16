package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.Fhe;
import io.github.rdlopes.tfhe.api.types.FheBool;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.I512;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted signed 512-bit integer (`int512_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheInt512 extends AbstractFheType<I512, FheInt512, CompressedFheInt512>
  implements FheSignedInteger<I512, FheInt512, CompressedFheInt512> {

  static final FheTypeHandles<I512> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(I512::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int512_destroy,
          TfheHeader::fhe_int512_clone,
          TfheHeader::fhe_int512_compress,
          TfheHeader::fhe_int512_safe_serialize,
          TfheHeader::fhe_int512_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int512_try_encrypt_with_client_key_i512,
          TfheHeader::fhe_int512_try_encrypt_with_public_key_i512,
          TfheHeader::fhe_int512_try_encrypt_trivial_i512,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int512_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int512_bitand,       TfheHeader::fhe_int512_bitor,      TfheHeader::fhe_int512_bitxor,
          TfheHeader::fhe_int512_not,
          TfheHeader::fhe_int512_bitand_assign, TfheHeader::fhe_int512_bitor_assign, TfheHeader::fhe_int512_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int512_eq,     TfheHeader::fhe_int512_ne,
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int512_lt,          TfheHeader::fhe_int512_le,
          TfheHeader::fhe_int512_gt,          TfheHeader::fhe_int512_ge,
          TfheHeader::fhe_int512_min,         TfheHeader::fhe_int512_max,
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int512_add,          TfheHeader::fhe_int512_sub,        TfheHeader::fhe_int512_mul,
          TfheHeader::fhe_int512_div,          TfheHeader::fhe_int512_rem,
          TfheHeader::fhe_int512_overflowing_add, TfheHeader::fhe_int512_overflowing_sub, TfheHeader::fhe_int512_overflowing_mul,
          TfheHeader::fhe_int512_div_rem,
          TfheHeader::fhe_int512_add_assign,   TfheHeader::fhe_int512_sub_assign,  TfheHeader::fhe_int512_mul_assign,
          TfheHeader::fhe_int512_div_assign,   TfheHeader::fhe_int512_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int512_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int512_neg,
          TfheHeader::fhe_int512_abs,          // non-null: signed
          TfheHeader::fhe_int512_ilog2,
          TfheHeader::fhe_int512_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int512_shl,           TfheHeader::fhe_int512_shr,
          TfheHeader::fhe_int512_rotate_left,   TfheHeader::fhe_int512_rotate_right,
          TfheHeader::fhe_int512_shl_assign,    TfheHeader::fhe_int512_shr_assign,
          TfheHeader::fhe_int512_rotate_left_assign, TfheHeader::fhe_int512_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int512_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int512_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int512_leading_ones,  TfheHeader::fhe_int512_leading_zeros,
          TfheHeader::fhe_int512_trailing_ones, TfheHeader::fhe_int512_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int512,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int512,
          TfheHeader::fhe_int512_if_then_else));

  static {
    FheRegistry.registerFactory(FheInt512.class, FheInt512::new);
    FheRegistry.registerHandles(FheInt512.class, HANDLES);
  }

  FheInt512() { super(TfheHeader::fhe_int512_destroy); }

  @Override protected FheTypeHandles<I512> handles()      { return HANDLES; }
  @Override protected FheInt512             newInstance()   { return new FheInt512(); }
  @Override protected CompressedFheInt512   newCompressed() { return new CompressedFheInt512(); }

  public static FheInt512 encrypt(I512 clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheInt512.class);
  }
  public static FheInt512 encrypt(I512 clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheInt512.class);
  }
  public static FheInt512 encrypt(I512 clearValue) {
    return Fhe.encrypt(clearValue, FheInt512.class);
  }
  public static FheInt512 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheInt512.class);
  }
  public static FheInt512 ifThenElse(FheBool condition, FheInt512 thenValue, FheInt512 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }


  @Override
  public FheInt512 abs() {
    return absImpl();
  }
}
