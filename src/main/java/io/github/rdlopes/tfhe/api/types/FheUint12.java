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

public final class FheUint12 extends AbstractFheUnsignedInteger<Short, FheUint12, CompressedFheUint12>
    implements FheUnsignedInteger<Short, FheUint12, CompressedFheUint12> {

  static {
    FheRegistry.registerFactory(FheUint12.class, FheUint12::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint12_destroy,
          TfheHeader::fhe_uint12_clone,
          TfheHeader::fhe_uint12_compress,
          TfheHeader::fhe_uint12_safe_serialize,
          TfheHeader::fhe_uint12_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint12_try_encrypt_with_client_key_u16,
          TfheHeader::fhe_uint12_try_encrypt_with_public_key_u16,
          TfheHeader::fhe_uint12_try_encrypt_trivial_u16,
          null, null, null,
          TfheHeader::fhe_uint12_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint12_bitand,       TfheHeader::fhe_uint12_bitor,      TfheHeader::fhe_uint12_bitxor,
          TfheHeader::fhe_uint12_not,
          TfheHeader::fhe_uint12_bitand_assign, TfheHeader::fhe_uint12_bitor_assign, TfheHeader::fhe_uint12_bitxor_assign,
          TfheHeader::fhe_uint12_scalar_bitand, TfheHeader::fhe_uint12_scalar_bitor, TfheHeader::fhe_uint12_scalar_bitxor,
          TfheHeader::fhe_uint12_scalar_bitand_assign, TfheHeader::fhe_uint12_scalar_bitor_assign, TfheHeader::fhe_uint12_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint12_eq,     TfheHeader::fhe_uint12_ne,
          TfheHeader::fhe_uint12_scalar_eq, TfheHeader::fhe_uint12_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint12_lt,          TfheHeader::fhe_uint12_le,
          TfheHeader::fhe_uint12_gt,          TfheHeader::fhe_uint12_ge,
          TfheHeader::fhe_uint12_min,         TfheHeader::fhe_uint12_max,
          TfheHeader::fhe_uint12_scalar_lt,   TfheHeader::fhe_uint12_scalar_le,
          TfheHeader::fhe_uint12_scalar_gt,   TfheHeader::fhe_uint12_scalar_ge,
          TfheHeader::fhe_uint12_scalar_min,  TfheHeader::fhe_uint12_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint12_add,          TfheHeader::fhe_uint12_sub,        TfheHeader::fhe_uint12_mul,
          TfheHeader::fhe_uint12_div,          TfheHeader::fhe_uint12_rem,
          TfheHeader::fhe_uint12_overflowing_add, TfheHeader::fhe_uint12_overflowing_sub, TfheHeader::fhe_uint12_overflowing_mul,
          TfheHeader::fhe_uint12_div_rem,
          TfheHeader::fhe_uint12_add_assign,   TfheHeader::fhe_uint12_sub_assign,  TfheHeader::fhe_uint12_mul_assign,
          TfheHeader::fhe_uint12_div_assign,   TfheHeader::fhe_uint12_rem_assign,
          TfheHeader::fhe_uint12_scalar_add,   TfheHeader::fhe_uint12_scalar_sub,  TfheHeader::fhe_uint12_scalar_mul,
          TfheHeader::fhe_uint12_scalar_div,   TfheHeader::fhe_uint12_scalar_rem,
          TfheHeader::fhe_uint12_scalar_add_assign, TfheHeader::fhe_uint12_scalar_sub_assign, TfheHeader::fhe_uint12_scalar_mul_assign,
          TfheHeader::fhe_uint12_scalar_div_assign, TfheHeader::fhe_uint12_scalar_rem_assign,
          TfheHeader::fhe_uint12_scalar_div_rem,
          TfheHeader::fhe_uint12_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint12_ilog2,
          TfheHeader::fhe_uint12_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint12_shl,           TfheHeader::fhe_uint12_shr,
          TfheHeader::fhe_uint12_rotate_left,   TfheHeader::fhe_uint12_rotate_right,
          TfheHeader::fhe_uint12_shl_assign,    TfheHeader::fhe_uint12_shr_assign,
          TfheHeader::fhe_uint12_rotate_left_assign, TfheHeader::fhe_uint12_rotate_right_assign,
          TfheHeader::fhe_uint12_scalar_shl,    TfheHeader::fhe_uint12_scalar_shr,
          TfheHeader::fhe_uint12_scalar_rotate_left, TfheHeader::fhe_uint12_scalar_rotate_right,
          TfheHeader::fhe_uint12_scalar_shl_assign, TfheHeader::fhe_uint12_scalar_shr_assign,
          TfheHeader::fhe_uint12_scalar_rotate_left_assign, TfheHeader::fhe_uint12_scalar_rotate_right_assign,
          TfheHeader::fhe_uint12_leading_ones,  TfheHeader::fhe_uint12_leading_zeros,
          TfheHeader::fhe_uint12_trailing_ones, TfheHeader::fhe_uint12_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint12,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint12,
          TfheHeader::fhe_uint12_if_then_else));

  FheUint12() { super(TfheHeader::fhe_uint12_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheUint12            newInstance()   { return new FheUint12(); }
  @Override protected CompressedFheUint12  newCompressed() { return new CompressedFheUint12(); }

  public static FheUint12 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint12::new);
  }
  public static FheUint12 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint12::new);
  }
  public static FheUint12 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint12::new);
  }
  public static FheUint12 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint12::new);
  }
  public static FheUint12 ifThenElse(FheBool condition, FheUint12 thenValue, FheUint12 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint12::new);
  }

}
