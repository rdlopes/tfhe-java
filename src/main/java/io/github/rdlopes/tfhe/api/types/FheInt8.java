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

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

/**
 * Encrypted signed 8-bit integer ({@code int8_t}).
 *
 * <p>All FHE operations are implemented in {@link AbstractFheType}. This class
 * supplies only the static {@link #HANDLES} metadata record and type-specific
 * factory methods. The entire business logic is ~25 lines.
 */
public final class FheInt8 extends AbstractFheType<Byte, FheInt8, CompressedFheInt8>
    implements FheInteger<Byte, FheInt8, CompressedFheInt8> {

  static {
    FheRegistry.registerFactory(FheInt8.class, FheInt8::new);
  }

  // ── Static metadata — resolved once at class-loading via invokedynamic ──────

  static final FheTypeHandles<Byte> HANDLES = new FheTypeHandles<>(
      FheValueKind.BYTE,

      new FheTypeHandles.Lifecycle(
          TfheHeader::fhe_int8_destroy,
          TfheHeader::fhe_int8_clone,
          TfheHeader::fhe_int8_compress,
          TfheHeader::fhe_int8_safe_serialize,
          TfheHeader::fhe_int8_safe_deserialize_conformant),

      new FheTypeHandles.Encryption<>(
          TfheHeader::fhe_int8_try_encrypt_with_client_key_i8,  // (byte clear, key, result**)
          TfheHeader::fhe_int8_try_encrypt_with_public_key_i8,
          TfheHeader::fhe_int8_try_encrypt_trivial_i8,          // (byte clear, result**)
          null, null, null,                                      // wide ops — N/A for Byte
          TfheHeader::fhe_int8_decrypt,
          null),                                                 // decryptWide — N/A

      new FheTypeHandles.Logic<>(
          TfheHeader::fhe_int8_bitand,           TfheHeader::fhe_int8_bitor,          TfheHeader::fhe_int8_bitxor,
          TfheHeader::fhe_int8_not,
          TfheHeader::fhe_int8_bitand_assign,    TfheHeader::fhe_int8_bitor_assign,   TfheHeader::fhe_int8_bitxor_assign,
          TfheHeader::fhe_int8_scalar_bitand,    TfheHeader::fhe_int8_scalar_bitor,   TfheHeader::fhe_int8_scalar_bitxor,
          TfheHeader::fhe_int8_scalar_bitand_assign, TfheHeader::fhe_int8_scalar_bitor_assign, TfheHeader::fhe_int8_scalar_bitxor_assign),

      new FheTypeHandles.Equality<>(
          TfheHeader::fhe_int8_eq,               TfheHeader::fhe_int8_ne,
          TfheHeader::fhe_int8_scalar_eq,        TfheHeader::fhe_int8_scalar_ne),

      new FheTypeHandles.Comparison<>(
          TfheHeader::fhe_int8_lt,               TfheHeader::fhe_int8_le,
          TfheHeader::fhe_int8_gt,               TfheHeader::fhe_int8_ge,
          TfheHeader::fhe_int8_min,              TfheHeader::fhe_int8_max,
          TfheHeader::fhe_int8_scalar_lt,        TfheHeader::fhe_int8_scalar_le,
          TfheHeader::fhe_int8_scalar_gt,        TfheHeader::fhe_int8_scalar_ge,
          TfheHeader::fhe_int8_scalar_min,       TfheHeader::fhe_int8_scalar_max),

      new FheTypeHandles.Arithmetic<>(
          TfheHeader::fhe_int8_add,              TfheHeader::fhe_int8_sub,            TfheHeader::fhe_int8_mul,
          TfheHeader::fhe_int8_div,              TfheHeader::fhe_int8_rem,
          TfheHeader::fhe_int8_overflowing_add,  TfheHeader::fhe_int8_overflowing_sub, TfheHeader::fhe_int8_overflowing_mul,
          TfheHeader::fhe_int8_div_rem,
          TfheHeader::fhe_int8_add_assign,       TfheHeader::fhe_int8_sub_assign,     TfheHeader::fhe_int8_mul_assign,
          TfheHeader::fhe_int8_div_assign,       TfheHeader::fhe_int8_rem_assign,
          TfheHeader::fhe_int8_scalar_add,       TfheHeader::fhe_int8_scalar_sub,     TfheHeader::fhe_int8_scalar_mul,
          TfheHeader::fhe_int8_scalar_div,       TfheHeader::fhe_int8_scalar_rem,
          TfheHeader::fhe_int8_scalar_add_assign, TfheHeader::fhe_int8_scalar_sub_assign, TfheHeader::fhe_int8_scalar_mul_assign,
          TfheHeader::fhe_int8_scalar_div_assign, TfheHeader::fhe_int8_scalar_rem_assign,
          TfheHeader::fhe_int8_scalar_div_rem,
          TfheHeader::fhe_int8_neg,
          TfheHeader::fhe_int8_abs,              // non-null: signed type
          TfheHeader::fhe_int8_ilog2,
          TfheHeader::fhe_int8_checked_ilog2),

      new FheTypeHandles.Bitwise<>(
          TfheHeader::fhe_int8_shl,              TfheHeader::fhe_int8_shr,
          TfheHeader::fhe_int8_rotate_left,      TfheHeader::fhe_int8_rotate_right,
          TfheHeader::fhe_int8_shl_assign,       TfheHeader::fhe_int8_shr_assign,
          TfheHeader::fhe_int8_rotate_left_assign, TfheHeader::fhe_int8_rotate_right_assign,
          TfheHeader::fhe_int8_scalar_shl,       TfheHeader::fhe_int8_scalar_shr,
          TfheHeader::fhe_int8_scalar_rotate_left, TfheHeader::fhe_int8_scalar_rotate_right,
          TfheHeader::fhe_int8_scalar_shl_assign, TfheHeader::fhe_int8_scalar_shr_assign,
          TfheHeader::fhe_int8_scalar_rotate_left_assign, TfheHeader::fhe_int8_scalar_rotate_right_assign,
          TfheHeader::fhe_int8_leading_ones,     TfheHeader::fhe_int8_leading_zeros,
          TfheHeader::fhe_int8_trailing_ones,    TfheHeader::fhe_int8_trailing_zeros),

      new FheTypeHandles.Misc(
          TfheHeader::generate_oblivious_pseudo_random_fhe_int8,
          TfheHeader::generate_oblivious_pseudo_random_bounded_fhe_int8,
          TfheHeader::fhe_int8_if_then_else));

  // ── Constructor (package-private — use static factories) ─────────────────────

  FheInt8() {
    super(TfheHeader::fhe_int8_destroy);
  }

  // ── Subclass metadata ─────────────────────────────────────────────────────────

  @Override protected FheTypeHandles<Byte> handles()      { return HANDLES; }
  @Override protected FheInt8             newInstance()   { return new FheInt8(); }
  @Override protected CompressedFheInt8   newCompressed() { return new CompressedFheInt8(); }

  // ── Static factories ──────────────────────────────────────────────────────────

  public static FheInt8 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(HANDLES, clearValue, clientKey, FheInt8::new);
  }

  public static FheInt8 encrypt(Byte clearValue, PublicKey publicKey) {
    return encryptPublicKey(HANDLES, clearValue, publicKey, FheInt8::new);
  }

  public static FheInt8 encrypt(Byte clearValue) {
    return encryptTrivial(HANDLES, clearValue, FheInt8::new);
  }

  public static FheInt8 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(HANDLES, buffer, serverKey, FheInt8::new);
  }

  public static FheInt8 ifThenElse(FheBool condition, FheInt8 thenValue, FheInt8 elseValue) {
    return ifThenElse(HANDLES, condition, thenValue, elseValue, FheInt8::new);
  }


}
