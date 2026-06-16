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
public final class FheUint4 extends AbstractFheUnsignedInteger<Byte, FheUint4, CompressedFheUint4>
    implements FheUnsignedInteger<Byte, FheUint4, CompressedFheUint4> {

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint4_destroy,
          TfheHeader::fhe_uint4_clone,
          TfheHeader::fhe_uint4_compress,
          TfheHeader::fhe_uint4_safe_serialize,
          TfheHeader::fhe_uint4_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint4_try_encrypt_with_client_key_u8,
          TfheHeader::fhe_uint4_try_encrypt_with_public_key_u8,
          TfheHeader::fhe_uint4_try_encrypt_trivial_u8,
          null, null, null,
          TfheHeader::fhe_uint4_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint4_bitand,       TfheHeader::fhe_uint4_bitor,      TfheHeader::fhe_uint4_bitxor,
          TfheHeader::fhe_uint4_not,
          TfheHeader::fhe_uint4_bitand_assign, TfheHeader::fhe_uint4_bitor_assign, TfheHeader::fhe_uint4_bitxor_assign,
          TfheHeader::fhe_uint4_scalar_bitand, TfheHeader::fhe_uint4_scalar_bitor, TfheHeader::fhe_uint4_scalar_bitxor,
          TfheHeader::fhe_uint4_scalar_bitand_assign, TfheHeader::fhe_uint4_scalar_bitor_assign, TfheHeader::fhe_uint4_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint4_eq,     TfheHeader::fhe_uint4_ne,
          TfheHeader::fhe_uint4_scalar_eq, TfheHeader::fhe_uint4_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint4_lt,          TfheHeader::fhe_uint4_le,
          TfheHeader::fhe_uint4_gt,          TfheHeader::fhe_uint4_ge,
          TfheHeader::fhe_uint4_min,         TfheHeader::fhe_uint4_max,
          TfheHeader::fhe_uint4_scalar_lt,   TfheHeader::fhe_uint4_scalar_le,
          TfheHeader::fhe_uint4_scalar_gt,   TfheHeader::fhe_uint4_scalar_ge,
          TfheHeader::fhe_uint4_scalar_min,  TfheHeader::fhe_uint4_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint4_add,          TfheHeader::fhe_uint4_sub,        TfheHeader::fhe_uint4_mul,
          TfheHeader::fhe_uint4_div,          TfheHeader::fhe_uint4_rem,
          TfheHeader::fhe_uint4_overflowing_add, TfheHeader::fhe_uint4_overflowing_sub, TfheHeader::fhe_uint4_overflowing_mul,
          TfheHeader::fhe_uint4_div_rem,
          TfheHeader::fhe_uint4_add_assign,   TfheHeader::fhe_uint4_sub_assign,  TfheHeader::fhe_uint4_mul_assign,
          TfheHeader::fhe_uint4_div_assign,   TfheHeader::fhe_uint4_rem_assign,
          TfheHeader::fhe_uint4_scalar_add,   TfheHeader::fhe_uint4_scalar_sub,  TfheHeader::fhe_uint4_scalar_mul,
          TfheHeader::fhe_uint4_scalar_div,   TfheHeader::fhe_uint4_scalar_rem,
          TfheHeader::fhe_uint4_scalar_add_assign, TfheHeader::fhe_uint4_scalar_sub_assign, TfheHeader::fhe_uint4_scalar_mul_assign,
          TfheHeader::fhe_uint4_scalar_div_assign, TfheHeader::fhe_uint4_scalar_rem_assign,
          TfheHeader::fhe_uint4_scalar_div_rem,
          TfheHeader::fhe_uint4_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint4_ilog2,
          TfheHeader::fhe_uint4_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint4_shl,           TfheHeader::fhe_uint4_shr,
          TfheHeader::fhe_uint4_rotate_left,   TfheHeader::fhe_uint4_rotate_right,
          TfheHeader::fhe_uint4_shl_assign,    TfheHeader::fhe_uint4_shr_assign,
          TfheHeader::fhe_uint4_rotate_left_assign, TfheHeader::fhe_uint4_rotate_right_assign,
          TfheHeader::fhe_uint4_scalar_shl,    TfheHeader::fhe_uint4_scalar_shr,
          TfheHeader::fhe_uint4_scalar_rotate_left, TfheHeader::fhe_uint4_scalar_rotate_right,
          TfheHeader::fhe_uint4_scalar_shl_assign, TfheHeader::fhe_uint4_scalar_shr_assign,
          TfheHeader::fhe_uint4_scalar_rotate_left_assign, TfheHeader::fhe_uint4_scalar_rotate_right_assign,
          TfheHeader::fhe_uint4_leading_ones,  TfheHeader::fhe_uint4_leading_zeros,
          TfheHeader::fhe_uint4_trailing_ones, TfheHeader::fhe_uint4_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint4,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint4,
          TfheHeader::fhe_uint4_if_then_else));

  static {
    FheRegistry.registerFactory(FheUint4.class, FheUint4::new);
    FheRegistry.registerHandles(FheUint4.class, HANDLES);
  }

  FheUint4() { super(TfheHeader::fhe_uint4_destroy); }

  @Override protected FheTypeHandles<Byte>  handles()      { return HANDLES; }
  @Override protected FheUint4             newInstance()   { return new FheUint4(); }
  @Override protected CompressedFheUint4   newCompressed() { return new CompressedFheUint4(); }

  public static FheUint4 encrypt(Byte clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheUint4.class);
  }
  public static FheUint4 encrypt(Byte clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheUint4.class);
  }
  public static FheUint4 encrypt(Byte clearValue) {
    return Fhe.encrypt(clearValue, FheUint4.class);
  }
  public static FheUint4 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheUint4.class);
  }
  public static FheUint4 ifThenElse(FheBool condition, FheUint4 thenValue, FheUint4 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }

}
