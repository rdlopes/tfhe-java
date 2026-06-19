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
public final class FheInt12 extends AbstractFheType<Short, FheInt12, CompressedFheInt12>
  implements FheSignedInteger<Short, FheInt12, CompressedFheInt12> {

  static {
    FheRegistry.registerFactory(FheInt12.class, FheInt12::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int12_destroy,
          TfheHeader::fhe_int12_clone,
          TfheHeader::fhe_int12_compress,
          TfheHeader::fhe_int12_safe_serialize,
          TfheHeader::fhe_int12_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int12_try_encrypt_with_client_key_i16,
          TfheHeader::fhe_int12_try_encrypt_with_public_key_i16,
          TfheHeader::fhe_int12_try_encrypt_trivial_i16,
          null, null, null,
          TfheHeader::fhe_int12_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int12_bitand,       TfheHeader::fhe_int12_bitor,      TfheHeader::fhe_int12_bitxor,
          TfheHeader::fhe_int12_not,
          TfheHeader::fhe_int12_bitand_assign, TfheHeader::fhe_int12_bitor_assign, TfheHeader::fhe_int12_bitxor_assign,
          TfheHeader::fhe_int12_scalar_bitand, TfheHeader::fhe_int12_scalar_bitor, TfheHeader::fhe_int12_scalar_bitxor,
          TfheHeader::fhe_int12_scalar_bitand_assign, TfheHeader::fhe_int12_scalar_bitor_assign, TfheHeader::fhe_int12_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int12_eq,     TfheHeader::fhe_int12_ne,
          TfheHeader::fhe_int12_scalar_eq, TfheHeader::fhe_int12_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int12_lt,          TfheHeader::fhe_int12_le,
          TfheHeader::fhe_int12_gt,          TfheHeader::fhe_int12_ge,
          TfheHeader::fhe_int12_min,         TfheHeader::fhe_int12_max,
          TfheHeader::fhe_int12_scalar_lt,   TfheHeader::fhe_int12_scalar_le,
          TfheHeader::fhe_int12_scalar_gt,   TfheHeader::fhe_int12_scalar_ge,
          TfheHeader::fhe_int12_scalar_min,  TfheHeader::fhe_int12_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int12_add,          TfheHeader::fhe_int12_sub,        TfheHeader::fhe_int12_mul,
          TfheHeader::fhe_int12_div,          TfheHeader::fhe_int12_rem,
          TfheHeader::fhe_int12_overflowing_add, TfheHeader::fhe_int12_overflowing_sub, TfheHeader::fhe_int12_overflowing_mul,
          TfheHeader::fhe_int12_div_rem,
          TfheHeader::fhe_int12_add_assign,   TfheHeader::fhe_int12_sub_assign,  TfheHeader::fhe_int12_mul_assign,
          TfheHeader::fhe_int12_div_assign,   TfheHeader::fhe_int12_rem_assign,
          TfheHeader::fhe_int12_scalar_add,   TfheHeader::fhe_int12_scalar_sub,  TfheHeader::fhe_int12_scalar_mul,
          TfheHeader::fhe_int12_scalar_div,   TfheHeader::fhe_int12_scalar_rem,
          TfheHeader::fhe_int12_scalar_add_assign, TfheHeader::fhe_int12_scalar_sub_assign, TfheHeader::fhe_int12_scalar_mul_assign,
          TfheHeader::fhe_int12_scalar_div_assign, TfheHeader::fhe_int12_scalar_rem_assign,
          TfheHeader::fhe_int12_scalar_div_rem,
          TfheHeader::fhe_int12_neg,
          TfheHeader::fhe_int12_abs,
          TfheHeader::fhe_int12_ilog2,
          TfheHeader::fhe_int12_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int12_shl,           TfheHeader::fhe_int12_shr,
          TfheHeader::fhe_int12_rotate_left,   TfheHeader::fhe_int12_rotate_right,
          TfheHeader::fhe_int12_shl_assign,    TfheHeader::fhe_int12_shr_assign,
          TfheHeader::fhe_int12_rotate_left_assign, TfheHeader::fhe_int12_rotate_right_assign,
          TfheHeader::fhe_int12_scalar_shl,    TfheHeader::fhe_int12_scalar_shr,
          TfheHeader::fhe_int12_scalar_rotate_left, TfheHeader::fhe_int12_scalar_rotate_right,
          TfheHeader::fhe_int12_scalar_shl_assign, TfheHeader::fhe_int12_scalar_shr_assign,
          TfheHeader::fhe_int12_scalar_rotate_left_assign, TfheHeader::fhe_int12_scalar_rotate_right_assign,
          TfheHeader::fhe_int12_leading_ones,  TfheHeader::fhe_int12_leading_zeros,
          TfheHeader::fhe_int12_trailing_ones, TfheHeader::fhe_int12_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int12,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int12,
          TfheHeader::fhe_int12_if_then_else));

  FheInt12() { super(TfheHeader::fhe_int12_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheInt12             newInstance()   { return new FheInt12(); }
  @Override protected CompressedFheInt12   newCompressed() { return new CompressedFheInt12(); }

  public static FheInt12 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt12::new);
  }
  public static FheInt12 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt12::new);
  }
  public static FheInt12 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt12::new);
  }
  public static FheInt12 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt12::new);
  }
  public static FheInt12 ifThenElse(FheBool condition, FheInt12 thenValue, FheInt12 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt12::new);
  }

}
