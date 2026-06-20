package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

@Generated
public final class FheInt32 extends AbstractFheType<Integer, FheInt32, CompressedFheInt32>
  implements FheSignedInteger<Integer, FheInt32, CompressedFheInt32> {

  static {
    FheRegistry.registerFactory(FheInt32.class, FheInt32::new);
  }

  static final FheTypeHandles<Integer> HANDLES = new FheTypeHandles<>(
      FheValueKind.INT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int32_destroy,
          TfheHeader::fhe_int32_clone,
          TfheHeader::fhe_int32_compress,
          TfheHeader::fhe_int32_safe_serialize,
          TfheHeader::fhe_int32_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int32_try_encrypt_with_client_key_i32,
          TfheHeader::fhe_int32_try_encrypt_with_public_key_i32,
          TfheHeader::fhe_int32_try_encrypt_trivial_i32,
          null, null, null,
          TfheHeader::fhe_int32_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int32_bitand,       TfheHeader::fhe_int32_bitor,      TfheHeader::fhe_int32_bitxor,
          TfheHeader::fhe_int32_not,
          TfheHeader::fhe_int32_bitand_assign, TfheHeader::fhe_int32_bitor_assign, TfheHeader::fhe_int32_bitxor_assign,
          TfheHeader::fhe_int32_scalar_bitand, TfheHeader::fhe_int32_scalar_bitor, TfheHeader::fhe_int32_scalar_bitxor,
          TfheHeader::fhe_int32_scalar_bitand_assign, TfheHeader::fhe_int32_scalar_bitor_assign, TfheHeader::fhe_int32_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int32_eq,     TfheHeader::fhe_int32_ne,
          TfheHeader::fhe_int32_scalar_eq, TfheHeader::fhe_int32_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int32_lt,          TfheHeader::fhe_int32_le,
          TfheHeader::fhe_int32_gt,          TfheHeader::fhe_int32_ge,
          TfheHeader::fhe_int32_min,         TfheHeader::fhe_int32_max,
          TfheHeader::fhe_int32_scalar_lt,   TfheHeader::fhe_int32_scalar_le,
          TfheHeader::fhe_int32_scalar_gt,   TfheHeader::fhe_int32_scalar_ge,
          TfheHeader::fhe_int32_scalar_min,  TfheHeader::fhe_int32_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int32_add,          TfheHeader::fhe_int32_sub,        TfheHeader::fhe_int32_mul,
          TfheHeader::fhe_int32_div,          TfheHeader::fhe_int32_rem,
          TfheHeader::fhe_int32_overflowing_add, TfheHeader::fhe_int32_overflowing_sub, TfheHeader::fhe_int32_overflowing_mul,
          TfheHeader::fhe_int32_div_rem,
          TfheHeader::fhe_int32_add_assign,   TfheHeader::fhe_int32_sub_assign,  TfheHeader::fhe_int32_mul_assign,
          TfheHeader::fhe_int32_div_assign,   TfheHeader::fhe_int32_rem_assign,
          TfheHeader::fhe_int32_scalar_add,   TfheHeader::fhe_int32_scalar_sub,  TfheHeader::fhe_int32_scalar_mul,
          TfheHeader::fhe_int32_scalar_div,   TfheHeader::fhe_int32_scalar_rem,
          TfheHeader::fhe_int32_scalar_add_assign, TfheHeader::fhe_int32_scalar_sub_assign, TfheHeader::fhe_int32_scalar_mul_assign,
          TfheHeader::fhe_int32_scalar_div_assign, TfheHeader::fhe_int32_scalar_rem_assign,
          TfheHeader::fhe_int32_scalar_div_rem,
          TfheHeader::fhe_int32_neg,
          TfheHeader::fhe_int32_abs,
          TfheHeader::fhe_int32_ilog2,
          TfheHeader::fhe_int32_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int32_shl,           TfheHeader::fhe_int32_shr,
          TfheHeader::fhe_int32_rotate_left,   TfheHeader::fhe_int32_rotate_right,
          TfheHeader::fhe_int32_shl_assign,    TfheHeader::fhe_int32_shr_assign,
          TfheHeader::fhe_int32_rotate_left_assign, TfheHeader::fhe_int32_rotate_right_assign,
          TfheHeader::fhe_int32_scalar_shl,    TfheHeader::fhe_int32_scalar_shr,
          TfheHeader::fhe_int32_scalar_rotate_left, TfheHeader::fhe_int32_scalar_rotate_right,
          TfheHeader::fhe_int32_scalar_shl_assign, TfheHeader::fhe_int32_scalar_shr_assign,
          TfheHeader::fhe_int32_scalar_rotate_left_assign, TfheHeader::fhe_int32_scalar_rotate_right_assign,
          TfheHeader::fhe_int32_leading_ones,  TfheHeader::fhe_int32_leading_zeros,
          TfheHeader::fhe_int32_trailing_ones, TfheHeader::fhe_int32_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int32,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int32,
          TfheHeader::fhe_int32_if_then_else));

  FheInt32() { super(TfheHeader::fhe_int32_destroy); }

  @Override protected FheTypeHandles<Integer> handles()      { return HANDLES; }
  @Override protected FheInt32               newInstance()   { return new FheInt32(); }
  @Override protected CompressedFheInt32     newCompressed() { return new CompressedFheInt32(); }

  public static FheInt32 encrypt(Integer clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt32::new);
  }
  public static FheInt32 encrypt(Integer clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt32::new);
  }
  public static FheInt32 encrypt(Integer clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt32::new);
  }
  public static FheInt32 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt32::new);
  }
  public static FheInt32 ifThenElse(FheBool condition, FheInt32 thenValue, FheInt32 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt32::new);
  }

}
