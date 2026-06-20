package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.Fhe;
import io.github.rdlopes.tfhe.api.types.FheBool;

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
public final class FheInt64 extends AbstractFheType<Long, FheInt64, CompressedFheInt64>
  implements FheSignedInteger<Long, FheInt64, CompressedFheInt64> {

  static final FheTypeHandles<Long> HANDLES = new FheTypeHandles<>(
      FheValueKind.LONG,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int64_destroy,
          TfheHeader::fhe_int64_clone,
          TfheHeader::fhe_int64_compress,
          TfheHeader::fhe_int64_safe_serialize,
          TfheHeader::fhe_int64_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int64_try_encrypt_with_client_key_i64,
          TfheHeader::fhe_int64_try_encrypt_with_public_key_i64,
          TfheHeader::fhe_int64_try_encrypt_trivial_i64,
          null, null, null,
          TfheHeader::fhe_int64_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int64_bitand,       TfheHeader::fhe_int64_bitor,      TfheHeader::fhe_int64_bitxor,
          TfheHeader::fhe_int64_not,
          TfheHeader::fhe_int64_bitand_assign, TfheHeader::fhe_int64_bitor_assign, TfheHeader::fhe_int64_bitxor_assign,
          TfheHeader::fhe_int64_scalar_bitand, TfheHeader::fhe_int64_scalar_bitor, TfheHeader::fhe_int64_scalar_bitxor,
          TfheHeader::fhe_int64_scalar_bitand_assign, TfheHeader::fhe_int64_scalar_bitor_assign, TfheHeader::fhe_int64_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int64_eq,     TfheHeader::fhe_int64_ne,
          TfheHeader::fhe_int64_scalar_eq, TfheHeader::fhe_int64_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int64_lt,          TfheHeader::fhe_int64_le,
          TfheHeader::fhe_int64_gt,          TfheHeader::fhe_int64_ge,
          TfheHeader::fhe_int64_min,         TfheHeader::fhe_int64_max,
          TfheHeader::fhe_int64_scalar_lt,   TfheHeader::fhe_int64_scalar_le,
          TfheHeader::fhe_int64_scalar_gt,   TfheHeader::fhe_int64_scalar_ge,
          TfheHeader::fhe_int64_scalar_min,  TfheHeader::fhe_int64_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int64_add,          TfheHeader::fhe_int64_sub,        TfheHeader::fhe_int64_mul,
          TfheHeader::fhe_int64_div,          TfheHeader::fhe_int64_rem,
          TfheHeader::fhe_int64_overflowing_add, TfheHeader::fhe_int64_overflowing_sub, TfheHeader::fhe_int64_overflowing_mul,
          TfheHeader::fhe_int64_div_rem,
          TfheHeader::fhe_int64_add_assign,   TfheHeader::fhe_int64_sub_assign,  TfheHeader::fhe_int64_mul_assign,
          TfheHeader::fhe_int64_div_assign,   TfheHeader::fhe_int64_rem_assign,
          TfheHeader::fhe_int64_scalar_add,   TfheHeader::fhe_int64_scalar_sub,  TfheHeader::fhe_int64_scalar_mul,
          TfheHeader::fhe_int64_scalar_div,   TfheHeader::fhe_int64_scalar_rem,
          TfheHeader::fhe_int64_scalar_add_assign, TfheHeader::fhe_int64_scalar_sub_assign, TfheHeader::fhe_int64_scalar_mul_assign,
          TfheHeader::fhe_int64_scalar_div_assign, TfheHeader::fhe_int64_scalar_rem_assign,
          TfheHeader::fhe_int64_scalar_div_rem,
          TfheHeader::fhe_int64_neg,
          TfheHeader::fhe_int64_abs,
          TfheHeader::fhe_int64_ilog2,
          TfheHeader::fhe_int64_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int64_shl,           TfheHeader::fhe_int64_shr,
          TfheHeader::fhe_int64_rotate_left,   TfheHeader::fhe_int64_rotate_right,
          TfheHeader::fhe_int64_shl_assign,    TfheHeader::fhe_int64_shr_assign,
          TfheHeader::fhe_int64_rotate_left_assign, TfheHeader::fhe_int64_rotate_right_assign,
          TfheHeader::fhe_int64_scalar_shl,    TfheHeader::fhe_int64_scalar_shr,
          TfheHeader::fhe_int64_scalar_rotate_left, TfheHeader::fhe_int64_scalar_rotate_right,
          TfheHeader::fhe_int64_scalar_shl_assign, TfheHeader::fhe_int64_scalar_shr_assign,
          TfheHeader::fhe_int64_scalar_rotate_left_assign, TfheHeader::fhe_int64_scalar_rotate_right_assign,
          TfheHeader::fhe_int64_leading_ones,  TfheHeader::fhe_int64_leading_zeros,
          TfheHeader::fhe_int64_trailing_ones, TfheHeader::fhe_int64_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int64,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int64,
          TfheHeader::fhe_int64_if_then_else));

  static {
    FheRegistry.registerFactory(FheInt64.class, FheInt64::new);
    FheRegistry.registerHandles(FheInt64.class, HANDLES);
  }

  FheInt64() { super(TfheHeader::fhe_int64_destroy); }

  @Override protected FheTypeHandles<Long> handles()      { return HANDLES; }
  @Override protected FheInt64            newInstance()   { return new FheInt64(); }
  @Override protected CompressedFheInt64  newCompressed() { return new CompressedFheInt64(); }

  public static FheInt64 encrypt(Long clearValue, ClientKey clientKey) {
    return Fhe.encrypt(clearValue, clientKey, FheInt64.class);
  }
  public static FheInt64 encrypt(Long clearValue, PublicKey publicKey) {
    return Fhe.encrypt(clearValue, publicKey, FheInt64.class);
  }
  public static FheInt64 encrypt(Long clearValue) {
    return Fhe.encrypt(clearValue, FheInt64.class);
  }
  public static FheInt64 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return Fhe.deserialize(buffer, serverKey, FheInt64.class);
  }
  public static FheInt64 ifThenElse(FheBool condition, FheInt64 thenValue, FheInt64 elseValue) {
    return thenValue.ifThenElse(condition, elseValue);
  }


  @Override
  public FheInt64 abs() {
    return absImpl();
  }
}
