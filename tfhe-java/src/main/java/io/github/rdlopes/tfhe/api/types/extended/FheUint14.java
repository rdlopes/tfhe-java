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
public final class FheUint14 extends AbstractFheUnsignedInteger<Short, FheUint14, CompressedFheUint14>
    implements FheUnsignedInteger<Short, FheUint14, CompressedFheUint14> {

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint14_destroy,
          TfheHeader::fhe_uint14_clone,
          TfheHeader::fhe_uint14_compress,
          TfheHeader::fhe_uint14_safe_serialize,
          TfheHeader::fhe_uint14_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint14_try_encrypt_with_client_key_u16,
          TfheHeader::fhe_uint14_try_encrypt_with_public_key_u16,
          TfheHeader::fhe_uint14_try_encrypt_trivial_u16,
          null, null, null,
          TfheHeader::fhe_uint14_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint14_bitand,       TfheHeader::fhe_uint14_bitor,      TfheHeader::fhe_uint14_bitxor,
          TfheHeader::fhe_uint14_not,
          TfheHeader::fhe_uint14_bitand_assign, TfheHeader::fhe_uint14_bitor_assign, TfheHeader::fhe_uint14_bitxor_assign,
          TfheHeader::fhe_uint14_scalar_bitand, TfheHeader::fhe_uint14_scalar_bitor, TfheHeader::fhe_uint14_scalar_bitxor,
          TfheHeader::fhe_uint14_scalar_bitand_assign, TfheHeader::fhe_uint14_scalar_bitor_assign, TfheHeader::fhe_uint14_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint14_eq,     TfheHeader::fhe_uint14_ne,
          TfheHeader::fhe_uint14_scalar_eq, TfheHeader::fhe_uint14_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint14_lt,          TfheHeader::fhe_uint14_le,
          TfheHeader::fhe_uint14_gt,          TfheHeader::fhe_uint14_ge,
          TfheHeader::fhe_uint14_min,         TfheHeader::fhe_uint14_max,
          TfheHeader::fhe_uint14_scalar_lt,   TfheHeader::fhe_uint14_scalar_le,
          TfheHeader::fhe_uint14_scalar_gt,   TfheHeader::fhe_uint14_scalar_ge,
          TfheHeader::fhe_uint14_scalar_min,  TfheHeader::fhe_uint14_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint14_add,          TfheHeader::fhe_uint14_sub,        TfheHeader::fhe_uint14_mul,
          TfheHeader::fhe_uint14_div,          TfheHeader::fhe_uint14_rem,
          TfheHeader::fhe_uint14_overflowing_add, TfheHeader::fhe_uint14_overflowing_sub, TfheHeader::fhe_uint14_overflowing_mul,
          TfheHeader::fhe_uint14_div_rem,
          TfheHeader::fhe_uint14_add_assign,   TfheHeader::fhe_uint14_sub_assign,  TfheHeader::fhe_uint14_mul_assign,
          TfheHeader::fhe_uint14_div_assign,   TfheHeader::fhe_uint14_rem_assign,
          TfheHeader::fhe_uint14_scalar_add,   TfheHeader::fhe_uint14_scalar_sub,  TfheHeader::fhe_uint14_scalar_mul,
          TfheHeader::fhe_uint14_scalar_div,   TfheHeader::fhe_uint14_scalar_rem,
          TfheHeader::fhe_uint14_scalar_add_assign, TfheHeader::fhe_uint14_scalar_sub_assign, TfheHeader::fhe_uint14_scalar_mul_assign,
          TfheHeader::fhe_uint14_scalar_div_assign, TfheHeader::fhe_uint14_scalar_rem_assign,
          TfheHeader::fhe_uint14_scalar_div_rem,
          TfheHeader::fhe_uint14_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint14_ilog2,
          TfheHeader::fhe_uint14_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint14_shl,           TfheHeader::fhe_uint14_shr,
          TfheHeader::fhe_uint14_rotate_left,   TfheHeader::fhe_uint14_rotate_right,
          TfheHeader::fhe_uint14_shl_assign,    TfheHeader::fhe_uint14_shr_assign,
          TfheHeader::fhe_uint14_rotate_left_assign, TfheHeader::fhe_uint14_rotate_right_assign,
          TfheHeader::fhe_uint14_scalar_shl,    TfheHeader::fhe_uint14_scalar_shr,
          TfheHeader::fhe_uint14_scalar_rotate_left, TfheHeader::fhe_uint14_scalar_rotate_right,
          TfheHeader::fhe_uint14_scalar_shl_assign, TfheHeader::fhe_uint14_scalar_shr_assign,
          TfheHeader::fhe_uint14_scalar_rotate_left_assign, TfheHeader::fhe_uint14_scalar_rotate_right_assign,
          TfheHeader::fhe_uint14_leading_ones,  TfheHeader::fhe_uint14_leading_zeros,
          TfheHeader::fhe_uint14_trailing_ones, TfheHeader::fhe_uint14_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint14,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint14,
          TfheHeader::fhe_uint14_if_then_else));

  static {
    FheRegistry.registerFactory(FheUint14.class, FheUint14::new);
    FheRegistry.registerHandles(FheUint14.class, HANDLES);
  }

  FheUint14() { super(TfheHeader::fhe_uint14_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheUint14            newInstance()   { return new FheUint14(); }
  @Override protected CompressedFheUint14  newCompressed() { return new CompressedFheUint14(); }

  public static FheUint14 encrypt(Short clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheUint14.class);
  }
  public static FheUint14 encrypt(Short clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheUint14.class);
  }
  public static FheUint14 encrypt(Short clearValue) {
    return Fhe.encrypt(clearValue, FheUint14.class);
  }
  public static FheUint14 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheUint14.class);
  }
  public static FheUint14 ifThenElse(FheBool condition, FheUint14 thenValue, FheUint14 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }

}
