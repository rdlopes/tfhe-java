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
public class FheInt10Array extends NativeArray implements FheArray<FheInt10, FheInt10Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt10Array.class);
// @formatter:on

  public FheInt10Array(Collection<FheInt10> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt10Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt10> elements = values.stream()
                                          .map(value -> FheInt10.encrypt(value, clientKey))
                                          .toList();
    return new FheInt10Array(elements);
  }

  public static FheInt10Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt10> elements = values.stream()
                                          .map(value -> FheInt10.encrypt(value, publicKey))
                                          .toList();
    return new FheInt10Array(elements);
  }

  public static FheInt10Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt10> elements = values.stream()
                                          .map(FheInt10::encrypt)
                                          .toList();
    return new FheInt10Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt10Array other){
    List<FheInt10> thisElements = this.getElements();
    List<FheInt10> otherElements = other.getElements();
    List<FheUint10> thisUnsigned = thisElements.stream().map(FheInt10::castIntoFheUint10).toList();
    List<FheUint10> otherUnsigned = otherElements.stream().map(FheInt10::castIntoFheUint10).toList();
    FheUint10Array thisArray = new FheUint10Array(thisUnsigned);
    FheUint10Array otherArray = new FheUint10Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint10::destroy);
    otherUnsigned.forEach(FheUint10::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt10Array other){
    List<FheInt10> thisElements = this.getElements();
    List<FheInt10> otherElements = other.getElements();
    List<FheUint10> thisUnsigned = thisElements.stream().map(FheInt10::castIntoFheUint10).toList();
    List<FheUint10> otherUnsigned = otherElements.stream().map(FheInt10::castIntoFheUint10).toList();
    FheUint10Array thisArray = new FheUint10Array(thisUnsigned);
    FheUint10Array otherArray = new FheUint10Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint10::destroy);
    otherUnsigned.forEach(FheUint10::destroy);
    return result;
  }

  @Override
  public FheInt10 sum(){
    FheInt10 result = new FheInt10();
    execute(() -> fhe_int10_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt10Array add(FheInt10Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt10> thisElements = this.getElements();
    List<FheInt10> otherElements = other.getElements();
    List<FheInt10> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt10Array(result);
  }

  @Override
  public FheInt10Array subtract(FheInt10Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt10> thisElements = this.getElements();
    List<FheInt10> otherElements = other.getElements();
    List<FheInt10> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt10Array(result);
  }
}
