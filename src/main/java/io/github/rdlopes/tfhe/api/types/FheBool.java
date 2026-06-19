package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.FheBoolean;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheOps;
import io.github.rdlopes.tfhe.ffm.NativeCallException;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.utils.FheRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.*;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
@SuppressWarnings({"java:S2975", "java:S1182"})
@Generated
public class FheBool extends NativePointer implements FheBoolean<FheBool, CompressedFheBool> {
  private static final Logger logger = LoggerFactory.getLogger(FheBool.class);
// @formatter:on

  private record Handles(
      FheOps.BinaryOp    bitAnd, FheOps.BinaryOp bitOr, FheOps.BinaryOp bitXor, FheOps.BinaryOp eq, FheOps.BinaryOp ne,
      FheOps.AssignOp    bitAndAssign, FheOps.AssignOp bitOrAssign, FheOps.AssignOp bitXorAssign,
      FheOps.ScalarOp<Boolean>       scalarBitAnd, FheOps.ScalarOp<Boolean> scalarBitOr, FheOps.ScalarOp<Boolean> scalarBitXor, FheOps.ScalarOp<Boolean> scalarEq, FheOps.ScalarOp<Boolean> scalarNe,
      FheOps.ScalarAssignOp<Boolean> scalarBitAndAssign, FheOps.ScalarAssignOp<Boolean> scalarBitOrAssign, FheOps.ScalarAssignOp<Boolean> scalarBitXorAssign,
      FheOps.UnaryOp     bitNot,
      FheOps.SerializeOp serialize,
      FheOps.UnaryOp     clone_,
      FheOps.UnaryOp     compress,
      FheOps.DecryptPrimitiveOp decryptPrimitive
  ) {}

  private static final Handles H = new Handles(
      TfheHeader::fhe_bool_bitand,  TfheHeader::fhe_bool_bitor,  TfheHeader::fhe_bool_bitxor,
      TfheHeader::fhe_bool_eq,      TfheHeader::fhe_bool_ne,
      TfheHeader::fhe_bool_bitand_assign, TfheHeader::fhe_bool_bitor_assign, TfheHeader::fhe_bool_bitxor_assign,
      TfheHeader::fhe_bool_scalar_bitand, TfheHeader::fhe_bool_scalar_bitor, TfheHeader::fhe_bool_scalar_bitxor,
      TfheHeader::fhe_bool_scalar_eq, TfheHeader::fhe_bool_scalar_ne,
      TfheHeader::fhe_bool_scalar_bitand_assign, TfheHeader::fhe_bool_scalar_bitor_assign, TfheHeader::fhe_bool_scalar_bitxor_assign,
      TfheHeader::fhe_bool_not,
      TfheHeader::fhe_bool_safe_serialize,
      TfheHeader::fhe_bool_clone,
      TfheHeader::fhe_bool_compress,
      TfheHeader::fhe_bool_decrypt
  );

  static {
    FheRegistry.registerFactory(FheBool.class, FheBool::new);
  }

  FheBool() {
    logger.trace("init");
    super(TfheHeader::fhe_bool_destroy);
  }

