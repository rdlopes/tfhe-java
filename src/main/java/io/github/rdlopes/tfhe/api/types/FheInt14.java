package io.github.rdlopes.tfhe.api.types;

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

public final class FheInt14 extends AbstractFheType<Short, FheInt14, CompressedFheInt14>
  implements FheSignedInteger<Short, FheInt14, CompressedFheInt14> {

  static {
    FheRegistry.registerFactory(FheInt14.class, FheInt14::new);
  }

  static final FheTypeHandles<Short> HANDLES = new FheTypeHandles<>(
      FheValueKind.SHORT,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int14_destroy,
          TfheHeader::fhe_int14_clone,
          TfheHeader::fhe_int14_compress,
          TfheHeader::fhe_int14_safe_serialize,
          TfheHeader::fhe_int14_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int14_try_encrypt_with_client_key_i16,
          TfheHeader::fhe_int14_try_encrypt_with_public_key_i16,
          TfheHeader::fhe_int14_try_encrypt_trivial_i16,
          null, null, null,
          TfheHeader::fhe_int14_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int14_bitand,       TfheHeader::fhe_int14_bitor,      TfheHeader::fhe_int14_bitxor,
          TfheHeader::fhe_int14_not,
          TfheHeader::fhe_int14_bitand_assign, TfheHeader::fhe_int14_bitor_assign, TfheHeader::fhe_int14_bitxor_assign,
          TfheHeader::fhe_int14_scalar_bitand, TfheHeader::fhe_int14_scalar_bitor, TfheHeader::fhe_int14_scalar_bitxor,
          TfheHeader::fhe_int14_scalar_bitand_assign, TfheHeader::fhe_int14_scalar_bitor_assign, TfheHeader::fhe_int14_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int14_eq,     TfheHeader::fhe_int14_ne,
          TfheHeader::fhe_int14_scalar_eq, TfheHeader::fhe_int14_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int14_lt,          TfheHeader::fhe_int14_le,
          TfheHeader::fhe_int14_gt,          TfheHeader::fhe_int14_ge,
          TfheHeader::fhe_int14_min,         TfheHeader::fhe_int14_max,
          TfheHeader::fhe_int14_scalar_lt,   TfheHeader::fhe_int14_scalar_le,
          TfheHeader::fhe_int14_scalar_gt,   TfheHeader::fhe_int14_scalar_ge,
          TfheHeader::fhe_int14_scalar_min,  TfheHeader::fhe_int14_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int14_add,          TfheHeader::fhe_int14_sub,        TfheHeader::fhe_int14_mul,
          TfheHeader::fhe_int14_div,          TfheHeader::fhe_int14_rem,
          TfheHeader::fhe_int14_overflowing_add, TfheHeader::fhe_int14_overflowing_sub, TfheHeader::fhe_int14_overflowing_mul,
          TfheHeader::fhe_int14_div_rem,
          TfheHeader::fhe_int14_add_assign,   TfheHeader::fhe_int14_sub_assign,  TfheHeader::fhe_int14_mul_assign,
          TfheHeader::fhe_int14_div_assign,   TfheHeader::fhe_int14_rem_assign,
          TfheHeader::fhe_int14_scalar_add,   TfheHeader::fhe_int14_scalar_sub,  TfheHeader::fhe_int14_scalar_mul,
          TfheHeader::fhe_int14_scalar_div,   TfheHeader::fhe_int14_scalar_rem,
          TfheHeader::fhe_int14_scalar_add_assign, TfheHeader::fhe_int14_scalar_sub_assign, TfheHeader::fhe_int14_scalar_mul_assign,
          TfheHeader::fhe_int14_scalar_div_assign, TfheHeader::fhe_int14_scalar_rem_assign,
          TfheHeader::fhe_int14_scalar_div_rem,
          TfheHeader::fhe_int14_neg,
          TfheHeader::fhe_int14_abs,
          TfheHeader::fhe_int14_ilog2,
          TfheHeader::fhe_int14_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int14_shl,           TfheHeader::fhe_int14_shr,
          TfheHeader::fhe_int14_rotate_left,   TfheHeader::fhe_int14_rotate_right,
          TfheHeader::fhe_int14_shl_assign,    TfheHeader::fhe_int14_shr_assign,
          TfheHeader::fhe_int14_rotate_left_assign, TfheHeader::fhe_int14_rotate_right_assign,
          TfheHeader::fhe_int14_scalar_shl,    TfheHeader::fhe_int14_scalar_shr,
          TfheHeader::fhe_int14_scalar_rotate_left, TfheHeader::fhe_int14_scalar_rotate_right,
          TfheHeader::fhe_int14_scalar_shl_assign, TfheHeader::fhe_int14_scalar_shr_assign,
          TfheHeader::fhe_int14_scalar_rotate_left_assign, TfheHeader::fhe_int14_scalar_rotate_right_assign,
          TfheHeader::fhe_int14_leading_ones,  TfheHeader::fhe_int14_leading_zeros,
          TfheHeader::fhe_int14_trailing_ones, TfheHeader::fhe_int14_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int14,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int14,
          TfheHeader::fhe_int14_if_then_else));

  FheInt14() { super(TfheHeader::fhe_int14_destroy); }

  @Override protected FheTypeHandles<Short> handles()      { return HANDLES; }
  @Override protected FheInt14             newInstance()   { return new FheInt14(); }
  @Override protected CompressedFheInt14   newCompressed() { return new CompressedFheInt14(); }

  public static FheInt14 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt14::new);
  }
  public static FheInt14 encrypt(Short clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt14::new);
  }
  public static FheInt14 encrypt(Short clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt14::new);
  }
  public static FheInt14 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt14::new);
  }
  public static FheInt14 ifThenElse(FheBool condition, FheInt14 thenValue, FheInt14 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt14::new);
  }

}
