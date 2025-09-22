package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheUnsignedInteger;
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
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeAndReturn;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheUint16 extends NativePointer
implements FheUnsignedInteger<Short, FheUint16, CompressedFheUint16> {
  private static final Logger logger = LoggerFactory.getLogger(FheUint16.class);
// @formatter:on

  /**
   {@snippet lang = "c":
    *
    *ptr can be null (no-op in that case)
    *
     int fhe_uint16_destroy(struct FheUint16 *ptr);
     }
   */
  FheUint16() {
    logger.trace("init");
    super(TfheHeader::fhe_uint16_destroy);
  }
  
/**
{@snippet lang = "c":
 *
 * Serializes safely.
 *
 * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
 * versions of TFHE-rs.
 *
 * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
 *    (to avoid out of memory attacks)
 *
  int fhe_uint16_safe_serialize(const struct FheUint16 *sself,
  struct DynamicBuffer *result,
  uint64_t serialized_size_limit);
  }
 */
@Override
public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> fhe_uint16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

  return dynamicBuffer;

}/**
{@snippet lang = "c":
int fhe_uint16_bitand(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheUint16 **result);
  }
   */
  @Override
  public FheUint16 bitAnd(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_bitand(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 bitAndScalar(Short other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_scalar_bitand(getValue(), other, result.getAddress()));
    return result;

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_bitand_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
     }
   */
  @Override
  public void bitAndAssign(FheUint16 other){
    execute(() -> fhe_uint16_bitand_assign(getValue(), other.getValue()));

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_bitand_assign(struct FheUint16 *lhs, uint16_t rhs);
  }
 */
@Override
public void bitAndScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_bitand_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_bitor(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 bitOr(FheUint16 other){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_bitor(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 bitOrScalar(Short other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_scalar_bitor(getValue(), other, result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_bitor_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
     }
   */
  @Override
  public void bitOrAssign(FheUint16 other) {
    execute(() -> fhe_uint16_bitor_assign(getValue(), other.getValue()));

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_bitor_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void bitOrScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_bitor_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_bitxor(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 bitXor(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_bitxor(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 bitXorScalar(Short other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_scalar_bitxor(getValue(), other, result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_bitxor_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
     }
   */
  @Override
  public void bitXorAssign(FheUint16 other) {
    execute(() -> fhe_uint16_bitxor_assign(getValue(), other.getValue()));

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_bitxor_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void bitXorScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_bitxor_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_not(const struct FheUint16 *input, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 bitNot() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_not(getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_if_then_else(const struct FheBool *condition_ct,
  const struct FheUint16 *then_ct,
  const struct FheUint16 *else_ct,
  struct FheUint16 **result);
  }
 */
@SuppressWarnings("unused")
public static FheUint16 ifThenElse(FheBool condition, FheUint16 thenValue, FheUint16 elseValue) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_if_then_else(condition.getValue(), thenValue.getValue(), elseValue.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_eq(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheBool **result);
     }
   */
  @Override
  public FheBool equalTo(FheUint16 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint16_eq(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_eq(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool equalToScalar(Short other){
    FheBool result = new FheBool();
      execute(() -> fhe_uint16_scalar_eq(getValue(), other, result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_ne(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheBool **result);
     }
   */
  @Override
  public FheBool notEqualTo(FheUint16 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint16_ne(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_ne(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool notEqualToScalar(Short other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_scalar_ne(getValue(), other, result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
    *
    * Deserializes safely, and checks that the resulting ciphertext
    * is in compliance with the shape of ciphertext that the `server_key` expects.
    *
    * This function can only deserialize types which have been serialized
    * by a `safe_serialize` function.
    *
    * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
    *    (to avoid out of memory attacks)
    * - `server_key`: ServerKey used in the conformance check
    * - `result`: pointer where resulting deserialized object needs to be stored.
    *    * cannot be NULL
    *    * (*result) will point the deserialized object on success, else NULL
    *
     int fhe_uint16_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
     uint64_t serialized_size_limit,
     const struct ServerKey *server_key,
     struct FheUint16 **result);
     }
   */
  public static FheUint16 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    FheUint16 deserialized = new FheUint16();
    execute(() -> fhe_uint16_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

}/**
{@snippet lang = "c":
  int fhe_uint16_try_encrypt_with_client_key_u16(uint16_t value,
  const struct ClientKey *client_key,
  struct FheUint16 **result);
  }
   */
  public static FheUint16 encrypt(Short clearValue, ClientKey clientKey) {
    FheUint16 encrypted = new FheUint16();
      execute(() -> fhe_uint16_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

}  
/**
{@snippet lang = "c":
  int fhe_uint16_try_encrypt_with_public_key_u16(uint16_t value,
  const struct PublicKey *public_key,
  struct FheUint16 **result);
  }
 */
public static FheUint16 encrypt(Short clearValue, PublicKey publicKey) {
  FheUint16 encrypted = new FheUint16();
      execute(() -> fhe_uint16_try_encrypt_with_public_key_u16(clearValue, publicKey.getValue(), encrypted.getAddress()));
  return encrypted;

}/**
{@snippet lang = "c":
  int fhe_uint16_try_encrypt_trivial_u16(uint16_t value, struct FheUint16 **result);
  }
   */
  public static FheUint16 encrypt(Short clearValue) {
    FheUint16 encrypted = new FheUint16();
      execute(() -> fhe_uint16_try_encrypt_trivial_u16(clearValue, encrypted.getAddress()));
    return encrypted;

}/**
{@snippet lang = "c":
  int fhe_uint16_clone(const struct FheUint16 *sself, struct FheUint16 **result);
  }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheUint16 clone() {
    FheUint16 cloned = new FheUint16();
    execute(() -> fhe_uint16_clone(getValue(), cloned.getAddress()));
    return cloned;

}  
/**
{@snippet lang = "c":
int fhe_uint16_compress(const struct FheUint16 *sself, struct CompressedFheUint16 **result);
}
*/
@Override
public CompressedFheUint16 compress(){
    CompressedFheUint16 compressed = new CompressedFheUint16();
    execute(() -> fhe_uint16_compress(getValue(), compressed.getAddress()));
    return compressed;

}/**
{@snippet lang = "c":
int fhe_uint16_add(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheUint16 **result);
  }
   */
  @Override
public FheUint16 add(FheUint16 other){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_add(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_overflowing_add(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheUint16 **out_result,
  struct FheBool **out_overflowed);
}
*/
@Override
public Map.Entry<FheUint16, FheBool> addWithOverflow(FheUint16 other){
    FheUint16 result = new FheUint16();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint16_overflowing_add(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return Map.entry(result, overflow);

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_add(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 addScalar(Short other) {
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_add(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_add_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void addAssign(FheUint16 other) {
  execute(() -> fhe_uint16_add_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_add_assign(struct FheUint16 *lhs, uint16_t rhs);
     }
   */
  @Override
  public void addScalarAssign(Short other) {
    execute(() -> fhe_uint16_scalar_add_assign(getValue(), other));

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_sub(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 subtract(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_sub(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
  int fhe_uint16_overflowing_sub(const struct FheUint16 *lhs,
                               const struct FheUint16 *rhs,
                               struct FheUint16 **out_result,
                               struct FheBool **out_overflowed);
}
*/
@Override
public Map.Entry<FheUint16, FheBool> subtractWithOverflow(FheUint16 other){
    FheUint16 result = new FheUint16();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint16_overflowing_sub(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
  return Map.entry(result, overflow);

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_sub(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 subtractScalar(Short other) {
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_sub(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_sub_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void subtractAssign(FheUint16 other) {
  execute(() -> fhe_uint16_sub_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_sub_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void subtractScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_sub_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_mul(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 multiply(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_mul(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
  int fhe_uint16_overflowing_mul(const struct FheUint16 *lhs,
                               const struct FheUint16 *rhs,
                               struct FheUint16 **out_result,
                               struct FheBool **out_overflowed);
}
*/
@Override
public Map.Entry<FheUint16, FheBool> multiplyWithOverflow(FheUint16 other){
    FheUint16 result = new FheUint16();
    FheBool overflow = new FheBool();
    execute(() -> fhe_uint16_overflowing_mul(getValue(), other.getValue(), result.getAddress(), overflow.getAddress()));
    return Map.entry(result, overflow);

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_mul(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 multiplyScalar(Short other) {
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_mul(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_mul_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void multiplyAssign(FheUint16 other) {
  execute(() -> fhe_uint16_mul_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_mul_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void multiplyScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_mul_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_div(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 divide(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_div(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_div(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 divideScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_div(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_div_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void divideAssign(FheUint16 other) {
  execute(() -> fhe_uint16_div_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_div_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void divideScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_div_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_rem(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 remainder(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_rem(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_rem(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 remainderScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_rem(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_rem_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void remainderAssign(FheUint16 other) {
  execute(() -> fhe_uint16_rem_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_scalar_rem_assign(struct FheUint16 *lhs, uint16_t rhs);
}
*/
@Override
public void remainderScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_rem_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_div_rem(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **q_result,
     struct FheUint16 **r_result);
     }
   */
  @Override
  public Map.Entry<FheUint16, FheUint16> divideWithRemainder(FheUint16 other) {
    FheUint16 divider = new FheUint16();
    FheUint16 remainder = new FheUint16();
    execute(() -> fhe_uint16_div_rem(getValue(), other.getValue(), divider.getAddress(), remainder.getAddress()));
    return Map.entry(divider, remainder);

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_div_rem(const struct FheUint16 *lhs,
                              uint16_t rhs,
                              struct FheUint16 **q_result,
                              struct FheUint16 **r_result);
}
*/
@Override
public Map.Entry<FheUint16,FheUint16> divideWithRemainderScalar(Short other){
    FheUint16 divider = new FheUint16();
    FheUint16 remainder = new FheUint16();
      execute(() -> fhe_uint16_scalar_div_rem(getValue(), other, divider.getAddress(), remainder.getAddress()));
  return Map.entry(divider, remainder);

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_neg(const struct FheUint16 *input, struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 negate() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_neg(getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
 *
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * Result has no meaning if self encrypts a value that is <= 0.
 * See `checked_ilog2`
 *
  int fhe_uint16_ilog2(const struct FheUint16 *input, struct FheUint32 **result);
  }
 */
@Override
public FheUint16 ilog2() {
  FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_ilog2(getValue(), result.getAddress()));
    return result;

}/**
{@snippet lang = "c":
  int fhe_uint16_lt(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheBool **result);
  }
   */
  @Override
  public FheBool lessThan(FheUint16 other) {
    FheBool result = new FheBool();
    execute(() -> fhe_uint16_lt(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_lt(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool lessThanScalar(Short other){
    FheBool result = new FheBool();
      execute(() -> fhe_uint16_scalar_lt(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_le(const struct FheUint16 *lhs,
                  const struct FheUint16 *rhs,
  struct FheBool **result);
  }
 */
@Override
public FheBool lessThanOrEqualTo(FheUint16 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_le(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_le(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool lessThanOrEqualToScalar(Short other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_scalar_le(getValue(), other, result.getAddress()));
  return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_gt(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheBool **result);
  }
 */
@Override
public FheBool greaterThan(FheUint16 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_gt(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_gt(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool greaterThanScalar(Short other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_scalar_gt(getValue(), other, result.getAddress()));
  return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_ge(const struct FheUint16 *lhs,
                  const struct FheUint16 *rhs,
  struct FheBool **result);
  }
 */
@Override
public FheBool greaterThanOrEqualTo(FheUint16 other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_ge(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_ge(const struct FheUint16 *lhs, uint16_t rhs, struct FheBool **result);
}
*/
@Override
public FheBool greaterThanOrEqualToScalar(Short other) {
  FheBool result = new FheBool();
  execute(() -> fhe_uint16_scalar_ge(getValue(), other, result.getAddress()));
  return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_min(const struct FheUint16 *lhs,
  const struct FheUint16 *rhs,
  struct FheUint16 **result);
  }
 */
@Override
public FheUint16 min(FheUint16 other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_min(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_min(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 minScalar(Short other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_scalar_min(getValue(), other, result.getAddress()));
  return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_max(const struct FheUint16 *lhs,
                   const struct FheUint16 *rhs,
  struct FheUint16 **result);
  }
 */
@Override
public FheUint16 max(FheUint16 other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_max(getValue(), other.getValue(), result.getAddress()));
  return result;

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_max(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 maxScalar(Short other) {
  FheUint16 result = new FheUint16();
  execute(() -> fhe_uint16_scalar_max(getValue(), other, result.getAddress()));
  return result;

}/**
{@snippet lang = "c":
int fhe_uint16_shl(const struct FheUint16 *lhs,
                   const struct FheUint16 *rhs,
  struct FheUint16 **result);
  }
   */
  @Override
  public FheUint16 shiftLeft(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_shl(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_shl(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 shiftLeftScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_shl(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_shl_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void shiftLeftAssign(FheUint16 other) {
  execute(() -> fhe_uint16_shl_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_shl_assign(struct FheUint16 *lhs, uint16_t rhs);
     }
   */
  @Override
  public void shiftLeftScalarAssign(Short other) {
    execute(() -> fhe_uint16_scalar_shl_assign(getValue(), other));

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_shr(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 shiftRight(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_shr(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_shr(const struct FheUint16 *lhs, uint16_t rhs, struct FheUint16 **result);
}
*/
@Override
public FheUint16 shiftRightScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_shr(getValue(), other, result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_shr_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
}
*/
@Override
public void shiftRightAssign(FheUint16 other) {
  execute(() -> fhe_uint16_shr_assign(getValue(), other.getValue()));

}

  /**
   {@snippet lang = "c":
int fhe_uint16_scalar_shr_assign(struct FheUint16 *lhs, uint16_t rhs);
     }
   */
  @Override
  public void shiftRightScalarAssign(Short other) {
    execute(() -> fhe_uint16_scalar_shr_assign(getValue(), other));

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_rotate_left(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 rotateLeft(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_rotate_left(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_rotate_left(const struct FheUint16 *lhs,
                                  uint16_t rhs,
                                  struct FheUint16 **result);
}
*/
@Override
public FheUint16 rotateLeftScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_rotate_left(getValue(), other, result.getAddress()));
    return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_rotate_left_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
     }
   */
  @Override
  public void rotateLeftAssign(FheUint16 other) {
    execute(() -> fhe_uint16_rotate_left_assign(getValue(), other.getValue()));

}  
/**
{@snippet lang = "c":
  int fhe_uint16_scalar_rotate_left_assign(struct FheUint16 *lhs, uint16_t rhs);
  }
 */
@Override
public void rotateLeftScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_rotate_left_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_rotate_right(const struct FheUint16 *lhs,
     const struct FheUint16 *rhs,
     struct FheUint16 **result);
     }
   */
  @Override
  public FheUint16 rotateRight(FheUint16 other) {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_rotate_right(getValue(), other.getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
int fhe_uint16_scalar_rotate_right(const struct FheUint16 *lhs,
                                   uint16_t rhs,
                                   struct FheUint16 **result);
}
*/
@Override
public FheUint16 rotateRightScalar(Short other){
    FheUint16 result = new FheUint16();
      execute(() -> fhe_uint16_scalar_rotate_right(getValue(), other, result.getAddress()));
    return result;

}

  /**
   {@snippet lang = "c":
     int fhe_uint16_rotate_right_assign(struct FheUint16 *lhs, const struct FheUint16 *rhs);
     }
   */
  @Override
  public void rotateRightAssign(FheUint16 other) {
    execute(() -> fhe_uint16_rotate_right_assign(getValue(), other.getValue()));

}  
/**
{@snippet lang = "c":
  int fhe_uint16_scalar_rotate_right_assign(struct FheUint16 *lhs, uint16_t rhs);
  }
 */
@Override
public void rotateRightScalarAssign(Short other) {
  execute(() -> fhe_uint16_scalar_rotate_right_assign(getValue(), other));

}

  /**
   {@snippet lang = "c":
    *
    * Returns the number of leading ones in the binary representation of input.
    *
     int fhe_uint16_leading_ones(const struct FheUint16 *input, struct FheUint32 **result);
     }
   */
  @Override
  public FheUint16 leadingOnes() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_leading_ones(getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
 *
 * Returns the number of leading zeros in the binary representation of input.
 *
int fhe_uint16_leading_zeros(const struct FheUint16 *input, struct FheUint32 **result);
}
*/
@Override
public FheUint16 leadingZeros(){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_leading_zeros(getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
 *
 * Returns the number of trailing ones in the binary representation of input.
 *
int fhe_uint16_trailing_ones(const struct FheUint16 *input, struct FheUint32 **result);
}
*/
@Override
public FheUint16 trailingOnes(){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_trailing_ones(getValue(), result.getAddress()));
    return result;

}  
/**
{@snippet lang = "c":
 *
 * Returns the number of trailing zeros in the binary representation of input.
 *
int fhe_uint16_trailing_zeros(const struct FheUint16 *input, struct FheUint32 **result);
  }
 */
@Override
public FheUint16 trailingZeros(){
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_trailing_zeros(getValue(), result.getAddress()));
    return result;

}/**
{@snippet lang = "c":
int fhe_uint16_decrypt(const struct FheUint16 *encrypted_value,
  const struct ClientKey *client_key,
  uint16_t *result);
  }
   */
  @Override
  public Short decrypt(ClientKey clientKey) {
    return executeAndReturn(Short.class, address -> fhe_uint16_decrypt(getValue(), clientKey.getValue(), address));

  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int10(const struct FheUint16 *sself, struct FheInt10 **result);
     }
   */
  public FheInt10 castIntoFheInt10() {
    FheInt10 result = new FheInt10();
    execute(() -> fhe_uint16_cast_into_fhe_int10(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int1024(const struct FheUint16 *sself, struct FheInt1024 **result);
     }
   */
  public FheInt1024 castIntoFheInt1024() {
  FheInt1024 result = new FheInt1024();
  execute(() -> fhe_uint16_cast_into_fhe_int1024(getValue(), result.getAddress()));
  return result;
}

/**
{@snippet lang = "c":
int fhe_uint16_cast_into_fhe_int104(const struct FheUint16 *sself, struct FheInt104 **result);
}
*/
public FheInt104 castIntoFheInt104() {
  FheInt104 result = new FheInt104();
  execute(() -> fhe_uint16_cast_into_fhe_int104(getValue(), result.getAddress()));
  return result;
}

/**
{@snippet lang = "c":
  int fhe_uint16_cast_into_fhe_int112(const struct FheUint16 *sself, struct FheInt112 **result);
  }
 */
public FheInt112 castIntoFheInt112() {
  FheInt112 result = new FheInt112();
  execute(() -> fhe_uint16_cast_into_fhe_int112(getValue(), result.getAddress()));
  return result;
}

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int12(const struct FheUint16 *sself, struct FheInt12 **result);
     }
   */
  public FheInt12 castIntoFheInt12() {
    FheInt12 result = new FheInt12();
    execute(() -> fhe_uint16_cast_into_fhe_int12(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int120(const struct FheUint16 *sself, struct FheInt120 **result);
     }
   */
  public FheInt120 castIntoFheInt120() {
    FheInt120 result = new FheInt120();
    execute(() -> fhe_uint16_cast_into_fhe_int120(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int128(const struct FheUint16 *sself, struct FheInt128 **result);
     }
   */
  public FheInt128 castIntoFheInt128() {
    FheInt128 result = new FheInt128();
    execute(() -> fhe_uint16_cast_into_fhe_int128(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int136(const struct FheUint16 *sself, struct FheInt136 **result);
     }
   */
  public FheInt136 castIntoFheInt136() {
    FheInt136 result = new FheInt136();
    execute(() -> fhe_uint16_cast_into_fhe_int136(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int14(const struct FheUint16 *sself, struct FheInt14 **result);
     }
   */
  public FheInt14 castIntoFheInt14() {
    FheInt14 result = new FheInt14();
    execute(() -> fhe_uint16_cast_into_fhe_int14(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int144(const struct FheUint16 *sself, struct FheInt144 **result);
     }
   */
  public FheInt144 castIntoFheInt144() {
    FheInt144 result = new FheInt144();
    execute(() -> fhe_uint16_cast_into_fhe_int144(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int152(const struct FheUint16 *sself, struct FheInt152 **result);
     }
   */
  public FheInt152 castIntoFheInt152() {
    FheInt152 result = new FheInt152();
    execute(() -> fhe_uint16_cast_into_fhe_int152(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int16(const struct FheUint16 *sself, struct FheInt16 **result);
     }
   */
  public FheInt16 castIntoFheInt16() {
    FheInt16 result = new FheInt16();
    execute(() -> fhe_uint16_cast_into_fhe_int16(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int160(const struct FheUint16 *sself, struct FheInt160 **result);
     }
   */
  public FheInt160 castIntoFheInt160() {
    FheInt160 result = new FheInt160();
    execute(() -> fhe_uint16_cast_into_fhe_int160(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int168(const struct FheUint16 *sself, struct FheInt168 **result);
     }
   */
  public FheInt168 castIntoFheInt168() {
    FheInt168 result = new FheInt168();
    execute(() -> fhe_uint16_cast_into_fhe_int168(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int176(const struct FheUint16 *sself, struct FheInt176 **result);
     }
   */
  public FheInt176 castIntoFheInt176() {
    FheInt176 result = new FheInt176();
    execute(() -> fhe_uint16_cast_into_fhe_int176(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int184(const struct FheUint16 *sself, struct FheInt184 **result);
     }
   */
  public FheInt184 castIntoFheInt184() {
    FheInt184 result = new FheInt184();
    execute(() -> fhe_uint16_cast_into_fhe_int184(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int192(const struct FheUint16 *sself, struct FheInt192 **result);
     }
   */
  public FheInt192 castIntoFheInt192() {
    FheInt192 result = new FheInt192();
    execute(() -> fhe_uint16_cast_into_fhe_int192(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int2(const struct FheUint16 *sself, struct FheInt2 **result);
     }
   */
  public FheInt2 castIntoFheInt2() {
    FheInt2 result = new FheInt2();
    execute(() -> fhe_uint16_cast_into_fhe_int2(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int200(const struct FheUint16 *sself, struct FheInt200 **result);
     }
   */
  public FheInt200 castIntoFheInt200() {
    FheInt200 result = new FheInt200();
    execute(() -> fhe_uint16_cast_into_fhe_int200(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int2048(const struct FheUint16 *sself, struct FheInt2048 **result);
     }
   */
  public FheInt2048 castIntoFheInt2048() {
    FheInt2048 result = new FheInt2048();
    execute(() -> fhe_uint16_cast_into_fhe_int2048(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int208(const struct FheUint16 *sself, struct FheInt208 **result);
     }
   */
  public FheInt208 castIntoFheInt208() {
    FheInt208 result = new FheInt208();
    execute(() -> fhe_uint16_cast_into_fhe_int208(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int216(const struct FheUint16 *sself, struct FheInt216 **result);
     }
   */
  public FheInt216 castIntoFheInt216() {
    FheInt216 result = new FheInt216();
    execute(() -> fhe_uint16_cast_into_fhe_int216(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int224(const struct FheUint16 *sself, struct FheInt224 **result);
     }
   */
  public FheInt224 castIntoFheInt224() {
    FheInt224 result = new FheInt224();
    execute(() -> fhe_uint16_cast_into_fhe_int224(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int232(const struct FheUint16 *sself, struct FheInt232 **result);
     }
   */
  public FheInt232 castIntoFheInt232() {
    FheInt232 result = new FheInt232();
    execute(() -> fhe_uint16_cast_into_fhe_int232(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int24(const struct FheUint16 *sself, struct FheInt24 **result);
     }
   */
  public FheInt24 castIntoFheInt24() {
    FheInt24 result = new FheInt24();
    execute(() -> fhe_uint16_cast_into_fhe_int24(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int240(const struct FheUint16 *sself, struct FheInt240 **result);
     }
   */
  public FheInt240 castIntoFheInt240() {
    FheInt240 result = new FheInt240();
    execute(() -> fhe_uint16_cast_into_fhe_int240(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int248(const struct FheUint16 *sself, struct FheInt248 **result);
     }
   */
  public FheInt248 castIntoFheInt248() {
    FheInt248 result = new FheInt248();
    execute(() -> fhe_uint16_cast_into_fhe_int248(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int256(const struct FheUint16 *sself, struct FheInt256 **result);
     }
   */
  public FheInt256 castIntoFheInt256() {
    FheInt256 result = new FheInt256();
    execute(() -> fhe_uint16_cast_into_fhe_int256(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int32(const struct FheUint16 *sself, struct FheInt32 **result);
     }
   */
  public FheInt32 castIntoFheInt32() {
    FheInt32 result = new FheInt32();
    execute(() -> fhe_uint16_cast_into_fhe_int32(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int4(const struct FheUint16 *sself, struct FheInt4 **result);
     }
   */
  public FheInt4 castIntoFheInt4() {
    FheInt4 result = new FheInt4();
    execute(() -> fhe_uint16_cast_into_fhe_int4(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int40(const struct FheUint16 *sself, struct FheInt40 **result);
     }
   */
  public FheInt40 castIntoFheInt40() {
    FheInt40 result = new FheInt40();
    execute(() -> fhe_uint16_cast_into_fhe_int40(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int48(const struct FheUint16 *sself, struct FheInt48 **result);
     }
   */
  public FheInt48 castIntoFheInt48() {
    FheInt48 result = new FheInt48();
    execute(() -> fhe_uint16_cast_into_fhe_int48(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int512(const struct FheUint16 *sself, struct FheInt512 **result);
     }
   */
  public FheInt512 castIntoFheInt512() {
    FheInt512 result = new FheInt512();
    execute(() -> fhe_uint16_cast_into_fhe_int512(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int56(const struct FheUint16 *sself, struct FheInt56 **result);
     }
   */
  public FheInt56 castIntoFheInt56() {
    FheInt56 result = new FheInt56();
    execute(() -> fhe_uint16_cast_into_fhe_int56(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int6(const struct FheUint16 *sself, struct FheInt6 **result);
     }
   */
  public FheInt6 castIntoFheInt6() {
    FheInt6 result = new FheInt6();
    execute(() -> fhe_uint16_cast_into_fhe_int6(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int64(const struct FheUint16 *sself, struct FheInt64 **result);
     }
   */
  public FheInt64 castIntoFheInt64() {
    FheInt64 result = new FheInt64();
    execute(() -> fhe_uint16_cast_into_fhe_int64(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int72(const struct FheUint16 *sself, struct FheInt72 **result);
     }
   */
  public FheInt72 castIntoFheInt72() {
    FheInt72 result = new FheInt72();
    execute(() -> fhe_uint16_cast_into_fhe_int72(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int8(const struct FheUint16 *sself, struct FheInt8 **result);
     }
   */
  public FheInt8 castIntoFheInt8() {
    FheInt8 result = new FheInt8();
    execute(() -> fhe_uint16_cast_into_fhe_int8(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int80(const struct FheUint16 *sself, struct FheInt80 **result);
     }
   */
  public FheInt80 castIntoFheInt80() {
    FheInt80 result = new FheInt80();
    execute(() -> fhe_uint16_cast_into_fhe_int80(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int88(const struct FheUint16 *sself, struct FheInt88 **result);
     }
   */
  public FheInt88 castIntoFheInt88() {
    FheInt88 result = new FheInt88();
    execute(() -> fhe_uint16_cast_into_fhe_int88(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_int96(const struct FheUint16 *sself, struct FheInt96 **result);
     }
   */
  public FheInt96 castIntoFheInt96() {
    FheInt96 result = new FheInt96();
    execute(() -> fhe_uint16_cast_into_fhe_int96(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint10(const struct FheUint16 *sself, struct FheUint10 **result);
     }
   */
  public FheUint10 castIntoFheUint10() {
    FheUint10 result = new FheUint10();
    execute(() -> fhe_uint16_cast_into_fhe_uint10(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint1024(const struct FheUint16 *sself, struct FheUint1024 **result);
     }
   */
  public FheUint1024 castIntoFheUint1024() {
    FheUint1024 result = new FheUint1024();
    execute(() -> fhe_uint16_cast_into_fhe_uint1024(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint104(const struct FheUint16 *sself, struct FheUint104 **result);
     }
   */
  public FheUint104 castIntoFheUint104() {
    FheUint104 result = new FheUint104();
    execute(() -> fhe_uint16_cast_into_fhe_uint104(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint112(const struct FheUint16 *sself, struct FheUint112 **result);
     }
   */
  public FheUint112 castIntoFheUint112() {
    FheUint112 result = new FheUint112();
    execute(() -> fhe_uint16_cast_into_fhe_uint112(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint12(const struct FheUint16 *sself, struct FheUint12 **result);
     }
   */
  public FheUint12 castIntoFheUint12() {
    FheUint12 result = new FheUint12();
    execute(() -> fhe_uint16_cast_into_fhe_uint12(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint120(const struct FheUint16 *sself, struct FheUint120 **result);
     }
   */
  public FheUint120 castIntoFheUint120() {
    FheUint120 result = new FheUint120();
    execute(() -> fhe_uint16_cast_into_fhe_uint120(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint128(const struct FheUint16 *sself, struct FheUint128 **result);
     }
   */
  public FheUint128 castIntoFheUint128() {
    FheUint128 result = new FheUint128();
    execute(() -> fhe_uint16_cast_into_fhe_uint128(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint136(const struct FheUint16 *sself, struct FheUint136 **result);
     }
   */
  public FheUint136 castIntoFheUint136() {
    FheUint136 result = new FheUint136();
    execute(() -> fhe_uint16_cast_into_fhe_uint136(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint14(const struct FheUint16 *sself, struct FheUint14 **result);
     }
   */
  public FheUint14 castIntoFheUint14() {
    FheUint14 result = new FheUint14();
    execute(() -> fhe_uint16_cast_into_fhe_uint14(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint144(const struct FheUint16 *sself, struct FheUint144 **result);
     }
   */
  public FheUint144 castIntoFheUint144() {
    FheUint144 result = new FheUint144();
    execute(() -> fhe_uint16_cast_into_fhe_uint144(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint152(const struct FheUint16 *sself, struct FheUint152 **result);
     }
   */
  public FheUint152 castIntoFheUint152() {
    FheUint152 result = new FheUint152();
    execute(() -> fhe_uint16_cast_into_fhe_uint152(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint16(const struct FheUint16 *sself, struct FheUint16 **result);
     }
   */
  public FheUint16 castIntoFheUint16() {
    FheUint16 result = new FheUint16();
    execute(() -> fhe_uint16_cast_into_fhe_uint16(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint160(const struct FheUint16 *sself, struct FheUint160 **result);
     }
   */
  public FheUint160 castIntoFheUint160() {
    FheUint160 result = new FheUint160();
    execute(() -> fhe_uint16_cast_into_fhe_uint160(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint168(const struct FheUint16 *sself, struct FheUint168 **result);
     }
   */
  public FheUint168 castIntoFheUint168() {
    FheUint168 result = new FheUint168();
    execute(() -> fhe_uint16_cast_into_fhe_uint168(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint176(const struct FheUint16 *sself, struct FheUint176 **result);
     }
   */
  public FheUint176 castIntoFheUint176() {
    FheUint176 result = new FheUint176();
    execute(() -> fhe_uint16_cast_into_fhe_uint176(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint184(const struct FheUint16 *sself, struct FheUint184 **result);
     }
   */
  public FheUint184 castIntoFheUint184() {
    FheUint184 result = new FheUint184();
    execute(() -> fhe_uint16_cast_into_fhe_uint184(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint192(const struct FheUint16 *sself, struct FheUint192 **result);
     }
   */
  public FheUint192 castIntoFheUint192() {
    FheUint192 result = new FheUint192();
    execute(() -> fhe_uint16_cast_into_fhe_uint192(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint2(const struct FheUint16 *sself, struct FheUint2 **result);
     }
   */
  public FheUint2 castIntoFheUint2() {
    FheUint2 result = new FheUint2();
    execute(() -> fhe_uint16_cast_into_fhe_uint2(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint200(const struct FheUint16 *sself, struct FheUint200 **result);
     }
   */
  public FheUint200 castIntoFheUint200() {
    FheUint200 result = new FheUint200();
    execute(() -> fhe_uint16_cast_into_fhe_uint200(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint2048(const struct FheUint16 *sself, struct FheUint2048 **result);
     }
   */
  public FheUint2048 castIntoFheUint2048() {
    FheUint2048 result = new FheUint2048();
    execute(() -> fhe_uint16_cast_into_fhe_uint2048(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint208(const struct FheUint16 *sself, struct FheUint208 **result);
     }
   */
  public FheUint208 castIntoFheUint208() {
    FheUint208 result = new FheUint208();
    execute(() -> fhe_uint16_cast_into_fhe_uint208(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint216(const struct FheUint16 *sself, struct FheUint216 **result);
     }
   */
  public FheUint216 castIntoFheUint216() {
    FheUint216 result = new FheUint216();
    execute(() -> fhe_uint16_cast_into_fhe_uint216(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint224(const struct FheUint16 *sself, struct FheUint224 **result);
     }
   */
  public FheUint224 castIntoFheUint224() {
    FheUint224 result = new FheUint224();
    execute(() -> fhe_uint16_cast_into_fhe_uint224(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint232(const struct FheUint16 *sself, struct FheUint232 **result);
     }
   */
  public FheUint232 castIntoFheUint232() {
    FheUint232 result = new FheUint232();
    execute(() -> fhe_uint16_cast_into_fhe_uint232(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint24(const struct FheUint16 *sself, struct FheUint24 **result);
     }
   */
  public FheUint24 castIntoFheUint24() {
    FheUint24 result = new FheUint24();
    execute(() -> fhe_uint16_cast_into_fhe_uint24(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint240(const struct FheUint16 *sself, struct FheUint240 **result);
     }
   */
  public FheUint240 castIntoFheUint240() {
    FheUint240 result = new FheUint240();
    execute(() -> fhe_uint16_cast_into_fhe_uint240(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint248(const struct FheUint16 *sself, struct FheUint248 **result);
     }
   */
  public FheUint248 castIntoFheUint248() {
    FheUint248 result = new FheUint248();
    execute(() -> fhe_uint16_cast_into_fhe_uint248(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint256(const struct FheUint16 *sself, struct FheUint256 **result);
     }
   */
  public FheUint256 castIntoFheUint256() {
    FheUint256 result = new FheUint256();
    execute(() -> fhe_uint16_cast_into_fhe_uint256(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint32(const struct FheUint16 *sself, struct FheUint32 **result);
     }
   */
  public FheUint32 castIntoFheUint32() {
    FheUint32 result = new FheUint32();
    execute(() -> fhe_uint16_cast_into_fhe_uint32(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint4(const struct FheUint16 *sself, struct FheUint4 **result);
     }
   */
  public FheUint4 castIntoFheUint4() {
    FheUint4 result = new FheUint4();
    execute(() -> fhe_uint16_cast_into_fhe_uint4(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint40(const struct FheUint16 *sself, struct FheUint40 **result);
     }
   */
  public FheUint40 castIntoFheUint40() {
    FheUint40 result = new FheUint40();
    execute(() -> fhe_uint16_cast_into_fhe_uint40(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint48(const struct FheUint16 *sself, struct FheUint48 **result);
     }
   */
  public FheUint48 castIntoFheUint48() {
    FheUint48 result = new FheUint48();
    execute(() -> fhe_uint16_cast_into_fhe_uint48(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint512(const struct FheUint16 *sself, struct FheUint512 **result);
     }
   */
  public FheUint512 castIntoFheUint512() {
    FheUint512 result = new FheUint512();
    execute(() -> fhe_uint16_cast_into_fhe_uint512(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint56(const struct FheUint16 *sself, struct FheUint56 **result);
     }
   */
  public FheUint56 castIntoFheUint56() {
    FheUint56 result = new FheUint56();
    execute(() -> fhe_uint16_cast_into_fhe_uint56(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint6(const struct FheUint16 *sself, struct FheUint6 **result);
     }
   */
  public FheUint6 castIntoFheUint6() {
    FheUint6 result = new FheUint6();
    execute(() -> fhe_uint16_cast_into_fhe_uint6(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint64(const struct FheUint16 *sself, struct FheUint64 **result);
     }
   */
  public FheUint64 castIntoFheUint64() {
    FheUint64 result = new FheUint64();
    execute(() -> fhe_uint16_cast_into_fhe_uint64(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint72(const struct FheUint16 *sself, struct FheUint72 **result);
     }
   */
  public FheUint72 castIntoFheUint72() {
    FheUint72 result = new FheUint72();
    execute(() -> fhe_uint16_cast_into_fhe_uint72(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint8(const struct FheUint16 *sself, struct FheUint8 **result);
     }
   */
  public FheUint8 castIntoFheUint8() {
    FheUint8 result = new FheUint8();
    execute(() -> fhe_uint16_cast_into_fhe_uint8(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint80(const struct FheUint16 *sself, struct FheUint80 **result);
     }
   */
  public FheUint80 castIntoFheUint80() {
    FheUint80 result = new FheUint80();
    execute(() -> fhe_uint16_cast_into_fhe_uint80(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint88(const struct FheUint16 *sself, struct FheUint88 **result);
     }
   */
  public FheUint88 castIntoFheUint88() {
    FheUint88 result = new FheUint88();
    execute(() -> fhe_uint16_cast_into_fhe_uint88(getValue(), result.getAddress()));
    return result;
  }

  /**
   {@snippet lang = "c":
     int fhe_uint16_cast_into_fhe_uint96(const struct FheUint16 *sself, struct FheUint96 **result);
     }
   */
  public FheUint96 castIntoFheUint96() {
    FheUint96 result = new FheUint96();
    execute(() -> fhe_uint16_cast_into_fhe_uint96(getValue(), result.getAddress()));
    return result;
  }

}
