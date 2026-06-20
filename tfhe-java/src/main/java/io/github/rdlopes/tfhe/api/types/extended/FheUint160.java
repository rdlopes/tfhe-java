package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractFheType;
import io.github.rdlopes.tfhe.api.AbstractFheUnsignedInteger;
import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U256;
import io.github.rdlopes.tfhe.ffm.FheTypeHandles;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;

/// Encrypted unsigned 160-bit integer (`uint160_t`).
///
/// All FHE operations are implemented in [AbstractFheType]. This class
/// supplies only the static [HANDLES] metadata record and type-specific
/// factory methods.
@Generated
public final class FheUint160 extends AbstractFheUnsignedInteger<U256, FheUint160, CompressedFheUint160>
    implements FheUnsignedInteger<U256, FheUint160, CompressedFheUint160> {

  static {
    FheRegistry.registerFactory(FheUint160.class, FheUint160::new);
  }

  static final FheTypeHandles<U256> HANDLES = new FheTypeHandles<>(
    new FheValueKind.Wide<>(U256::newEmpty),

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_uint160_destroy,
          TfheHeader::fhe_uint160_clone,
          TfheHeader::fhe_uint160_compress,
          TfheHeader::fhe_uint160_safe_serialize,
          TfheHeader::fhe_uint160_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          null, null, null,                                            // primitive ops — N/A for Wide
          TfheHeader::fhe_uint160_try_encrypt_with_client_key_u256,
          TfheHeader::fhe_uint160_try_encrypt_with_public_key_u256,
          TfheHeader::fhe_uint160_try_encrypt_trivial_u256,
          null,                                                        // decryptPrimitive — N/A
          TfheHeader::fhe_uint160_decrypt),

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_uint160_bitand,       TfheHeader::fhe_uint160_bitor,      TfheHeader::fhe_uint160_bitxor,
          TfheHeader::fhe_uint160_not,
          TfheHeader::fhe_uint160_bitand_assign, TfheHeader::fhe_uint160_bitor_assign, TfheHeader::fhe_uint160_bitxor_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_bitand(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_bitor(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_bitxor(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_bitand_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_bitor_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_bitxor_assign(lhs, v.getAddress())),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_uint160_eq,     TfheHeader::fhe_uint160_ne,
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_eq(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_ne(lhs, v.getAddress(), r)),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_uint160_lt,          TfheHeader::fhe_uint160_le,
          TfheHeader::fhe_uint160_gt,          TfheHeader::fhe_uint160_ge,
          TfheHeader::fhe_uint160_min,         TfheHeader::fhe_uint160_max,
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_lt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_le(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_gt(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_ge(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_min(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_max(lhs, v.getAddress(), r)),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_uint160_add,          TfheHeader::fhe_uint160_sub,        TfheHeader::fhe_uint160_mul,
          TfheHeader::fhe_uint160_div,          TfheHeader::fhe_uint160_rem,
          TfheHeader::fhe_uint160_overflowing_add, TfheHeader::fhe_uint160_overflowing_sub, TfheHeader::fhe_uint160_overflowing_mul,
          TfheHeader::fhe_uint160_div_rem,
          TfheHeader::fhe_uint160_add_assign,   TfheHeader::fhe_uint160_sub_assign,  TfheHeader::fhe_uint160_mul_assign,
          TfheHeader::fhe_uint160_div_assign,   TfheHeader::fhe_uint160_rem_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_add(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_sub(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_mul(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_div(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_rem(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_add_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_sub_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_mul_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_div_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_rem_assign(lhs, v.getAddress()),
          (lhs, v, r1, r2) -> TfheHeader.fhe_uint160_scalar_div_rem(lhs, v.getAddress(), r1, r2),
          TfheHeader::fhe_uint160_neg,
          null,                                // abs is null for unsigned
          TfheHeader::fhe_uint160_ilog2,
          TfheHeader::fhe_uint160_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_uint160_shl,           TfheHeader::fhe_uint160_shr,
          TfheHeader::fhe_uint160_rotate_left,   TfheHeader::fhe_uint160_rotate_right,
          TfheHeader::fhe_uint160_shl_assign,    TfheHeader::fhe_uint160_shr_assign,
          TfheHeader::fhe_uint160_rotate_left_assign, TfheHeader::fhe_uint160_rotate_right_assign,
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_shl(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_shr(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_rotate_left(lhs, v.getAddress(), r),
          (lhs, v, r) -> TfheHeader.fhe_uint160_scalar_rotate_right(lhs, v.getAddress(), r),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_shl_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_shr_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_rotate_left_assign(lhs, v.getAddress()),
          (lhs, v) -> TfheHeader.fhe_uint160_scalar_rotate_right_assign(lhs, v.getAddress()),
          TfheHeader::fhe_uint160_leading_ones,  TfheHeader::fhe_uint160_leading_zeros,
          TfheHeader::fhe_uint160_trailing_ones, TfheHeader::fhe_uint160_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_uint160,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_uint160,
          TfheHeader::fhe_uint160_if_then_else));

  FheUint160() { super(TfheHeader::fhe_uint160_destroy); }

  @Override protected FheTypeHandles<U256> handles()      { return HANDLES; }
  @Override protected FheUint160             newInstance()   { return new FheUint160(); }
  @Override protected CompressedFheUint160   newCompressed() { return new CompressedFheUint160(); }

  public static FheUint160 encrypt(U256 clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheUint160::new);
  }
  public static FheUint160 encrypt(U256 clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheUint160::new);
  }
  public static FheUint160 encrypt(U256 clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheUint160::new);
  }
  public static FheUint160 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheUint160::new);
  }
  public static FheUint160 ifThenElse(FheBool condition, FheUint160 thenValue, FheUint160 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheUint160::new);
  }

}
