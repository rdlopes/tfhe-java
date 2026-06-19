package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

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
public final class FheUint10 extends AbstractFheUnsignedInteger<Short, FheUint10, CompressedFheUint10>
    implements FheUnsignedInteger<Short, FheUint10, CompressedFheUint10> {

  static {
    FheRegistry.registerFactory(FheUint10.class, FheUint10::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint10_destroy,
          TfheHeader::fhe_uint10_clone,
          TfheHeader::fhe_uint10_compress,
          TfheHeader::fhe_uint10_safe_serialize,
          TfheHeader::fhe_uint10_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint10_try_encrypt_with_client_key_u16,
          TfheHeader::fhe_uint10_try_encrypt_with_public_key_u16,
          TfheHeader::fhe_uint10_try_encrypt_trivial_u16,
          null, null, null,
          TfheHeader::fhe_uint10_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint10_bitand,       TfheHeader::fhe_uint10_bitor,      TfheHeader::fhe_uint10_bitxor,
          TfheHeader::fhe_uint10_not,
          TfheHeader::fhe_uint10_bitand_assign, TfheHeader::fhe_uint10_bitor_assign, TfheHeader::fhe_uint10_bitxor_assign,
          TfheHeader::fhe_uint10_scalar_bitand, TfheHeader::fhe_uint10_scalar_bitor, TfheHeader::fhe_uint10_scalar_bitxor,
          TfheHeader::fhe_uint10_scalar_bitand_assign, TfheHeader::fhe_uint10_scalar_bitor_assign, TfheHeader::fhe_uint10_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint10_eq,     TfheHeader::fhe_uint10_ne,
          TfheHeader::fhe_uint10_scalar_eq, TfheHeader::fhe_uint10_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint10_lt,          TfheHeader::fhe_uint10_le,
          TfheHeader::fhe_uint10_gt,          TfheHeader::fhe_uint10_ge,
          TfheHeader::fhe_uint10_min,         TfheHeader::fhe_uint10_max,
          TfheHeader::fhe_uint10_scalar_lt,   TfheHeader::fhe_uint10_scalar_le,
          TfheHeader::fhe_uint10_scalar_gt,   TfheHeader::fhe_uint10_scalar_ge,
          TfheHeader::fhe_uint10_scalar_min,  TfheHeader::fhe_uint10_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint10_add,          TfheHeader::fhe_uint10_sub,        TfheHeader::fhe_uint10_mul,
          TfheHeader::fhe_uint10_div,          TfheHeader::fhe_uint10_rem,
          TfheHeader::fhe_uint10_overflowing_add, TfheHeader::fhe_uint10_overflowing_sub, TfheHeader::fhe_uint10_overflowing_mul,
          TfheHeader::fhe_uint10_div_rem,
          TfheHeader::fhe_uint10_add_assign,   TfheHeader::fhe_uint10_sub_assign,  TfheHeader::fhe_uint10_mul_assign,
          TfheHeader::fhe_uint10_div_assign,   TfheHeader::fhe_uint10_rem_assign,
          TfheHeader::fhe_uint10_scalar_add,   TfheHeader::fhe_uint10_scalar_sub,  TfheHeader::fhe_uint10_scalar_mul,
          TfheHeader::fhe_uint10_scalar_div,   TfheHeader::fhe_uint10_scalar_rem,
          TfheHeader::fhe_uint10_scalar_add_assign, TfheHeader::fhe_uint10_scalar_sub_assign, TfheHeader::fhe_uint10_scalar_mul_assign,
          TfheHeader::fhe_uint10_scalar_div_assign, TfheHeader::fhe_uint10_scalar_rem_assign,
          TfheHeader::fhe_uint10_scalar_div_rem,
          TfheHeader::fhe_uint10_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint10_ilog2,
          TfheHeader::fhe_uint10_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint10_shl,           TfheHeader::fhe_uint10_shr,
          TfheHeader::fhe_uint10_rotate_left,   TfheHeader::fhe_uint10_rotate_right,
          TfheHeader::fhe_uint10_shl_assign,    TfheHeader::fhe_uint10_shr_assign,
          TfheHeader::fhe_uint10_rotate_left_assign, TfheHeader::fhe_uint10_rotate_right_assign,
          TfheHeader::fhe_uint10_scalar_shl,    TfheHeader::fhe_uint10_scalar_shr,
          TfheHeader::fhe_uint10_scalar_rotate_left, TfheHeader::fhe_uint10_scalar_rotate_right,
          TfheHeader::fhe_uint10_scalar_shl_assign, TfheHeader::fhe_uint10_scalar_shr_assign,
          TfheHeader::fhe_uint10_scalar_rotate_left_assign, TfheHeader::fhe_uint10_scalar_rotate_right_assign,
          TfheHeader::fhe_uint10_leading_ones,  TfheHeader::fhe_uint10_leading_zeros,
          TfheHeader::fhe_uint10_trailing_ones, TfheHeader::fhe_uint10_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint10,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint10,
          TfheHeader::fhe_uint10_if_then_else));

  FheUint10() { super(TfheHeader::fhe_uint10_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheUint10            newInstance()   { return new FheUint10(); }
  @Override protected CompressedFheUint10  newCompressed() { return new CompressedFheUint10(); }

  public static FheUint10 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint10::new);
  }
  public static FheUint10 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint10::new);
  }
  public static FheUint10 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint10::new);
  }
  public static FheUint10 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint10::new);
  }
  public static FheUint10 ifThenElse(FheBool condition, FheUint10 thenValue, FheUint10 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint10::new);
  }

}
