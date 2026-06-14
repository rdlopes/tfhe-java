package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.FheSignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I256;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted signed 160-bit integer (`int160_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
public final class FheInt160 extends AbstractFheType<I256, FheInt160, CompressedFheInt160>
  implements FheSignedInteger<I256, FheInt160, CompressedFheInt160> {

  static {
    FheRegistry.registerFactory(FheInt160.class, FheInt160::new);
  }

  static final FheTypeHandles<I256> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(I256::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int160_destroy,
          TfheHeader::fhe_int160_clone,
          TfheHeader::fhe_int160_compress,
          TfheHeader::fhe_int160_safe_serialize,
          TfheHeader::fhe_int160_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_int160_try_encrypt_with_client_key_i256,
          TfheHeader::fhe_int160_try_encrypt_with_public_key_i256,
          TfheHeader::fhe_int160_try_encrypt_trivial_i256,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_int160_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int160_bitand,       TfheHeader::fhe_int160_bitor,      TfheHeader::fhe_int160_bitxor,
          TfheHeader::fhe_int160_not,
          TfheHeader::fhe_int160_bitand_assign, TfheHeader::fhe_int160_bitor_assign, TfheHeader::fhe_int160_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int160_eq,     TfheHeader::fhe_int160_ne,
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int160_lt,          TfheHeader::fhe_int160_le,
          TfheHeader::fhe_int160_gt,          TfheHeader::fhe_int160_ge,
          TfheHeader::fhe_int160_min,         TfheHeader::fhe_int160_max,
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int160_add,          TfheHeader::fhe_int160_sub,        TfheHeader::fhe_int160_mul,
          TfheHeader::fhe_int160_div,          TfheHeader::fhe_int160_rem,
          TfheHeader::fhe_int160_overflowing_add, TfheHeader::fhe_int160_overflowing_sub, TfheHeader::fhe_int160_overflowing_mul,
          TfheHeader::fhe_int160_div_rem,
          TfheHeader::fhe_int160_add_assign,   TfheHeader::fhe_int160_sub_assign,  TfheHeader::fhe_int160_mul_assign,
          TfheHeader::fhe_int160_div_assign,   TfheHeader::fhe_int160_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_int160_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_int160_neg,
          TfheHeader::fhe_int160_abs,          // non-null: signed
          TfheHeader::fhe_int160_ilog2,
          TfheHeader::fhe_int160_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int160_shl,           TfheHeader::fhe_int160_shr,
          TfheHeader::fhe_int160_rotate_left,   TfheHeader::fhe_int160_rotate_right,
          TfheHeader::fhe_int160_shl_assign,    TfheHeader::fhe_int160_shr_assign,
          TfheHeader::fhe_int160_rotate_left_assign, TfheHeader::fhe_int160_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_int160_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_int160_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_int160_leading_ones,  TfheHeader::fhe_int160_leading_zeros,
          TfheHeader::fhe_int160_trailing_ones, TfheHeader::fhe_int160_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int160,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int160,
          TfheHeader::fhe_int160_if_then_else));

  FheInt160() { super(TfheHeader::fhe_int160_destroy); }

  @Override protected FheTypeHandles<I256> handles()      { return HANDLES; }
  @Override protected FheInt160             newInstance()   { return new FheInt160(); }
  @Override protected CompressedFheInt160   newCompressed() { return new CompressedFheInt160(); }

  public static FheInt160 encrypt(I256 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt160::new);
  }
  public static FheInt160 encrypt(I256 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt160::new);
  }
  public static FheInt160 encrypt(I256 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt160::new);
  }
  public static FheInt160 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt160::new);
  }
  public static FheInt160 ifThenElse(FheBool condition, FheInt160 thenValue, FheInt160 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt160::new);
  }

}
