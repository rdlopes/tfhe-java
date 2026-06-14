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

public final class FheUint6 extends AbstractFheUnsignedInteger<Byte, FheUint6, CompressedFheUint6>
    implements FheUnsignedInteger<Byte, FheUint6, CompressedFheUint6> {

  static {
    FheRegistry.registerFactory(FheUint6.class, FheUint6::new);
  }

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint6_destroy,
          TfheHeader::fhe_uint6_clone,
          TfheHeader::fhe_uint6_compress,
          TfheHeader::fhe_uint6_safe_serialize,
          TfheHeader::fhe_uint6_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint6_try_encrypt_with_client_key_u8,
          TfheHeader::fhe_uint6_try_encrypt_with_public_key_u8,
          TfheHeader::fhe_uint6_try_encrypt_trivial_u8,
          null, null, null,
          TfheHeader::fhe_uint6_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint6_bitand,       TfheHeader::fhe_uint6_bitor,      TfheHeader::fhe_uint6_bitxor,
          TfheHeader::fhe_uint6_not,
          TfheHeader::fhe_uint6_bitand_assign, TfheHeader::fhe_uint6_bitor_assign, TfheHeader::fhe_uint6_bitxor_assign,
          TfheHeader::fhe_uint6_scalar_bitand, TfheHeader::fhe_uint6_scalar_bitor, TfheHeader::fhe_uint6_scalar_bitxor,
          TfheHeader::fhe_uint6_scalar_bitand_assign, TfheHeader::fhe_uint6_scalar_bitor_assign, TfheHeader::fhe_uint6_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint6_eq,     TfheHeader::fhe_uint6_ne,
          TfheHeader::fhe_uint6_scalar_eq, TfheHeader::fhe_uint6_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint6_lt,          TfheHeader::fhe_uint6_le,
          TfheHeader::fhe_uint6_gt,          TfheHeader::fhe_uint6_ge,
          TfheHeader::fhe_uint6_min,         TfheHeader::fhe_uint6_max,
          TfheHeader::fhe_uint6_scalar_lt,   TfheHeader::fhe_uint6_scalar_le,
          TfheHeader::fhe_uint6_scalar_gt,   TfheHeader::fhe_uint6_scalar_ge,
          TfheHeader::fhe_uint6_scalar_min,  TfheHeader::fhe_uint6_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint6_add,          TfheHeader::fhe_uint6_sub,        TfheHeader::fhe_uint6_mul,
          TfheHeader::fhe_uint6_div,          TfheHeader::fhe_uint6_rem,
          TfheHeader::fhe_uint6_overflowing_add, TfheHeader::fhe_uint6_overflowing_sub, TfheHeader::fhe_uint6_overflowing_mul,
          TfheHeader::fhe_uint6_div_rem,
          TfheHeader::fhe_uint6_add_assign,   TfheHeader::fhe_uint6_sub_assign,  TfheHeader::fhe_uint6_mul_assign,
          TfheHeader::fhe_uint6_div_assign,   TfheHeader::fhe_uint6_rem_assign,
          TfheHeader::fhe_uint6_scalar_add,   TfheHeader::fhe_uint6_scalar_sub,  TfheHeader::fhe_uint6_scalar_mul,
          TfheHeader::fhe_uint6_scalar_div,   TfheHeader::fhe_uint6_scalar_rem,
          TfheHeader::fhe_uint6_scalar_add_assign, TfheHeader::fhe_uint6_scalar_sub_assign, TfheHeader::fhe_uint6_scalar_mul_assign,
          TfheHeader::fhe_uint6_scalar_div_assign, TfheHeader::fhe_uint6_scalar_rem_assign,
          TfheHeader::fhe_uint6_scalar_div_rem,
          TfheHeader::fhe_uint6_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint6_ilog2,
          TfheHeader::fhe_uint6_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint6_shl,           TfheHeader::fhe_uint6_shr,
          TfheHeader::fhe_uint6_rotate_left,   TfheHeader::fhe_uint6_rotate_right,
          TfheHeader::fhe_uint6_shl_assign,    TfheHeader::fhe_uint6_shr_assign,
          TfheHeader::fhe_uint6_rotate_left_assign, TfheHeader::fhe_uint6_rotate_right_assign,
          TfheHeader::fhe_uint6_scalar_shl,    TfheHeader::fhe_uint6_scalar_shr,
          TfheHeader::fhe_uint6_scalar_rotate_left, TfheHeader::fhe_uint6_scalar_rotate_right,
          TfheHeader::fhe_uint6_scalar_shl_assign, TfheHeader::fhe_uint6_scalar_shr_assign,
          TfheHeader::fhe_uint6_scalar_rotate_left_assign, TfheHeader::fhe_uint6_scalar_rotate_right_assign,
          TfheHeader::fhe_uint6_leading_ones,  TfheHeader::fhe_uint6_leading_zeros,
          TfheHeader::fhe_uint6_trailing_ones, TfheHeader::fhe_uint6_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint6,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint6,
          TfheHeader::fhe_uint6_if_then_else));

  FheUint6() { super(TfheHeader::fhe_uint6_destroy); }

  @Override protected FheTypeHandles<Byte>  handles()      { return HANDLES; }
  @Override protected FheUint6             newInstance()   { return new FheUint6(); }
  @Override protected CompressedFheUint6   newCompressed() { return new CompressedFheUint6(); }

  public static FheUint6 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint6::new);
  }
  public static FheUint6 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint6::new);
  }
  public static FheUint6 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint6::new);
  }
  public static FheUint6 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint6::new);
  }
  public static FheUint6 ifThenElse(FheBool condition, FheUint6 thenValue, FheUint6 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint6::new);
  }

}
