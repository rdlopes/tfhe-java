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
public final class FheInt4 extends AbstractFheType<Byte, FheInt4, CompressedFheInt4>
  implements FheSignedInteger<Byte, FheInt4, CompressedFheInt4> {

  static {
    FheRegistry.registerFactory(FheInt4.class, FheInt4::new);
  }

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int4_destroy,
          TfheHeader::fhe_int4_clone,
          TfheHeader::fhe_int4_compress,
          TfheHeader::fhe_int4_safe_serialize,
          TfheHeader::fhe_int4_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int4_try_encrypt_with_client_key_i8,
          TfheHeader::fhe_int4_try_encrypt_with_public_key_i8,
          TfheHeader::fhe_int4_try_encrypt_trivial_i8,
          null, null, null,
          TfheHeader::fhe_int4_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int4_bitand,       TfheHeader::fhe_int4_bitor,      TfheHeader::fhe_int4_bitxor,
          TfheHeader::fhe_int4_not,
          TfheHeader::fhe_int4_bitand_assign, TfheHeader::fhe_int4_bitor_assign, TfheHeader::fhe_int4_bitxor_assign,
          TfheHeader::fhe_int4_scalar_bitand, TfheHeader::fhe_int4_scalar_bitor, TfheHeader::fhe_int4_scalar_bitxor,
          TfheHeader::fhe_int4_scalar_bitand_assign, TfheHeader::fhe_int4_scalar_bitor_assign, TfheHeader::fhe_int4_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int4_eq,     TfheHeader::fhe_int4_ne,
          TfheHeader::fhe_int4_scalar_eq, TfheHeader::fhe_int4_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int4_lt,          TfheHeader::fhe_int4_le,
          TfheHeader::fhe_int4_gt,          TfheHeader::fhe_int4_ge,
          TfheHeader::fhe_int4_min,         TfheHeader::fhe_int4_max,
          TfheHeader::fhe_int4_scalar_lt,   TfheHeader::fhe_int4_scalar_le,
          TfheHeader::fhe_int4_scalar_gt,   TfheHeader::fhe_int4_scalar_ge,
          TfheHeader::fhe_int4_scalar_min,  TfheHeader::fhe_int4_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int4_add,          TfheHeader::fhe_int4_sub,        TfheHeader::fhe_int4_mul,
          TfheHeader::fhe_int4_div,          TfheHeader::fhe_int4_rem,
          TfheHeader::fhe_int4_overflowing_add, TfheHeader::fhe_int4_overflowing_sub, TfheHeader::fhe_int4_overflowing_mul,
          TfheHeader::fhe_int4_div_rem,
          TfheHeader::fhe_int4_add_assign,   TfheHeader::fhe_int4_sub_assign,  TfheHeader::fhe_int4_mul_assign,
          TfheHeader::fhe_int4_div_assign,   TfheHeader::fhe_int4_rem_assign,
          TfheHeader::fhe_int4_scalar_add,   TfheHeader::fhe_int4_scalar_sub,  TfheHeader::fhe_int4_scalar_mul,
          TfheHeader::fhe_int4_scalar_div,   TfheHeader::fhe_int4_scalar_rem,
          TfheHeader::fhe_int4_scalar_add_assign, TfheHeader::fhe_int4_scalar_sub_assign, TfheHeader::fhe_int4_scalar_mul_assign,
          TfheHeader::fhe_int4_scalar_div_assign, TfheHeader::fhe_int4_scalar_rem_assign,
          TfheHeader::fhe_int4_scalar_div_rem,
          TfheHeader::fhe_int4_neg,
          TfheHeader::fhe_int4_abs,
          TfheHeader::fhe_int4_ilog2,
          TfheHeader::fhe_int4_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int4_shl,           TfheHeader::fhe_int4_shr,
          TfheHeader::fhe_int4_rotate_left,   TfheHeader::fhe_int4_rotate_right,
          TfheHeader::fhe_int4_shl_assign,    TfheHeader::fhe_int4_shr_assign,
          TfheHeader::fhe_int4_rotate_left_assign, TfheHeader::fhe_int4_rotate_right_assign,
          TfheHeader::fhe_int4_scalar_shl,    TfheHeader::fhe_int4_scalar_shr,
          TfheHeader::fhe_int4_scalar_rotate_left, TfheHeader::fhe_int4_scalar_rotate_right,
          TfheHeader::fhe_int4_scalar_shl_assign, TfheHeader::fhe_int4_scalar_shr_assign,
          TfheHeader::fhe_int4_scalar_rotate_left_assign, TfheHeader::fhe_int4_scalar_rotate_right_assign,
          TfheHeader::fhe_int4_leading_ones,  TfheHeader::fhe_int4_leading_zeros,
          TfheHeader::fhe_int4_trailing_ones, TfheHeader::fhe_int4_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int4,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int4,
          TfheHeader::fhe_int4_if_then_else));

  FheInt4() { super(TfheHeader::fhe_int4_destroy); }

  @Override protected FheTypeHandles<Byte> handles()      { return HANDLES; }
  @Override protected FheInt4             newInstance()   { return new FheInt4(); }
  @Override protected CompressedFheInt4   newCompressed() { return new CompressedFheInt4(); }

  public static FheInt4 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt4::new);
  }
  public static FheInt4 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt4::new);
  }
  public static FheInt4 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt4::new);
  }
  public static FheInt4 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt4::new);
  }
  public static FheInt4 ifThenElse(FheBool condition, FheInt4 thenValue, FheInt4 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt4::new);
  }

}
