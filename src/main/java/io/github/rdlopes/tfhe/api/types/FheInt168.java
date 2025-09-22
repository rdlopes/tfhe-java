package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheInteger;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeWithAddress;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class FheInt168 extends NativePointer
  implements FheInteger<I256, FheInt168, CompressedFheInt168> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt168.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int fhe_int168_destroy(struct FheInt168 *ptr);
     }
   */
  FheInt168() {
    logger.trace("init");
    super(TfheHeader::fhe_int168_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static FheInt168 ifThenElse(FheBool condition, FheInt168 thenValue, FheInt168 elseValue) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static FheInt168 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    FheInt168 deserialized = new FheInt168();
    execute(() -> fhe_int168_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static FheInt168 encrypt(I256 clearValue, ClientKey clientKey) {
    FheInt168 encrypted = new FheInt168();
    execute(() -> fhe_int168_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static FheInt168 encrypt(I256 clearValue, PublicKey publicKey) {
    FheInt168 encrypted = new FheInt168();
    execute(() -> fhe_int168_try_encrypt_with_public_key_i256(clearValue.getAddress(), publicKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static FheInt168 encrypt(I256 clearValue) {
    FheInt168 encrypted = new FheInt168();
    execute(() -> fhe_int168_try_encrypt_trivial_i256(clearValue.getAddress(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_int168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitAnd(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitAndScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_bitand(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitAndAssign(FheInt168 other) {
    execute(() -> fhe_int168_bitand_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitAndScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_bitand_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitOr(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitOrScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_bitor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitOrAssign(FheInt168 other) {
    execute(() -> fhe_int168_bitor_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitOrScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_bitor_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitXor(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitXorScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_bitxor(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitXorAssign(FheInt168 other) {
    execute(() -> fhe_int168_bitxor_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void bitXorScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_bitxor_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 bitNot() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_not(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool equalTo(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool equalToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_eq(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool notEqualTo(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool notEqualToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_ne(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheInt168 clone() {
    FheInt168 cloned = new FheInt168();
    execute(() -> fhe_int168_clone(getValue(), cloned.getAddress()));
    return cloned;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public CompressedFheInt168 compress() {
    CompressedFheInt168 compressed = new CompressedFheInt168();
    execute(() -> fhe_int168_compress(getValue(), compressed.getAddress()));
    return compressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 add(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_add(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public Map.Entry<FheInt168, FheBool> addWithOverflow(FheInt168 other) {
    FheInt168 result = new FheInt168();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int168_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return Map.entry(result, overflow);

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 addScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_add(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void addAssign(FheInt168 other) {
    execute(() -> fhe_int168_add_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void addScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_add_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 subtract(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public Map.Entry<FheInt168, FheBool> subtractWithOverflow(FheInt168 other) {
    FheInt168 result = new FheInt168();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int168_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return Map.entry(result, overflow);

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 subtractScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_sub(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void subtractAssign(FheInt168 other) {
    execute(() -> fhe_int168_sub_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void subtractScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_sub_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 multiply(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public Map.Entry<FheInt168, FheBool> multiplyWithOverflow(FheInt168 other) {
    FheInt168 result = new FheInt168();
    FheBool overflow = new FheBool();
    execute(() -> fhe_int168_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return Map.entry(result, overflow);

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 multiplyScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_mul(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void multiplyAssign(FheInt168 other) {
    execute(() -> fhe_int168_mul_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void multiplyScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_mul_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 divide(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_div(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 divideScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_div(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void divideAssign(FheInt168 other) {
    execute(() -> fhe_int168_div_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void divideScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_div_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 remainder(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 remainderScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_rem(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void remainderAssign(FheInt168 other) {
    execute(() -> fhe_int168_rem_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void remainderScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_rem_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public Map.Entry<FheInt168, FheInt168> divideWithRemainder(FheInt168 other) {
    FheInt168 divider = new FheInt168();
    FheInt168 remainder = new FheInt168();
    execute(() -> fhe_int168_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return Map.entry(divider, remainder);

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public Map.Entry<FheInt168, FheInt168> divideWithRemainderScalar(I256 other) {
    FheInt168 divider = new FheInt168();
    FheInt168 remainder = new FheInt168();
    execute(() -> fhe_int168_scalar_div_rem(getValue(), other.getAddress(), divider.getAddress(), remainder.getAddress()));
    return Map.entry(divider, remainder);

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 negate() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_neg(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 ilog2() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_ilog2(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool lessThan(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool lessThanScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_lt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool lessThanOrEqualTo(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_le(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool lessThanOrEqualToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_le(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool greaterThan(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_gt(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool greaterThanScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_gt(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool greaterThanOrEqualTo(FheInt168 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_ge(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheBool greaterThanOrEqualToScalar(I256 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_int168_scalar_ge(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 min(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_min(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 minScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_min(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 max(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_max(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 maxScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_max(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 shiftLeft(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 shiftLeftScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_shl(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void shiftLeftAssign(FheInt168 other) {
    execute(() -> fhe_int168_shl_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void shiftLeftScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_shl_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 shiftRight(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 shiftRightScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_shr(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void shiftRightAssign(FheInt168 other) {
    execute(() -> fhe_int168_shr_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void shiftRightScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_shr_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 rotateLeft(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 rotateLeftScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_rotate_left(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void rotateLeftAssign(FheInt168 other) {
    execute(() -> fhe_int168_rotate_left_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void rotateLeftScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_rotate_left_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 rotateRight(FheInt168 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 rotateRightScalar(I256 other) {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_scalar_rotate_right(getValue(), other.getAddress(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void rotateRightAssign(FheInt168 other) {
    execute(() -> fhe_int168_rotate_right_assign(getValue(), other.getValue()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public void rotateRightScalarAssign(I256 other) {
    execute(() -> fhe_int168_scalar_rotate_right_assign(getValue(), other.getAddress()));

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 leadingOnes() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_leading_ones(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 leadingZeros() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_leading_zeros(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 trailingOnes() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_trailing_ones(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 trailingZeros() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_trailing_zeros(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public I256 decrypt(ClientKey clientKey) {
    I256 decrypted = new I256();
    executeWithAddress(decrypted.getAddress(), address -> fhe_int168_decrypt(getValue(), clientKey.getValue(), address));
    return decrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt168 abs() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_abs(getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int10(const struct FheInt168 *sself, struct FheInt10 **result);
     }
   */
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int168_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int1024(const struct FheInt168 *sself, struct FheInt1024 **result);
     }
   */
  public FheInt1024 castIntoFheInt1024() {
    FheInt1024 result = new FheInt1024();
    execute(() -> fhe_int168_cast_into_fhe_int1024(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int104(const struct FheInt168 *sself, struct FheInt104 **result);
     }
   */
  public FheInt104 castIntoFheInt104() {
    FheInt104 result = new FheInt104();
    execute(() -> fhe_int168_cast_into_fhe_int104(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int112(const struct FheInt168 *sself, struct FheInt112 **result);
     }
   */
  public FheInt112 castIntoFheInt112() {
    FheInt112 result = new FheInt112();
    execute(() -> fhe_int168_cast_into_fhe_int112(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int12(const struct FheInt168 *sself, struct FheInt12 **result);
     }
   */
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int168_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int120(const struct FheInt168 *sself, struct FheInt120 **result);
     }
   */
  public FheInt120 castIntoFheInt120() {
    FheInt120 result = new FheInt120();
    execute(() -> fhe_int168_cast_into_fhe_int120(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int128(const struct FheInt168 *sself, struct FheInt128 **result);
     }
   */
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int168_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int136(const struct FheInt168 *sself, struct FheInt136 **result);
     }
   */
  public FheInt136 castIntoFheInt136() {
    FheInt136 result = new FheInt136();
    execute(() -> fhe_int168_cast_into_fhe_int136(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int14(const struct FheInt168 *sself, struct FheInt14 **result);
     }
   */
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int168_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int144(const struct FheInt168 *sself, struct FheInt144 **result);
     }
   */
  public FheInt144 castIntoFheInt144() {
    FheInt144 result = new FheInt144();
    execute(() -> fhe_int168_cast_into_fhe_int144(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int152(const struct FheInt168 *sself, struct FheInt152 **result);
     }
   */
  public FheInt152 castIntoFheInt152() {
    FheInt152 result = new FheInt152();
    execute(() -> fhe_int168_cast_into_fhe_int152(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int16(const struct FheInt168 *sself, struct FheInt16 **result);
     }
   */
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int168_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int160(const struct FheInt168 *sself, struct FheInt160 **result);
     }
   */
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_int168_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int168(const struct FheInt168 *sself, struct FheInt168 **result);
     }
   */
  public FheInt168 castIntoFheInt168() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_int168_cast_into_fhe_int168(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int176(const struct FheInt168 *sself, struct FheInt176 **result);
     }
   */
  public FheInt176 castIntoFheInt176() {
    FheInt176 result = new FheInt176();
    execute(() -> fhe_int168_cast_into_fhe_int176(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int184(const struct FheInt168 *sself, struct FheInt184 **result);
     }
   */
  public FheInt184 castIntoFheInt184() {
    FheInt184 result = new FheInt184();
    execute(() -> fhe_int168_cast_into_fhe_int184(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int192(const struct FheInt168 *sself, struct FheInt192 **result);
     }
   */
  public FheInt192 castIntoFheInt192() {
    FheInt192 result = new FheInt192();
    execute(() -> fhe_int168_cast_into_fhe_int192(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int2(const struct FheInt168 *sself, struct FheInt2 **result);
     }
   */
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int168_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int200(const struct FheInt168 *sself, struct FheInt200 **result);
     }
   */
  public FheInt200 castIntoFheInt200() {
    FheInt200 result = new FheInt200();
    execute(() -> fhe_int168_cast_into_fhe_int200(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int2048(const struct FheInt168 *sself, struct FheInt2048 **result);
     }
   */
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_int168_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int208(const struct FheInt168 *sself, struct FheInt208 **result);
     }
   */
  public FheInt208 castIntoFheInt208() {
    FheInt208 result = new FheInt208();
    execute(() -> fhe_int168_cast_into_fhe_int208(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int216(const struct FheInt168 *sself, struct FheInt216 **result);
     }
   */
  public FheInt216 castIntoFheInt216() {
    FheInt216 result = new FheInt216();
    execute(() -> fhe_int168_cast_into_fhe_int216(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int224(const struct FheInt168 *sself, struct FheInt224 **result);
     }
   */
  public FheInt224 castIntoFheInt224() {
    FheInt224 result = new FheInt224();
    execute(() -> fhe_int168_cast_into_fhe_int224(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int232(const struct FheInt168 *sself, struct FheInt232 **result);
     }
   */
  public FheInt232 castIntoFheInt232() {
    FheInt232 result = new FheInt232();
    execute(() -> fhe_int168_cast_into_fhe_int232(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int24(const struct FheInt168 *sself, struct FheInt24 **result);
     }
   */
  public FheInt24 castIntoFheInt24() {
    FheInt24 result = new FheInt24();
    execute(() -> fhe_int168_cast_into_fhe_int24(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int240(const struct FheInt168 *sself, struct FheInt240 **result);
     }
   */
  public FheInt240 castIntoFheInt240() {
    FheInt240 result = new FheInt240();
    execute(() -> fhe_int168_cast_into_fhe_int240(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int248(const struct FheInt168 *sself, struct FheInt248 **result);
     }
   */
  public FheInt248 castIntoFheInt248() {
    FheInt248 result = new FheInt248();
    execute(() -> fhe_int168_cast_into_fhe_int248(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int256(const struct FheInt168 *sself, struct FheInt256 **result);
     }
   */
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int168_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int32(const struct FheInt168 *sself, struct FheInt32 **result);
     }
   */
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int168_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int4(const struct FheInt168 *sself, struct FheInt4 **result);
     }
   */
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int168_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int40(const struct FheInt168 *sself, struct FheInt40 **result);
     }
   */
  public FheInt40 castIntoFheInt40() {
    FheInt40 result = new FheInt40();
    execute(() -> fhe_int168_cast_into_fhe_int40(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int48(const struct FheInt168 *sself, struct FheInt48 **result);
     }
   */
  public FheInt48 castIntoFheInt48() {
    FheInt48 result = new FheInt48();
    execute(() -> fhe_int168_cast_into_fhe_int48(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int512(const struct FheInt168 *sself, struct FheInt512 **result);
     }
   */
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_int168_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int56(const struct FheInt168 *sself, struct FheInt56 **result);
     }
   */
  public FheInt56 castIntoFheInt56() {
    FheInt56 result = new FheInt56();
    execute(() -> fhe_int168_cast_into_fhe_int56(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int6(const struct FheInt168 *sself, struct FheInt6 **result);
     }
   */
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int168_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int64(const struct FheInt168 *sself, struct FheInt64 **result);
     }
   */
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int168_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int72(const struct FheInt168 *sself, struct FheInt72 **result);
     }
   */
  public FheInt72 castIntoFheInt72() {
    FheInt72 result = new FheInt72();
    execute(() -> fhe_int168_cast_into_fhe_int72(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int8(const struct FheInt168 *sself, struct FheInt8 **result);
     }
   */
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int168_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int80(const struct FheInt168 *sself, struct FheInt80 **result);
     }
   */
  public FheInt80 castIntoFheInt80() {
    FheInt80 result = new FheInt80();
    execute(() -> fhe_int168_cast_into_fhe_int80(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int88(const struct FheInt168 *sself, struct FheInt88 **result);
     }
   */
  public FheInt88 castIntoFheInt88() {
    FheInt88 result = new FheInt88();
    execute(() -> fhe_int168_cast_into_fhe_int88(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_int96(const struct FheInt168 *sself, struct FheInt96 **result);
     }
   */
  public FheInt96 castIntoFheInt96() {
    FheInt96 result = new FheInt96();
    execute(() -> fhe_int168_cast_into_fhe_int96(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint10(const struct FheInt168 *sself, struct FheUint10 **result);
     }
   */
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_int168_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint1024(const struct FheInt168 *sself, struct FheUint1024 **result);
     }
   */
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_int168_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint104(const struct FheInt168 *sself, struct FheUint104 **result);
     }
   */
  public FheUint104 castIntoFheUint104() {
    FheUint104 result = new FheUint104();
    execute(() -> fhe_int168_cast_into_fhe_uint104(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint112(const struct FheInt168 *sself, struct FheUint112 **result);
     }
   */
  public FheUint112 castIntoFheUint112() {
    FheUint112 result = new FheUint112();
    execute(() -> fhe_int168_cast_into_fhe_uint112(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint12(const struct FheInt168 *sself, struct FheUint12 **result);
     }
   */
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_int168_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint120(const struct FheInt168 *sself, struct FheUint120 **result);
     }
   */
  public FheUint120 castIntoFheUint120() {
    FheUint120 result = new FheUint120();
    execute(() -> fhe_int168_cast_into_fhe_uint120(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint128(const struct FheInt168 *sself, struct FheUint128 **result);
     }
   */
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_int168_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint136(const struct FheInt168 *sself, struct FheUint136 **result);
     }
   */
  public FheUint136 castIntoFheUint136() {
    FheUint136 result = new FheUint136();
    execute(() -> fhe_int168_cast_into_fhe_uint136(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint14(const struct FheInt168 *sself, struct FheUint14 **result);
     }
   */
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_int168_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint144(const struct FheInt168 *sself, struct FheUint144 **result);
     }
   */
  public FheUint144 castIntoFheUint144() {
    FheUint144 result = new FheUint144();
    execute(() -> fhe_int168_cast_into_fhe_uint144(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint152(const struct FheInt168 *sself, struct FheUint152 **result);
     }
   */
  public FheUint152 castIntoFheUint152() {
    FheUint152 result = new FheUint152();
    execute(() -> fhe_int168_cast_into_fhe_uint152(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint16(const struct FheInt168 *sself, struct FheUint16 **result);
     }
   */
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_int168_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint160(const struct FheInt168 *sself, struct FheUint160 **result);
     }
   */
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_int168_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint168(const struct FheInt168 *sself, struct FheUint168 **result);
     }
   */
  public FheUint168 castIntoFheUint168() {
    FheUint168 result = new FheUint168();
    execute(() -> fhe_int168_cast_into_fhe_uint168(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint176(const struct FheInt168 *sself, struct FheUint176 **result);
     }
   */
  public FheUint176 castIntoFheUint176() {
    FheUint176 result = new FheUint176();
    execute(() -> fhe_int168_cast_into_fhe_uint176(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint184(const struct FheInt168 *sself, struct FheUint184 **result);
     }
   */
  public FheUint184 castIntoFheUint184() {
    FheUint184 result = new FheUint184();
    execute(() -> fhe_int168_cast_into_fhe_uint184(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint192(const struct FheInt168 *sself, struct FheUint192 **result);
     }
   */
  public FheUint192 castIntoFheUint192() {
    FheUint192 result = new FheUint192();
    execute(() -> fhe_int168_cast_into_fhe_uint192(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint2(const struct FheInt168 *sself, struct FheUint2 **result);
     }
   */
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_int168_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint200(const struct FheInt168 *sself, struct FheUint200 **result);
     }
   */
  public FheUint200 castIntoFheUint200() {
    FheUint200 result = new FheUint200();
    execute(() -> fhe_int168_cast_into_fhe_uint200(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint2048(const struct FheInt168 *sself, struct FheUint2048 **result);
     }
   */
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_int168_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint208(const struct FheInt168 *sself, struct FheUint208 **result);
     }
   */
  public FheUint208 castIntoFheUint208() {
    FheUint208 result = new FheUint208();
    execute(() -> fhe_int168_cast_into_fhe_uint208(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint216(const struct FheInt168 *sself, struct FheUint216 **result);
     }
   */
  public FheUint216 castIntoFheUint216() {
    FheUint216 result = new FheUint216();
    execute(() -> fhe_int168_cast_into_fhe_uint216(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint224(const struct FheInt168 *sself, struct FheUint224 **result);
     }
   */
  public FheUint224 castIntoFheUint224() {
    FheUint224 result = new FheUint224();
    execute(() -> fhe_int168_cast_into_fhe_uint224(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint232(const struct FheInt168 *sself, struct FheUint232 **result);
     }
   */
  public FheUint232 castIntoFheUint232() {
    FheUint232 result = new FheUint232();
    execute(() -> fhe_int168_cast_into_fhe_uint232(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint24(const struct FheInt168 *sself, struct FheUint24 **result);
     }
   */
  public FheUint24 castIntoFheUint24() {
    FheUint24 result = new FheUint24();
    execute(() -> fhe_int168_cast_into_fhe_uint24(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint240(const struct FheInt168 *sself, struct FheUint240 **result);
     }
   */
  public FheUint240 castIntoFheUint240() {
    FheUint240 result = new FheUint240();
    execute(() -> fhe_int168_cast_into_fhe_uint240(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint248(const struct FheInt168 *sself, struct FheUint248 **result);
     }
   */
  public FheUint248 castIntoFheUint248() {
    FheUint248 result = new FheUint248();
    execute(() -> fhe_int168_cast_into_fhe_uint248(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint256(const struct FheInt168 *sself, struct FheUint256 **result);
     }
   */
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_int168_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint32(const struct FheInt168 *sself, struct FheUint32 **result);
     }
   */
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_int168_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint4(const struct FheInt168 *sself, struct FheUint4 **result);
     }
   */
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_int168_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint40(const struct FheInt168 *sself, struct FheUint40 **result);
     }
   */
  public FheUint40 castIntoFheUint40() {
    FheUint40 result = new FheUint40();
    execute(() -> fhe_int168_cast_into_fhe_uint40(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint48(const struct FheInt168 *sself, struct FheUint48 **result);
     }
   */
  public FheUint48 castIntoFheUint48() {
    FheUint48 result = new FheUint48();
    execute(() -> fhe_int168_cast_into_fhe_uint48(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint512(const struct FheInt168 *sself, struct FheUint512 **result);
     }
   */
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_int168_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint56(const struct FheInt168 *sself, struct FheUint56 **result);
     }
   */
  public FheUint56 castIntoFheUint56() {
    FheUint56 result = new FheUint56();
    execute(() -> fhe_int168_cast_into_fhe_uint56(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint6(const struct FheInt168 *sself, struct FheUint6 **result);
     }
   */
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
    execute(() -> fhe_int168_cast_into_fhe_uint6(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint64(const struct FheInt168 *sself, struct FheUint64 **result);
     }
   */
  public FheUint64 castIntoFheUint64() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_int168_cast_into_fhe_uint64(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint72(const struct FheInt168 *sself, struct FheUint72 **result);
     }
   */
  public FheUint72 castIntoFheUint72() {
    FheUint72 result = new FheUint72();
    execute(() -> fhe_int168_cast_into_fhe_uint72(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint8(const struct FheInt168 *sself, struct FheUint8 **result);
     }
   */
  public FheUint8 castIntoFheUint8() {
    FheUint8 result = new FheUint8();
    execute(() -> fhe_int168_cast_into_fhe_uint8(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint80(const struct FheInt168 *sself, struct FheUint80 **result);
     }
   */
  public FheUint80 castIntoFheUint80() {
    FheUint80 result = new FheUint80();
    execute(() -> fhe_int168_cast_into_fhe_uint80(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint88(const struct FheInt168 *sself, struct FheUint88 **result);
     }
   */
  public FheUint88 castIntoFheUint88() {
    FheUint88 result = new FheUint88();
    execute(() -> fhe_int168_cast_into_fhe_uint88(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_int168_cast_into_fhe_uint96(const struct FheInt168 *sself, struct FheUint96 **result);
     }
   */
  public FheUint96 castIntoFheUint96() {
    FheUint96 result = new FheUint96();
    execute(() -> fhe_int168_cast_into_fhe_uint96(getValue(), result.getAddress()));
    return result;
  }

}
