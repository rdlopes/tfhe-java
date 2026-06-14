package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.FheRegistry;

import io.github.rdlopes.tfhe.api.*;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class FheUint64 extends AbstractFheUnsignedInteger<Long, FheUint64, CompressedFheUint64>
    implements FheUnsignedInteger<Long, FheUint64, CompressedFheUint64> {

  static {
    FheRegistry.registerFactory(FheUint64.class, FheUint64::new);
  }

  static final FheTypeHandles<Long> HANDLES = new FheTypeHandles<>(
      FheValueKind.LONG,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint64_destroy,
          TfheHeader::fhe_uint64_clone,
          TfheHeader::fhe_uint64_compress,
          TfheHeader::fhe_uint64_safe_serialize,
          TfheHeader::fhe_uint64_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_uint64_try_encrypt_with_client_key_u64,
          TfheHeader::fhe_uint64_try_encrypt_with_public_key_u64,
          TfheHeader::fhe_uint64_try_encrypt_trivial_u64,
          null, null, null,
          TfheHeader::fhe_uint64_decrypt,
          null),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint64_bitand,       TfheHeader::fhe_uint64_bitor,      TfheHeader::fhe_uint64_bitxor,
          TfheHeader::fhe_uint64_not,
          TfheHeader::fhe_uint64_bitand_assign, TfheHeader::fhe_uint64_bitor_assign, TfheHeader::fhe_uint64_bitxor_assign,
          TfheHeader::fhe_uint64_scalar_bitand, TfheHeader::fhe_uint64_scalar_bitor, TfheHeader::fhe_uint64_scalar_bitxor,
          TfheHeader::fhe_uint64_scalar_bitand_assign, TfheHeader::fhe_uint64_scalar_bitor_assign, TfheHeader::fhe_uint64_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint64_eq,     TfheHeader::fhe_uint64_ne,
          TfheHeader::fhe_uint64_scalar_eq, TfheHeader::fhe_uint64_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint64_lt,          TfheHeader::fhe_uint64_le,
          TfheHeader::fhe_uint64_gt,          TfheHeader::fhe_uint64_ge,
          TfheHeader::fhe_uint64_min,         TfheHeader::fhe_uint64_max,
          TfheHeader::fhe_uint64_scalar_lt,   TfheHeader::fhe_uint64_scalar_le,
          TfheHeader::fhe_uint64_scalar_gt,   TfheHeader::fhe_uint64_scalar_ge,
          TfheHeader::fhe_uint64_scalar_min,  TfheHeader::fhe_uint64_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint64_add,          TfheHeader::fhe_uint64_sub,        TfheHeader::fhe_uint64_mul,
          TfheHeader::fhe_uint64_div,          TfheHeader::fhe_uint64_rem,
          TfheHeader::fhe_uint64_overflowing_add, TfheHeader::fhe_uint64_overflowing_sub, TfheHeader::fhe_uint64_overflowing_mul,
          TfheHeader::fhe_uint64_div_rem,
          TfheHeader::fhe_uint64_add_assign,   TfheHeader::fhe_uint64_sub_assign,  TfheHeader::fhe_uint64_mul_assign,
          TfheHeader::fhe_uint64_div_assign,   TfheHeader::fhe_uint64_rem_assign,
          TfheHeader::fhe_uint64_scalar_add,   TfheHeader::fhe_uint64_scalar_sub,  TfheHeader::fhe_uint64_scalar_mul,
          TfheHeader::fhe_uint64_scalar_div,   TfheHeader::fhe_uint64_scalar_rem,
          TfheHeader::fhe_uint64_scalar_add_assign, TfheHeader::fhe_uint64_scalar_sub_assign, TfheHeader::fhe_uint64_scalar_mul_assign,
          TfheHeader::fhe_uint64_scalar_div_assign, TfheHeader::fhe_uint64_scalar_rem_assign,
          TfheHeader::fhe_uint64_scalar_div_rem,
          TfheHeader::fhe_uint64_neg,
          null,                               // abs — N/A for unsigned
          TfheHeader::fhe_uint64_ilog2,
          TfheHeader::fhe_uint64_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint64_shl,           TfheHeader::fhe_uint64_shr,
          TfheHeader::fhe_uint64_rotate_left,   TfheHeader::fhe_uint64_rotate_right,
          TfheHeader::fhe_uint64_shl_assign,    TfheHeader::fhe_uint64_shr_assign,
          TfheHeader::fhe_uint64_rotate_left_assign, TfheHeader::fhe_uint64_rotate_right_assign,
          TfheHeader::fhe_uint64_scalar_shl,    TfheHeader::fhe_uint64_scalar_shr,
          TfheHeader::fhe_uint64_scalar_rotate_left, TfheHeader::fhe_uint64_scalar_rotate_right,
          TfheHeader::fhe_uint64_scalar_shl_assign, TfheHeader::fhe_uint64_scalar_shr_assign,
          TfheHeader::fhe_uint64_scalar_rotate_left_assign, TfheHeader::fhe_uint64_scalar_rotate_right_assign,
          TfheHeader::fhe_uint64_leading_ones,  TfheHeader::fhe_uint64_leading_zeros,
          TfheHeader::fhe_uint64_trailing_ones, TfheHeader::fhe_uint64_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint64,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint64,
          TfheHeader::fhe_uint64_if_then_else));

  FheUint64() { super(TfheHeader::fhe_uint64_destroy); }

  @Override protected FheTypeHandles<Long> handles()      { return HANDLES; }
  @Override protected FheUint64           newInstance()   { return new FheUint64(); }
  @Override protected CompressedFheUint64 newCompressed() { return new CompressedFheUint64(); }

  public static FheUint64 encrypt(Long clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint64::new);
  }
  public static FheUint64 encrypt(Long clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint64::new);
  }
  public static FheUint64 encrypt(Long clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint64::new);
  }
  public static FheUint64 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint64::new);
  }
  public static FheUint64 ifThenElse(FheBool condition, FheUint64 thenValue, FheUint64 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint64::new);
  }

}
