package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheUnsignedInteger;
import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import io.github.rdlopes.tfhe.core.utils.FheRegistry;

/// Encrypted unsigned 8-bit integer (`uint8_t`).
@Generated
public final class FheUint8 extends AbstractFheUnsignedInteger<Byte, FheUint8, CompressedFheUint8>
    implements FheUnsignedInteger<Byte, FheUint8, CompressedFheUint8> {

  static {
    FheRegistry.registerFactory(FheUint8.class, FheUint8::new);
  }

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint8_destroy,
          TfheHeader::fhe_uint8_clone,
          TfheHeader::fhe_uint8_compress,
          TfheHeader::fhe_uint8_safe_serialize,
          TfheHeader::fhe_uint8_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint8_try_encrypt_with_client_key_u8,
          TfheHeader::fhe_uint8_try_encrypt_with_public_key_u8,
          TfheHeader::fhe_uint8_try_encrypt_trivial_u8,
          null, null, null,
          TfheHeader::fhe_uint8_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint8_bitand,           TfheHeader::fhe_uint8_bitor,          TfheHeader::fhe_uint8_bitxor,
          TfheHeader::fhe_uint8_not,
          TfheHeader::fhe_uint8_bitand_assign,    TfheHeader::fhe_uint8_bitor_assign,   TfheHeader::fhe_uint8_bitxor_assign,
          TfheHeader::fhe_uint8_scalar_bitand,    TfheHeader::fhe_uint8_scalar_bitor,   TfheHeader::fhe_uint8_scalar_bitxor,
          TfheHeader::fhe_uint8_scalar_bitand_assign, TfheHeader::fhe_uint8_scalar_bitor_assign, TfheHeader::fhe_uint8_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint8_eq,               TfheHeader::fhe_uint8_ne,
          TfheHeader::fhe_uint8_scalar_eq,        TfheHeader::fhe_uint8_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint8_lt,               TfheHeader::fhe_uint8_le,
          TfheHeader::fhe_uint8_gt,               TfheHeader::fhe_uint8_ge,
          TfheHeader::fhe_uint8_min,              TfheHeader::fhe_uint8_max,
          TfheHeader::fhe_uint8_scalar_lt,        TfheHeader::fhe_uint8_scalar_le,
          TfheHeader::fhe_uint8_scalar_gt,        TfheHeader::fhe_uint8_scalar_ge,
          TfheHeader::fhe_uint8_scalar_min,       TfheHeader::fhe_uint8_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint8_add,              TfheHeader::fhe_uint8_sub,            TfheHeader::fhe_uint8_mul,
          TfheHeader::fhe_uint8_div,              TfheHeader::fhe_uint8_rem,
          TfheHeader::fhe_uint8_overflowing_add,  TfheHeader::fhe_uint8_overflowing_sub, TfheHeader::fhe_uint8_overflowing_mul,
          TfheHeader::fhe_uint8_div_rem,
          TfheHeader::fhe_uint8_add_assign,       TfheHeader::fhe_uint8_sub_assign,     TfheHeader::fhe_uint8_mul_assign,
          TfheHeader::fhe_uint8_div_assign,       TfheHeader::fhe_uint8_rem_assign,
          TfheHeader::fhe_uint8_scalar_add,       TfheHeader::fhe_uint8_scalar_sub,     TfheHeader::fhe_uint8_scalar_mul,
          TfheHeader::fhe_uint8_scalar_div,       TfheHeader::fhe_uint8_scalar_rem,
          TfheHeader::fhe_uint8_scalar_add_assign, TfheHeader::fhe_uint8_scalar_sub_assign, TfheHeader::fhe_uint8_scalar_mul_assign,
          TfheHeader::fhe_uint8_scalar_div_assign, TfheHeader::fhe_uint8_scalar_rem_assign,
          TfheHeader::fhe_uint8_scalar_div_rem,
          TfheHeader::fhe_uint8_neg,
          null,                                   // abs — N/A for unsigned
          TfheHeader::fhe_uint8_ilog2,
          TfheHeader::fhe_uint8_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint8_shl,              TfheHeader::fhe_uint8_shr,
          TfheHeader::fhe_uint8_rotate_left,      TfheHeader::fhe_uint8_rotate_right,
          TfheHeader::fhe_uint8_shl_assign,       TfheHeader::fhe_uint8_shr_assign,
          TfheHeader::fhe_uint8_rotate_left_assign, TfheHeader::fhe_uint8_rotate_right_assign,
          TfheHeader::fhe_uint8_scalar_shl,       TfheHeader::fhe_uint8_scalar_shr,
          TfheHeader::fhe_uint8_scalar_rotate_left, TfheHeader::fhe_uint8_scalar_rotate_right,
          TfheHeader::fhe_uint8_scalar_shl_assign, TfheHeader::fhe_uint8_scalar_shr_assign,
          TfheHeader::fhe_uint8_scalar_rotate_left_assign, TfheHeader::fhe_uint8_scalar_rotate_right_assign,
          TfheHeader::fhe_uint8_leading_ones,     TfheHeader::fhe_uint8_leading_zeros,
          TfheHeader::fhe_uint8_trailing_ones,    TfheHeader::fhe_uint8_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint8,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint8,
          TfheHeader::fhe_uint8_if_then_else));

  FheUint8() {
    super(TfheHeader::fhe_uint8_destroy);
  }

  @Override protected FheTypeHandles<Byte>  handles()      { return HANDLES; }
  @Override protected FheUint8             newInstance()   { return new FheUint8(); }
  @Override protected CompressedFheUint8   newCompressed() { return new CompressedFheUint8(); }

  public static FheUint8 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint8::new);
  }

  public static FheUint8 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint8::new);
  }

  public static FheUint8 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint8::new);
  }

  public static FheUint8 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint8::new);
  }

  public static FheUint8 ifThenElse(FheBool condition, FheUint8 thenValue, FheUint8 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint8::new);
  }

}
