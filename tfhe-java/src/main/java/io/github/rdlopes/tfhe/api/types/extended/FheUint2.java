package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.Fhe;
import io.github.rdlopes.tfhe.api.types.FheBool;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

@Generated
public final class FheUint2 extends AbstractFheUnsignedInteger<Byte, FheUint2, CompressedFheUint2>
    implements FheUnsignedInteger<Byte, FheUint2, CompressedFheUint2> {

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint2_destroy,
          TfheHeader::fhe_uint2_clone,
          TfheHeader::fhe_uint2_compress,
          TfheHeader::fhe_uint2_safe_serialize,
          TfheHeader::fhe_uint2_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint2_try_encrypt_with_client_key_u8,
          TfheHeader::fhe_uint2_try_encrypt_with_public_key_u8,
          TfheHeader::fhe_uint2_try_encrypt_trivial_u8,
          null, null, null,
          TfheHeader::fhe_uint2_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint2_bitand,       TfheHeader::fhe_uint2_bitor,      TfheHeader::fhe_uint2_bitxor,
          TfheHeader::fhe_uint2_not,
          TfheHeader::fhe_uint2_bitand_assign, TfheHeader::fhe_uint2_bitor_assign, TfheHeader::fhe_uint2_bitxor_assign,
          TfheHeader::fhe_uint2_scalar_bitand, TfheHeader::fhe_uint2_scalar_bitor, TfheHeader::fhe_uint2_scalar_bitxor,
          TfheHeader::fhe_uint2_scalar_bitand_assign, TfheHeader::fhe_uint2_scalar_bitor_assign, TfheHeader::fhe_uint2_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint2_eq,     TfheHeader::fhe_uint2_ne,
          TfheHeader::fhe_uint2_scalar_eq, TfheHeader::fhe_uint2_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint2_lt,          TfheHeader::fhe_uint2_le,
          TfheHeader::fhe_uint2_gt,          TfheHeader::fhe_uint2_ge,
          TfheHeader::fhe_uint2_min,         TfheHeader::fhe_uint2_max,
          TfheHeader::fhe_uint2_scalar_lt,   TfheHeader::fhe_uint2_scalar_le,
          TfheHeader::fhe_uint2_scalar_gt,   TfheHeader::fhe_uint2_scalar_ge,
          TfheHeader::fhe_uint2_scalar_min,  TfheHeader::fhe_uint2_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint2_add,          TfheHeader::fhe_uint2_sub,        TfheHeader::fhe_uint2_mul,
          TfheHeader::fhe_uint2_div,          TfheHeader::fhe_uint2_rem,
          TfheHeader::fhe_uint2_overflowing_add, TfheHeader::fhe_uint2_overflowing_sub, TfheHeader::fhe_uint2_overflowing_mul,
          TfheHeader::fhe_uint2_div_rem,
          TfheHeader::fhe_uint2_add_assign,   TfheHeader::fhe_uint2_sub_assign,  TfheHeader::fhe_uint2_mul_assign,
          TfheHeader::fhe_uint2_div_assign,   TfheHeader::fhe_uint2_rem_assign,
          TfheHeader::fhe_uint2_scalar_add,   TfheHeader::fhe_uint2_scalar_sub,  TfheHeader::fhe_uint2_scalar_mul,
          TfheHeader::fhe_uint2_scalar_div,   TfheHeader::fhe_uint2_scalar_rem,
          TfheHeader::fhe_uint2_scalar_add_assign, TfheHeader::fhe_uint2_scalar_sub_assign, TfheHeader::fhe_uint2_scalar_mul_assign,
          TfheHeader::fhe_uint2_scalar_div_assign, TfheHeader::fhe_uint2_scalar_rem_assign,
          TfheHeader::fhe_uint2_scalar_div_rem,
          TfheHeader::fhe_uint2_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint2_ilog2,
          TfheHeader::fhe_uint2_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint2_shl,           TfheHeader::fhe_uint2_shr,
          TfheHeader::fhe_uint2_rotate_left,   TfheHeader::fhe_uint2_rotate_right,
          TfheHeader::fhe_uint2_shl_assign,    TfheHeader::fhe_uint2_shr_assign,
          TfheHeader::fhe_uint2_rotate_left_assign, TfheHeader::fhe_uint2_rotate_right_assign,
          TfheHeader::fhe_uint2_scalar_shl,    TfheHeader::fhe_uint2_scalar_shr,
          TfheHeader::fhe_uint2_scalar_rotate_left, TfheHeader::fhe_uint2_scalar_rotate_right,
          TfheHeader::fhe_uint2_scalar_shl_assign, TfheHeader::fhe_uint2_scalar_shr_assign,
          TfheHeader::fhe_uint2_scalar_rotate_left_assign, TfheHeader::fhe_uint2_scalar_rotate_right_assign,
          TfheHeader::fhe_uint2_leading_ones,  TfheHeader::fhe_uint2_leading_zeros,
          TfheHeader::fhe_uint2_trailing_ones, TfheHeader::fhe_uint2_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint2,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint2,
          TfheHeader::fhe_uint2_if_then_else));

  static {
    FheRegistry.registerFactory(FheUint2.class, FheUint2::new);
    FheRegistry.registerHandles(FheUint2.class, HANDLES);
  }

  FheUint2() { super(TfheHeader::fhe_uint2_destroy); }

  @Override protected FheTypeHandles<Byte>  handles()      { return HANDLES; }
  @Override protected FheUint2             newInstance()   { return new FheUint2(); }
  @Override protected CompressedFheUint2   newCompressed() { return new CompressedFheUint2(); }

  public static FheUint2 encrypt(Byte clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheUint2.class);
  }
  public static FheUint2 encrypt(Byte clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheUint2.class);
  }
  public static FheUint2 encrypt(Byte clearValue) {
    return Fhe.encrypt(clearValue, FheUint2.class);
  }
  public static FheUint2 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheUint2.class);
  }
  public static FheUint2 ifThenElse(FheBool condition, FheUint2 thenValue, FheUint2 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }

}