  /// Creates a new empty FheBool output slot. For use by classes in the `api` package.
  public static FheBool newEmpty() { return new FheBool(); }

  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> H.serialize.apply(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));
    return dynamicBuffer;
  }

  @Override
  public FheBool bitAnd(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> H.bitAnd.apply(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  @Override
  public FheBool bitAndScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> H.scalarBitAnd.apply(getValue(), other, result.getAddress()));
    return result;
  }

  @Override
  public void bitAndAssign(FheBool other) {
    execute(() -> H.bitAndAssign.apply(getValue(), other.getValue()));
  }

  @Override
  public void bitAndScalarAssign(Boolean other) {
    execute(() -> H.scalarBitAndAssign.apply(getValue(), other));
  }

  @Override
  public FheBool bitOr(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> H.bitOr.apply(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  @Override
  public FheBool bitOrScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> H.scalarBitOr.apply(getValue(), other, result.getAddress()));
    return result;
  }

  @Override
  public void bitOrAssign(FheBool other) {
    execute(() -> H.bitOrAssign.apply(getValue(), other.getValue()));
  }

  @Override
  public void bitOrScalarAssign(Boolean other) {
    execute(() -> H.scalarBitOrAssign.apply(getValue(), other));
  }

  @Override
  public FheBool bitXor(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> H.bitXor.apply(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  @Override
  public FheBool bitXorScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> H.scalarBitXor.apply(getValue(), other, result.getAddress()));
    return result;
  }

  @Override
  public void bitXorAssign(FheBool other) {
    execute(() -> H.bitXorAssign.apply(getValue(), other.getValue()));
  }

  @Override
  public void bitXorScalarAssign(Boolean other) {
    execute(() -> H.scalarBitXorAssign.apply(getValue(), other));
  }

  @Override
  public FheBool bitNot() {
    FheBool result = new FheBool();
    execute(() -> H.bitNot.apply(getValue(), result.getAddress()));
    return result;
  }

  public static FheBool ifThenElse(FheBool condition, FheBool thenValue, FheBool elseValue) {
    return condition.bitAnd(thenValue).bitOr(condition.bitNot().bitAnd(elseValue));
  }

  @Override
  public FheBool equalTo(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> H.eq.apply(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  @Override
  public FheBool equalToScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> H.scalarEq.apply(getValue(), other, result.getAddress()));
    return result;
  }

  @Override
  public FheBool notEqualTo(FheBool other) {
    FheBool result = new FheBool();
    execute(() -> H.ne.apply(getValue(), other.getValue(), result.getAddress()));
    return result;
  }

  @Override
  public FheBool notEqualToScalar(Boolean other) {
    FheBool result = new FheBool();
    execute(() -> H.scalarNe.apply(getValue(), other, result.getAddress()));
    return result;
  }

  public static FheBool deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    FheBool deserialized = new FheBool();
    execute(() -> fhe_bool_safe_deserialize_conformant(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;
  }

  public static FheBool encrypt(Boolean clearValue, ClientKey clientKey) {
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }

  public static FheBool encrypt(Boolean clearValue, PublicKey publicKey) {
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_with_public_key_bool(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }

  public static FheBool encrypt(Boolean clearValue) {
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_trivial_bool(clearValue, encrypted.getAddress()));
    return encrypted;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheBool clone() {
    FheBool cloned = new FheBool();
    execute(() -> H.clone_.apply(getValue(), cloned.getAddress()));
    return cloned;
  }

  @Override
  public Boolean decrypt(ClientKey clientKey) {
    return executeAndReturn(Boolean.class, address -> H.decryptPrimitive.apply(getValue(), clientKey.getValue(), address));
  }

  @Override
  public java.util.Optional<Boolean> tryDecryptTrivial() {
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment memorySegment = arena.allocate(C_BOOL);
      int status = fhe_bool_try_decrypt_trivial(getValue(), memorySegment);
      if (status != 0) {
        MemorySegment errorMessageAddress = tfhe_error_get_last();
        String errorMessage = errorMessageAddress.getString(0);
        if (!NO_ERROR_MESSAGE.equals(errorMessage)) {
          throw new NativeCallException(status, errorMessage);
        }
        return java.util.Optional.empty();
      }
      return java.util.Optional.of(memorySegment.get(C_BOOL, 0));
    }
  }

  @Override
  public CompressedFheBool compress() {
    CompressedFheBool compressed = new CompressedFheBool();
    execute(() -> H.compress.apply(getValue(), compressed.getAddress()));
    return compressed;
  }

  @SuppressWarnings("unchecked")
  public final <R extends NativePointer> R castInto(Class<R> targetClass) {
    FheOps.UnaryOp castOp = FheRegistry.getCastOp(this.getClass(), targetClass);
    if (castOp == null) {
      throw new UnsupportedOperationException("Casting from FheBool to " + targetClass.getSimpleName() + " is not supported.");
    }
    java.util.function.Supplier<R> factory = FheRegistry.getFactory(targetClass);
    R r = factory.get();
    execute(() -> castOp.apply(getValue(), r.getAddress()));
    return r;
  }

  // @formatter:off
}
// @formatter:on
