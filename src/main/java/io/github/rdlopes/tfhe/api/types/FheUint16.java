package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class FheUint16 extends AbstractFheUnsignedInteger<Short, FheUint16, CompressedFheUint16>
    implements FheUnsignedInteger<Short, FheUint16, CompressedFheUint16> {

  static {
    FheRegistry.registerFactory(FheUint16.class, FheUint16::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint16_destroy,
          TfheHeader::fhe_uint16_clone,
          TfheHeader::fhe_uint16_compress,
          TfheHeader::fhe_uint16_safe_serialize,
          TfheHeader::fhe_uint16_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint16_try_encrypt_with_client_key_u16,
          TfheHeader::fhe_uint16_try_encrypt_with_public_key_u16,
          TfheHeader::fhe_uint16_try_encrypt_trivial_u16,
          null, null, null,
          TfheHeader::fhe_uint16_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint16_bitand,       TfheHeader::fhe_uint16_bitor,      TfheHeader::fhe_uint16_bitxor,
          TfheHeader::fhe_uint16_not,
          TfheHeader::fhe_uint16_bitand_assign, TfheHeader::fhe_uint16_bitor_assign, TfheHeader::fhe_uint16_bitxor_assign,
          TfheHeader::fhe_uint16_scalar_bitand, TfheHeader::fhe_uint16_scalar_bitor, TfheHeader::fhe_uint16_scalar_bitxor,
          TfheHeader::fhe_uint16_scalar_bitand_assign, TfheHeader::fhe_uint16_scalar_bitor_assign, TfheHeader::fhe_uint16_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint16_eq,     TfheHeader::fhe_uint16_ne,
          TfheHeader::fhe_uint16_scalar_eq, TfheHeader::fhe_uint16_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint16_lt,          TfheHeader::fhe_uint16_le,
          TfheHeader::fhe_uint16_gt,          TfheHeader::fhe_uint16_ge,
          TfheHeader::fhe_uint16_min,         TfheHeader::fhe_uint16_max,
          TfheHeader::fhe_uint16_scalar_lt,   TfheHeader::fhe_uint16_scalar_le,
          TfheHeader::fhe_uint16_scalar_gt,   TfheHeader::fhe_uint16_scalar_ge,
          TfheHeader::fhe_uint16_scalar_min,  TfheHeader::fhe_uint16_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint16_add,          TfheHeader::fhe_uint16_sub,        TfheHeader::fhe_uint16_mul,
          TfheHeader::fhe_uint16_div,          TfheHeader::fhe_uint16_rem,
          TfheHeader::fhe_uint16_overflowing_add, TfheHeader::fhe_uint16_overflowing_sub, TfheHeader::fhe_uint16_overflowing_mul,
          TfheHeader::fhe_uint16_div_rem,
          TfheHeader::fhe_uint16_add_assign,   TfheHeader::fhe_uint16_sub_assign,  TfheHeader::fhe_uint16_mul_assign,
          TfheHeader::fhe_uint16_div_assign,   TfheHeader::fhe_uint16_rem_assign,
          TfheHeader::fhe_uint16_scalar_add,   TfheHeader::fhe_uint16_scalar_sub,  TfheHeader::fhe_uint16_scalar_mul,
          TfheHeader::fhe_uint16_scalar_div,   TfheHeader::fhe_uint16_scalar_rem,
          TfheHeader::fhe_uint16_scalar_add_assign, TfheHeader::fhe_uint16_scalar_sub_assign, TfheHeader::fhe_uint16_scalar_mul_assign,
          TfheHeader::fhe_uint16_scalar_div_assign, TfheHeader::fhe_uint16_scalar_rem_assign,
          TfheHeader::fhe_uint16_scalar_div_rem,
          TfheHeader::fhe_uint16_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint16_ilog2,
          TfheHeader::fhe_uint16_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint16_shl,           TfheHeader::fhe_uint16_shr,
          TfheHeader::fhe_uint16_rotate_left,   TfheHeader::fhe_uint16_rotate_right,
          TfheHeader::fhe_uint16_shl_assign,    TfheHeader::fhe_uint16_shr_assign,
          TfheHeader::fhe_uint16_rotate_left_assign, TfheHeader::fhe_uint16_rotate_right_assign,
          TfheHeader::fhe_uint16_scalar_shl,    TfheHeader::fhe_uint16_scalar_shr,
          TfheHeader::fhe_uint16_scalar_rotate_left, TfheHeader::fhe_uint16_scalar_rotate_right,
          TfheHeader::fhe_uint16_scalar_shl_assign, TfheHeader::fhe_uint16_scalar_shr_assign,
          TfheHeader::fhe_uint16_scalar_rotate_left_assign, TfheHeader::fhe_uint16_scalar_rotate_right_assign,
          TfheHeader::fhe_uint16_leading_ones,  TfheHeader::fhe_uint16_leading_zeros,
          TfheHeader::fhe_uint16_trailing_ones, TfheHeader::fhe_uint16_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint16,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint16,
          TfheHeader::fhe_uint16_if_then_else));

  FheUint16() { super(TfheHeader::fhe_uint16_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheUint16            newInstance()   { return new FheUint16(); }
  @Override protected CompressedFheUint16  newCompressed() { return new CompressedFheUint16(); }

  public static FheUint16 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint16::new);
  }
  public static FheUint16 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint16::new);
  }
  public static FheUint16 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint16::new);
  }
  public static FheUint16 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint16::new);
  }
  public static FheUint16 ifThenElse(FheBool condition, FheUint16 thenValue, FheUint16 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint16::new);
  }

}
