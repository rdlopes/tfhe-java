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
public class FheInt32Array extends NativeArray implements FheArray<FheInt32, FheInt32Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt32Array.class);
// @formatter:on

  public FheInt32Array(Collection<FheInt32> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt32Array encrypt(Collection<Integer> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt32> elements = values.stream()
                                          .map(value -> FheInt32.encrypt(value, clientKey))
                                          .toList();
    return new FheInt32Array(elements);
  }

  public static FheInt32Array encrypt(Collection<Integer> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt32> elements = values.stream()
                                          .map(value -> FheInt32.encrypt(value, publicKey))
                                          .toList();
    return new FheInt32Array(elements);
  }

  public static FheInt32Array encrypt(Collection<Integer> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt32> elements = values.stream()
                                          .map(FheInt32::encrypt)
                                          .toList();
    return new FheInt32Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt32Array other){
    List<FheInt32> thisElements = this.getElements();
    List<FheInt32> otherElements = other.getElements();
    List<FheUint32> thisUnsigned = thisElements.stream().map(FheInt32::castIntoFheUint32).toList();
    List<FheUint32> otherUnsigned = otherElements.stream().map(FheInt32::castIntoFheUint32).toList();
    FheUint32Array thisArray = new FheUint32Array(thisUnsigned);
    FheUint32Array otherArray = new FheUint32Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint32::destroy);
    otherUnsigned.forEach(FheUint32::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt32Array other){
    List<FheInt32> thisElements = this.getElements();
    List<FheInt32> otherElements = other.getElements();
    List<FheUint32> thisUnsigned = thisElements.stream().map(FheInt32::castIntoFheUint32).toList();
    List<FheUint32> otherUnsigned = otherElements.stream().map(FheInt32::castIntoFheUint32).toList();
    FheUint32Array thisArray = new FheUint32Array(thisUnsigned);
    FheUint32Array otherArray = new FheUint32Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint32::destroy);
    otherUnsigned.forEach(FheUint32::destroy);
    return result;
  }

  @Override
  public FheInt32 sum(){
    FheInt32 result = new FheInt32();
    execute(() -> fhe_int32_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt32Array add(FheInt32Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt32> thisElements = this.getElements();
    List<FheInt32> otherElements = other.getElements();
    List<FheInt32> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt32Array(result);
  }

  @Override
  public FheInt32Array subtract(FheInt32Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt32> thisElements = this.getElements();
    List<FheInt32> otherElements = other.getElements();
    List<FheInt32> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt32Array(result);
  }
}
