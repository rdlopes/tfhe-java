package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.FheRegistry;

@Generated
public final class FheInt10 extends AbstractFheType<Short, FheInt10, CompressedFheInt10>
  implements FheSignedInteger<Short, FheInt10, CompressedFheInt10> {

  static {
    FheRegistry.registerFactory(FheInt10.class, FheInt10::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int10_destroy,
          TfheHeader::fhe_int10_clone,
          TfheHeader::fhe_int10_compress,
          TfheHeader::fhe_int10_safe_serialize,
          TfheHeader::fhe_int10_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int10_try_encrypt_with_client_key_i16,
          TfheHeader::fhe_int10_try_encrypt_with_public_key_i16,
          TfheHeader::fhe_int10_try_encrypt_trivial_i16,
          null, null, null,
          TfheHeader::fhe_int10_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int10_bitand,       TfheHeader::fhe_int10_bitor,      TfheHeader::fhe_int10_bitxor,
          TfheHeader::fhe_int10_not,
          TfheHeader::fhe_int10_bitand_assign, TfheHeader::fhe_int10_bitor_assign, TfheHeader::fhe_int10_bitxor_assign,
          TfheHeader::fhe_int10_scalar_bitand, TfheHeader::fhe_int10_scalar_bitor, TfheHeader::fhe_int10_scalar_bitxor,
          TfheHeader::fhe_int10_scalar_bitand_assign, TfheHeader::fhe_int10_scalar_bitor_assign, TfheHeader::fhe_int10_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int10_eq,     TfheHeader::fhe_int10_ne,
          TfheHeader::fhe_int10_scalar_eq, TfheHeader::fhe_int10_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int10_lt,          TfheHeader::fhe_int10_le,
          TfheHeader::fhe_int10_gt,          TfheHeader::fhe_int10_ge,
          TfheHeader::fhe_int10_min,         TfheHeader::fhe_int10_max,
          TfheHeader::fhe_int10_scalar_lt,   TfheHeader::fhe_int10_scalar_le,
          TfheHeader::fhe_int10_scalar_gt,   TfheHeader::fhe_int10_scalar_ge,
          TfheHeader::fhe_int10_scalar_min,  TfheHeader::fhe_int10_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int10_add,          TfheHeader::fhe_int10_sub,        TfheHeader::fhe_int10_mul,
          TfheHeader::fhe_int10_div,          TfheHeader::fhe_int10_rem,
          TfheHeader::fhe_int10_overflowing_add, TfheHeader::fhe_int10_overflowing_sub, TfheHeader::fhe_int10_overflowing_mul,
          TfheHeader::fhe_int10_div_rem,
          TfheHeader::fhe_int10_add_assign,   TfheHeader::fhe_int10_sub_assign,  TfheHeader::fhe_int10_mul_assign,
          TfheHeader::fhe_int10_div_assign,   TfheHeader::fhe_int10_rem_assign,
          TfheHeader::fhe_int10_scalar_add,   TfheHeader::fhe_int10_scalar_sub,  TfheHeader::fhe_int10_scalar_mul,
          TfheHeader::fhe_int10_scalar_div,   TfheHeader::fhe_int10_scalar_rem,
          TfheHeader::fhe_int10_scalar_add_assign, TfheHeader::fhe_int10_scalar_sub_assign, TfheHeader::fhe_int10_scalar_mul_assign,
          TfheHeader::fhe_int10_scalar_div_assign, TfheHeader::fhe_int10_scalar_rem_assign,
          TfheHeader::fhe_int10_scalar_div_rem,
          TfheHeader::fhe_int10_neg,
          TfheHeader::fhe_int10_abs,
          TfheHeader::fhe_int10_ilog2,
          TfheHeader::fhe_int10_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int10_shl,           TfheHeader::fhe_int10_shr,
          TfheHeader::fhe_int10_rotate_left,   TfheHeader::fhe_int10_rotate_right,
          TfheHeader::fhe_int10_shl_assign,    TfheHeader::fhe_int10_shr_assign,
          TfheHeader::fhe_int10_rotate_left_assign, TfheHeader::fhe_int10_rotate_right_assign,
          TfheHeader::fhe_int10_scalar_shl,    TfheHeader::fhe_int10_scalar_shr,
          TfheHeader::fhe_int10_scalar_rotate_left, TfheHeader::fhe_int10_scalar_rotate_right,
          TfheHeader::fhe_int10_scalar_shl_assign, TfheHeader::fhe_int10_scalar_shr_assign,
          TfheHeader::fhe_int10_scalar_rotate_left_assign, TfheHeader::fhe_int10_scalar_rotate_right_assign,
          TfheHeader::fhe_int10_leading_ones,  TfheHeader::fhe_int10_leading_zeros,
          TfheHeader::fhe_int10_trailing_ones, TfheHeader::fhe_int10_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int10,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int10,
          TfheHeader::fhe_int10_if_then_else));

  FheInt10() { super(TfheHeader::fhe_int10_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheInt10             newInstance()   { return new FheInt10(); }
  @Override protected CompressedFheInt10   newCompressed() { return new CompressedFheInt10(); }

  public static FheInt10 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt10::new);
  }
  public static FheInt10 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt10::new);
  }
  public static FheInt10 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt10::new);
  }
  public static FheInt10 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt10::new);
  }
  public static FheInt10 ifThenElse(FheBool condition, FheInt10 thenValue, FheInt10 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt10::new);
  }

}
