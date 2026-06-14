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

public final class FheInt16 extends AbstractFheType<Short, FheInt16, CompressedFheInt16>
    implements FheInteger<Short, FheInt16, CompressedFheInt16> {

  static {
    FheRegistry.registerFactory(FheInt16.class, FheInt16::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int16_destroy,
          TfheHeader::fhe_int16_clone,
          TfheHeader::fhe_int16_compress,
          TfheHeader::fhe_int16_safe_serialize,
          TfheHeader::fhe_int16_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int16_try_encrypt_with_client_key_i16,
          TfheHeader::fhe_int16_try_encrypt_with_public_key_i16,
          TfheHeader::fhe_int16_try_encrypt_trivial_i16,
          null, null, null,
          TfheHeader::fhe_int16_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int16_bitand,       TfheHeader::fhe_int16_bitor,      TfheHeader::fhe_int16_bitxor,
          TfheHeader::fhe_int16_not,
          TfheHeader::fhe_int16_bitand_assign, TfheHeader::fhe_int16_bitor_assign, TfheHeader::fhe_int16_bitxor_assign,
          TfheHeader::fhe_int16_scalar_bitand, TfheHeader::fhe_int16_scalar_bitor, TfheHeader::fhe_int16_scalar_bitxor,
          TfheHeader::fhe_int16_scalar_bitand_assign, TfheHeader::fhe_int16_scalar_bitor_assign, TfheHeader::fhe_int16_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int16_eq,     TfheHeader::fhe_int16_ne,
          TfheHeader::fhe_int16_scalar_eq, TfheHeader::fhe_int16_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int16_lt,          TfheHeader::fhe_int16_le,
          TfheHeader::fhe_int16_gt,          TfheHeader::fhe_int16_ge,
          TfheHeader::fhe_int16_min,         TfheHeader::fhe_int16_max,
          TfheHeader::fhe_int16_scalar_lt,   TfheHeader::fhe_int16_scalar_le,
          TfheHeader::fhe_int16_scalar_gt,   TfheHeader::fhe_int16_scalar_ge,
          TfheHeader::fhe_int16_scalar_min,  TfheHeader::fhe_int16_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int16_add,          TfheHeader::fhe_int16_sub,        TfheHeader::fhe_int16_mul,
          TfheHeader::fhe_int16_div,          TfheHeader::fhe_int16_rem,
          TfheHeader::fhe_int16_overflowing_add, TfheHeader::fhe_int16_overflowing_sub, TfheHeader::fhe_int16_overflowing_mul,
          TfheHeader::fhe_int16_div_rem,
          TfheHeader::fhe_int16_add_assign,   TfheHeader::fhe_int16_sub_assign,  TfheHeader::fhe_int16_mul_assign,
          TfheHeader::fhe_int16_div_assign,   TfheHeader::fhe_int16_rem_assign,
          TfheHeader::fhe_int16_scalar_add,   TfheHeader::fhe_int16_scalar_sub,  TfheHeader::fhe_int16_scalar_mul,
          TfheHeader::fhe_int16_scalar_div,   TfheHeader::fhe_int16_scalar_rem,
          TfheHeader::fhe_int16_scalar_add_assign, TfheHeader::fhe_int16_scalar_sub_assign, TfheHeader::fhe_int16_scalar_mul_assign,
          TfheHeader::fhe_int16_scalar_div_assign, TfheHeader::fhe_int16_scalar_rem_assign,
          TfheHeader::fhe_int16_scalar_div_rem,
          TfheHeader::fhe_int16_neg,
          TfheHeader::fhe_int16_abs,
          TfheHeader::fhe_int16_ilog2,
          TfheHeader::fhe_int16_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int16_shl,           TfheHeader::fhe_int16_shr,
          TfheHeader::fhe_int16_rotate_left,   TfheHeader::fhe_int16_rotate_right,
          TfheHeader::fhe_int16_shl_assign,    TfheHeader::fhe_int16_shr_assign,
          TfheHeader::fhe_int16_rotate_left_assign, TfheHeader::fhe_int16_rotate_right_assign,
          TfheHeader::fhe_int16_scalar_shl,    TfheHeader::fhe_int16_scalar_shr,
          TfheHeader::fhe_int16_scalar_rotate_left, TfheHeader::fhe_int16_scalar_rotate_right,
          TfheHeader::fhe_int16_scalar_shl_assign, TfheHeader::fhe_int16_scalar_shr_assign,
          TfheHeader::fhe_int16_scalar_rotate_left_assign, TfheHeader::fhe_int16_scalar_rotate_right_assign,
          TfheHeader::fhe_int16_leading_ones,  TfheHeader::fhe_int16_leading_zeros,
          TfheHeader::fhe_int16_trailing_ones, TfheHeader::fhe_int16_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int16,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int16,
          TfheHeader::fhe_int16_if_then_else));

  FheInt16() { super(TfheHeader::fhe_int16_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheInt16             newInstance()   { return new FheInt16(); }
  @Override protected CompressedFheInt16   newCompressed() { return new CompressedFheInt16(); }

  public static FheInt16 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt16::new);
  }
  public static FheInt16 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt16::new);
  }
  public static FheInt16 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt16::new);
  }
  public static FheInt16 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt16::new);
  }
  public static FheInt16 ifThenElse(FheBool condition, FheInt16 thenValue, FheInt16 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt16::new);
  }

}
