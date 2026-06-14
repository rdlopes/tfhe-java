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

public final class FheInt2 extends AbstractFheType<Byte, FheInt2, CompressedFheInt2>
  implements FheSignedInteger<Byte, FheInt2, CompressedFheInt2> {

  static {
    FheRegistry.registerFactory(FheInt2.class, FheInt2::new);
  }

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int2_destroy,
          TfheHeader::fhe_int2_clone,
          TfheHeader::fhe_int2_compress,
          TfheHeader::fhe_int2_safe_serialize,
          TfheHeader::fhe_int2_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int2_try_encrypt_with_client_key_i8,
          TfheHeader::fhe_int2_try_encrypt_with_public_key_i8,
          TfheHeader::fhe_int2_try_encrypt_trivial_i8,
          null, null, null,
          TfheHeader::fhe_int2_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int2_bitand,       TfheHeader::fhe_int2_bitor,      TfheHeader::fhe_int2_bitxor,
          TfheHeader::fhe_int2_not,
          TfheHeader::fhe_int2_bitand_assign, TfheHeader::fhe_int2_bitor_assign, TfheHeader::fhe_int2_bitxor_assign,
          TfheHeader::fhe_int2_scalar_bitand, TfheHeader::fhe_int2_scalar_bitor, TfheHeader::fhe_int2_scalar_bitxor,
          TfheHeader::fhe_int2_scalar_bitand_assign, TfheHeader::fhe_int2_scalar_bitor_assign, TfheHeader::fhe_int2_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int2_eq,     TfheHeader::fhe_int2_ne,
          TfheHeader::fhe_int2_scalar_eq, TfheHeader::fhe_int2_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int2_lt,          TfheHeader::fhe_int2_le,
          TfheHeader::fhe_int2_gt,          TfheHeader::fhe_int2_ge,
          TfheHeader::fhe_int2_min,         TfheHeader::fhe_int2_max,
          TfheHeader::fhe_int2_scalar_lt,   TfheHeader::fhe_int2_scalar_le,
          TfheHeader::fhe_int2_scalar_gt,   TfheHeader::fhe_int2_scalar_ge,
          TfheHeader::fhe_int2_scalar_min,  TfheHeader::fhe_int2_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int2_add,          TfheHeader::fhe_int2_sub,        TfheHeader::fhe_int2_mul,
          TfheHeader::fhe_int2_div,          TfheHeader::fhe_int2_rem,
          TfheHeader::fhe_int2_overflowing_add, TfheHeader::fhe_int2_overflowing_sub, TfheHeader::fhe_int2_overflowing_mul,
          TfheHeader::fhe_int2_div_rem,
          TfheHeader::fhe_int2_add_assign,   TfheHeader::fhe_int2_sub_assign,  TfheHeader::fhe_int2_mul_assign,
          TfheHeader::fhe_int2_div_assign,   TfheHeader::fhe_int2_rem_assign,
          TfheHeader::fhe_int2_scalar_add,   TfheHeader::fhe_int2_scalar_sub,  TfheHeader::fhe_int2_scalar_mul,
          TfheHeader::fhe_int2_scalar_div,   TfheHeader::fhe_int2_scalar_rem,
          TfheHeader::fhe_int2_scalar_add_assign, TfheHeader::fhe_int2_scalar_sub_assign, TfheHeader::fhe_int2_scalar_mul_assign,
          TfheHeader::fhe_int2_scalar_div_assign, TfheHeader::fhe_int2_scalar_rem_assign,
          TfheHeader::fhe_int2_scalar_div_rem,
          TfheHeader::fhe_int2_neg,
          TfheHeader::fhe_int2_abs,
          TfheHeader::fhe_int2_ilog2,
          TfheHeader::fhe_int2_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int2_shl,           TfheHeader::fhe_int2_shr,
          TfheHeader::fhe_int2_rotate_left,   TfheHeader::fhe_int2_rotate_right,
          TfheHeader::fhe_int2_shl_assign,    TfheHeader::fhe_int2_shr_assign,
          TfheHeader::fhe_int2_rotate_left_assign, TfheHeader::fhe_int2_rotate_right_assign,
          TfheHeader::fhe_int2_scalar_shl,    TfheHeader::fhe_int2_scalar_shr,
          TfheHeader::fhe_int2_scalar_rotate_left, TfheHeader::fhe_int2_scalar_rotate_right,
          TfheHeader::fhe_int2_scalar_shl_assign, TfheHeader::fhe_int2_scalar_shr_assign,
          TfheHeader::fhe_int2_scalar_rotate_left_assign, TfheHeader::fhe_int2_scalar_rotate_right_assign,
          TfheHeader::fhe_int2_leading_ones,  TfheHeader::fhe_int2_leading_zeros,
          TfheHeader::fhe_int2_trailing_ones, TfheHeader::fhe_int2_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int2,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int2,
          TfheHeader::fhe_int2_if_then_else));

  FheInt2() { super(TfheHeader::fhe_int2_destroy); }

  @Override protected FheTypeHandles<Byte> handles()      { return HANDLES; }
  @Override protected FheInt2             newInstance()   { return new FheInt2(); }
  @Override protected CompressedFheInt2   newCompressed() { return new CompressedFheInt2(); }

  public static FheInt2 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt2::new);
  }
  public static FheInt2 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt2::new);
  }
  public static FheInt2 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt2::new);
  }
  public static FheInt2 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt2::new);
  }
  public static FheInt2 ifThenElse(FheBool condition, FheInt2 thenValue, FheInt2 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt2::new);
  }

}
