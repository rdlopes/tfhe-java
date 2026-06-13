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
public class FheInt16Array extends NativeArray implements FheArray<FheInt16, FheInt16Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt16Array.class);
// @formatter:on

  public FheInt16Array(Collection<FheInt16> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt16Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt16> elements = values.stream()
                                          .map(value -> FheInt16.encrypt(value, clientKey))
                                          .toList();
    return new FheInt16Array(elements);
  }

  public static FheInt16Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt16> elements = values.stream()
                                          .map(value -> FheInt16.encrypt(value, publicKey))
                                          .toList();
    return new FheInt16Array(elements);
  }

  public static FheInt16Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt16> elements = values.stream()
                                          .map(FheInt16::encrypt)
                                          .toList();
    return new FheInt16Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt16Array other){
    List<FheInt16> thisElements = this.getElements();
    List<FheInt16> otherElements = other.getElements();
    List<FheUint16> thisUnsigned = thisElements.stream().map(FheInt16::castIntoFheUint16).toList();
    List<FheUint16> otherUnsigned = otherElements.stream().map(FheInt16::castIntoFheUint16).toList();
    FheUint16Array thisArray = new FheUint16Array(thisUnsigned);
    FheUint16Array otherArray = new FheUint16Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint16::destroy);
    otherUnsigned.forEach(FheUint16::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt16Array other){
    List<FheInt16> thisElements = this.getElements();
    List<FheInt16> otherElements = other.getElements();
    List<FheUint16> thisUnsigned = thisElements.stream().map(FheInt16::castIntoFheUint16).toList();
    List<FheUint16> otherUnsigned = otherElements.stream().map(FheInt16::castIntoFheUint16).toList();
    FheUint16Array thisArray = new FheUint16Array(thisUnsigned);
    FheUint16Array otherArray = new FheUint16Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint16::destroy);
    otherUnsigned.forEach(FheUint16::destroy);
    return result;
  }

  @Override
  public FheInt16 sum(){
    FheInt16 result = new FheInt16();
    execute(() -> fhe_int16_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt16Array add(FheInt16Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt16> thisElements = this.getElements();
    List<FheInt16> otherElements = other.getElements();
    List<FheInt16> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt16Array(result);
  }

  @Override
  public FheInt16Array subtract(FheInt16Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt16> thisElements = this.getElements();
    List<FheInt16> otherElements = other.getElements();
    List<FheInt16> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt16Array(result);
  }
}
