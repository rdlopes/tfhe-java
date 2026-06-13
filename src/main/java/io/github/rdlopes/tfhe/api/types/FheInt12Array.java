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
public class FheInt12Array extends NativeArray implements FheArray<FheInt12, FheInt12Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt12Array.class);
// @formatter:on

  public FheInt12Array(Collection<FheInt12> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt12Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt12> elements = values.stream()
                                          .map(value -> FheInt12.encrypt(value, clientKey))
                                          .toList();
    return new FheInt12Array(elements);
  }

  public static FheInt12Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt12> elements = values.stream()
                                          .map(value -> FheInt12.encrypt(value, publicKey))
                                          .toList();
    return new FheInt12Array(elements);
  }

  public static FheInt12Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt12> elements = values.stream()
                                          .map(FheInt12::encrypt)
                                          .toList();
    return new FheInt12Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt12Array other){
    List<FheInt12> thisElements = this.getElements();
    List<FheInt12> otherElements = other.getElements();
    List<FheUint12> thisUnsigned = thisElements.stream().map(FheInt12::castIntoFheUint12).toList();
    List<FheUint12> otherUnsigned = otherElements.stream().map(FheInt12::castIntoFheUint12).toList();
    FheUint12Array thisArray = new FheUint12Array(thisUnsigned);
    FheUint12Array otherArray = new FheUint12Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint12::destroy);
    otherUnsigned.forEach(FheUint12::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt12Array other){
    List<FheInt12> thisElements = this.getElements();
    List<FheInt12> otherElements = other.getElements();
    List<FheUint12> thisUnsigned = thisElements.stream().map(FheInt12::castIntoFheUint12).toList();
    List<FheUint12> otherUnsigned = otherElements.stream().map(FheInt12::castIntoFheUint12).toList();
    FheUint12Array thisArray = new FheUint12Array(thisUnsigned);
    FheUint12Array otherArray = new FheUint12Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint12::destroy);
    otherUnsigned.forEach(FheUint12::destroy);
    return result;
  }

  @Override
  public FheInt12 sum(){
    FheInt12 result = new FheInt12();
    execute(() -> fhe_int12_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt12Array add(FheInt12Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt12> thisElements = this.getElements();
    List<FheInt12> otherElements = other.getElements();
    List<FheInt12> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt12Array(result);
  }

  @Override
  public FheInt12Array subtract(FheInt12Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt12> thisElements = this.getElements();
    List<FheInt12> otherElements = other.getElements();
    List<FheInt12> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt12Array(result);
  }
}
