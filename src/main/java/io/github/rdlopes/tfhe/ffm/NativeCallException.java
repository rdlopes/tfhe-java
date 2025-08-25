package io.github.rdlopes.tfhe.ffm;

public class NativeCallException extends RuntimeException {
  public NativeCallException(int result, String nativeErrorMessage) {
    super("Native call failed with result: " + result + " and message: " + nativeErrorMessage);
  }
}
