package io.github.rdlopes.tfhe.ffm;

public class NativeCallException extends RuntimeException {

  public NativeCallException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public NativeCallException(int result, String nativeErrorMessage) {
    this("Native call failed with result: " + result + " and message: " + nativeErrorMessage, null);
  }

  public NativeCallException(Throwable throwable) {
    this(null, throwable);
  }
}
