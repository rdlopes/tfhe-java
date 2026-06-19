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
public final class FheUint32 extends AbstractFheUnsignedInteger<Integer, FheUint32, CompressedFheUint32>
    implements FheUnsignedInteger<Integer, FheUint32, CompressedFheUint32> {

  static {
    FheRegistry.registerFactory(FheUint32.class, FheUint32::new);
  }

  static final FheTypeHandles<Integer> HANDLES = new FheTypeHandles<>(
      FheValueKind.INT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint32_destroy,
          TfheHeader::fhe_uint32_clone,
          TfheHeader::fhe_uint32_compress,
          TfheHeader::fhe_uint32_safe_serialize,
          TfheHeader::fhe_uint32_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint32_try_encrypt_with_client_key_u32,
          TfheHeader::fhe_uint32_try_encrypt_with_public_key_u32,
          TfheHeader::fhe_uint32_try_encrypt_trivial_u32,
          null, null, null,
          TfheHeader::fhe_uint32_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint32_bitand,       TfheHeader::fhe_uint32_bitor,      TfheHeader::fhe_uint32_bitxor,
          TfheHeader::fhe_uint32_not,
          TfheHeader::fhe_uint32_bitand_assign, TfheHeader::fhe_uint32_bitor_assign, TfheHeader::fhe_uint32_bitxor_assign,
          TfheHeader::fhe_uint32_scalar_bitand, TfheHeader::fhe_uint32_scalar_bitor, TfheHeader::fhe_uint32_scalar_bitxor,
          TfheHeader::fhe_uint32_scalar_bitand_assign, TfheHeader::fhe_uint32_scalar_bitor_assign, TfheHeader::fhe_uint32_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint32_eq,     TfheHeader::fhe_uint32_ne,
          TfheHeader::fhe_uint32_scalar_eq, TfheHeader::fhe_uint32_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint32_lt,          TfheHeader::fhe_uint32_le,
          TfheHeader::fhe_uint32_gt,          TfheHeader::fhe_uint32_ge,
          TfheHeader::fhe_uint32_min,         TfheHeader::fhe_uint32_max,
          TfheHeader::fhe_uint32_scalar_lt,   TfheHeader::fhe_uint32_scalar_le,
          TfheHeader::fhe_uint32_scalar_gt,   TfheHeader::fhe_uint32_scalar_ge,
          TfheHeader::fhe_uint32_scalar_min,  TfheHeader::fhe_uint32_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint32_add,          TfheHeader::fhe_uint32_sub,        TfheHeader::fhe_uint32_mul,
          TfheHeader::fhe_uint32_div,          TfheHeader::fhe_uint32_rem,
          TfheHeader::fhe_uint32_overflowing_add, TfheHeader::fhe_uint32_overflowing_sub, TfheHeader::fhe_uint32_overflowing_mul,
          TfheHeader::fhe_uint32_div_rem,
          TfheHeader::fhe_uint32_add_assign,   TfheHeader::fhe_uint32_sub_assign,  TfheHeader::fhe_uint32_mul_assign,
          TfheHeader::fhe_uint32_div_assign,   TfheHeader::fhe_uint32_rem_assign,
          TfheHeader::fhe_uint32_scalar_add,   TfheHeader::fhe_uint32_scalar_sub,  TfheHeader::fhe_uint32_scalar_mul,
          TfheHeader::fhe_uint32_scalar_div,   TfheHeader::fhe_uint32_scalar_rem,
          TfheHeader::fhe_uint32_scalar_add_assign, TfheHeader::fhe_uint32_scalar_sub_assign, TfheHeader::fhe_uint32_scalar_mul_assign,
          TfheHeader::fhe_uint32_scalar_div_assign, TfheHeader::fhe_uint32_scalar_rem_assign,
          TfheHeader::fhe_uint32_scalar_div_rem,
          TfheHeader::fhe_uint32_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint32_ilog2,
          TfheHeader::fhe_uint32_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint32_shl,           TfheHeader::fhe_uint32_shr,
          TfheHeader::fhe_uint32_rotate_left,   TfheHeader::fhe_uint32_rotate_right,
          TfheHeader::fhe_uint32_shl_assign,    TfheHeader::fhe_uint32_shr_assign,
          TfheHeader::fhe_uint32_rotate_left_assign, TfheHeader::fhe_uint32_rotate_right_assign,
          TfheHeader::fhe_uint32_scalar_shl,    TfheHeader::fhe_uint32_scalar_shr,
          TfheHeader::fhe_uint32_scalar_rotate_left, TfheHeader::fhe_uint32_scalar_rotate_right,
          TfheHeader::fhe_uint32_scalar_shl_assign, TfheHeader::fhe_uint32_scalar_shr_assign,
          TfheHeader::fhe_uint32_scalar_rotate_left_assign, TfheHeader::fhe_uint32_scalar_rotate_right_assign,
          TfheHeader::fhe_uint32_leading_ones,  TfheHeader::fhe_uint32_leading_zeros,
          TfheHeader::fhe_uint32_trailing_ones, TfheHeader::fhe_uint32_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint32,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint32,
          TfheHeader::fhe_uint32_if_then_else));

  FheUint32() { super(TfheHeader::fhe_uint32_destroy); }

  @Override protected FheTypeHandles<Integer> handles()      { return HANDLES; }
  @Override protected FheUint32              newInstance()   { return new FheUint32(); }
  @Override protected CompressedFheUint32    newCompressed() { return new CompressedFheUint32(); }

  public static FheUint32 encrypt(Integer clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint32::new);
  }
  public static FheUint32 encrypt(Integer clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint32::new);
  }
  public static FheUint32 encrypt(Integer clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint32::new);
  }
  public static FheUint32 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint32::new);
  }
  public static FheUint32 ifThenElse(FheBool condition, FheUint32 thenValue, FheUint32 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint32::new);
  }

}
