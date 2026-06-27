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
public final class FheInt6 extends AbstractFheType<Byte, FheInt6, CompressedFheInt6>
  implements FheSignedInteger<Byte, FheInt6, CompressedFheInt6> {

  static {
    FheRegistry.registerFactory(FheInt6.class, FheInt6::new);
  }

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int6_destroy,
          TfheHeader::fhe_int6_clone,
          TfheHeader::fhe_int6_compress,
          TfheHeader::fhe_int6_safe_serialize,
          TfheHeader::fhe_int6_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int6_try_encrypt_with_client_key_i8,
          TfheHeader::fhe_int6_try_encrypt_with_public_key_i8,
          TfheHeader::fhe_int6_try_encrypt_trivial_i8,
          null, null, null,
          TfheHeader::fhe_int6_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int6_bitand,       TfheHeader::fhe_int6_bitor,      TfheHeader::fhe_int6_bitxor,
          TfheHeader::fhe_int6_not,
          TfheHeader::fhe_int6_bitand_assign, TfheHeader::fhe_int6_bitor_assign, TfheHeader::fhe_int6_bitxor_assign,
          TfheHeader::fhe_int6_scalar_bitand, TfheHeader::fhe_int6_scalar_bitor, TfheHeader::fhe_int6_scalar_bitxor,
          TfheHeader::fhe_int6_scalar_bitand_assign, TfheHeader::fhe_int6_scalar_bitor_assign, TfheHeader::fhe_int6_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int6_eq,     TfheHeader::fhe_int6_ne,
          TfheHeader::fhe_int6_scalar_eq, TfheHeader::fhe_int6_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int6_lt,          TfheHeader::fhe_int6_le,
          TfheHeader::fhe_int6_gt,          TfheHeader::fhe_int6_ge,
          TfheHeader::fhe_int6_min,         TfheHeader::fhe_int6_max,
          TfheHeader::fhe_int6_scalar_lt,   TfheHeader::fhe_int6_scalar_le,
          TfheHeader::fhe_int6_scalar_gt,   TfheHeader::fhe_int6_scalar_ge,
          TfheHeader::fhe_int6_scalar_min,  TfheHeader::fhe_int6_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int6_add,          TfheHeader::fhe_int6_sub,        TfheHeader::fhe_int6_mul,
          TfheHeader::fhe_int6_div,          TfheHeader::fhe_int6_rem,
          TfheHeader::fhe_int6_overflowing_add, TfheHeader::fhe_int6_overflowing_sub, TfheHeader::fhe_int6_overflowing_mul,
          TfheHeader::fhe_int6_div_rem,
          TfheHeader::fhe_int6_add_assign,   TfheHeader::fhe_int6_sub_assign,  TfheHeader::fhe_int6_mul_assign,
          TfheHeader::fhe_int6_div_assign,   TfheHeader::fhe_int6_rem_assign,
          TfheHeader::fhe_int6_scalar_add,   TfheHeader::fhe_int6_scalar_sub,  TfheHeader::fhe_int6_scalar_mul,
          TfheHeader::fhe_int6_scalar_div,   TfheHeader::fhe_int6_scalar_rem,
          TfheHeader::fhe_int6_scalar_add_assign, TfheHeader::fhe_int6_scalar_sub_assign, TfheHeader::fhe_int6_scalar_mul_assign,
          TfheHeader::fhe_int6_scalar_div_assign, TfheHeader::fhe_int6_scalar_rem_assign,
          TfheHeader::fhe_int6_scalar_div_rem,
          TfheHeader::fhe_int6_neg,
          TfheHeader::fhe_int6_abs,
          TfheHeader::fhe_int6_ilog2,
          TfheHeader::fhe_int6_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int6_shl,           TfheHeader::fhe_int6_shr,
          TfheHeader::fhe_int6_rotate_left,   TfheHeader::fhe_int6_rotate_right,
          TfheHeader::fhe_int6_shl_assign,    TfheHeader::fhe_int6_shr_assign,
          TfheHeader::fhe_int6_rotate_left_assign, TfheHeader::fhe_int6_rotate_right_assign,
          TfheHeader::fhe_int6_scalar_shl,    TfheHeader::fhe_int6_scalar_shr,
          TfheHeader::fhe_int6_scalar_rotate_left, TfheHeader::fhe_int6_scalar_rotate_right,
          TfheHeader::fhe_int6_scalar_shl_assign, TfheHeader::fhe_int6_scalar_shr_assign,
          TfheHeader::fhe_int6_scalar_rotate_left_assign, TfheHeader::fhe_int6_scalar_rotate_right_assign,
          TfheHeader::fhe_int6_leading_ones,  TfheHeader::fhe_int6_leading_zeros,
          TfheHeader::fhe_int6_trailing_ones, TfheHeader::fhe_int6_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int6,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int6,
          TfheHeader::fhe_int6_if_then_else));

  FheInt6() { super(TfheHeader::fhe_int6_destroy); }

  @Override protected FheTypeHandles<Byte> handles()      { return HANDLES; }
  @Override protected FheInt6             newInstance()   { return new FheInt6(); }
  @Override protected CompressedFheInt6   newCompressed() { return new CompressedFheInt6(); }

  public static FheInt6 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt6::new);
  }
  public static FheInt6 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt6::new);
  }
  public static FheInt6 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt6::new);
  }
  public static FheInt6 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt6::new);
  }
  public static FheInt6 ifThenElse(FheBool condition, FheInt6 thenValue, FheInt6 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt6::new);
  }

}
