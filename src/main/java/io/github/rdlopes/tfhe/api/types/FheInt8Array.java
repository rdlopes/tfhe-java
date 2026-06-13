package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt8Array extends NativeArray implements FheArray<FheInt8, FheInt8Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt8Array.class);
// @formatter:on

  public FheInt8Array(Collection<FheInt8> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt8Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt8> elements = values.stream()
                                          .map(value -> FheInt8.encrypt(value, clientKey))
                                          .toList();
    return new FheInt8Array(elements);
  }

  public static FheInt8Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt8> elements = values.stream()
                                          .map(value -> FheInt8.encrypt(value, publicKey))
                                          .toList();
    return new FheInt8Array(elements);
  }

  public static FheInt8Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt8> elements = values.stream()
                                          .map(FheInt8::encrypt)
                                          .toList();
    return new FheInt8Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt8Array other){
    List<FheInt8> thisElements = this.getElements();
    List<FheInt8> otherElements = other.getElements();
    List<FheUint8> thisUnsigned = thisElements.stream().map(FheInt8::castIntoFheUint8).toList();
    List<FheUint8> otherUnsigned = otherElements.stream().map(FheInt8::castIntoFheUint8).toList();
    FheUint8Array thisArray = new FheUint8Array(thisUnsigned);
    FheUint8Array otherArray = new FheUint8Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint8::destroy);
    otherUnsigned.forEach(FheUint8::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt8Array other){
    List<FheInt8> thisElements = this.getElements();
    List<FheInt8> otherElements = other.getElements();
    List<FheUint8> thisUnsigned = thisElements.stream().map(FheInt8::castIntoFheUint8).toList();
    List<FheUint8> otherUnsigned = otherElements.stream().map(FheInt8::castIntoFheUint8).toList();
    FheUint8Array thisArray = new FheUint8Array(thisUnsigned);
    FheUint8Array otherArray = new FheUint8Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint8::destroy);
    otherUnsigned.forEach(FheUint8::destroy);
    return result;
  }

  @Override
  public FheInt8 sum(){
    FheInt8 result = new FheInt8();
    execute(() -> fhe_int8_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt8Array add(FheInt8Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt8> thisElements = this.getElements();
    List<FheInt8> otherElements = other.getElements();
    List<FheInt8> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt8Array(result);
  }

  @Override
  public FheInt8Array subtract(FheInt8Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt8> thisElements = this.getElements();
    List<FheInt8> otherElements = other.getElements();
    List<FheInt8> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt8Array(result);
  }
}
