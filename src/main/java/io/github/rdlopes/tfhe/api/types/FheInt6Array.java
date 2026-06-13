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
public class FheInt6Array extends NativeArray implements FheArray<FheInt6, FheInt6Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt6Array.class);
// @formatter:on

  public FheInt6Array(Collection<FheInt6> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt6Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt6> elements = values.stream()
                                          .map(value -> FheInt6.encrypt(value, clientKey))
                                          .toList();
    return new FheInt6Array(elements);
  }

  public static FheInt6Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt6> elements = values.stream()
                                          .map(value -> FheInt6.encrypt(value, publicKey))
                                          .toList();
    return new FheInt6Array(elements);
  }

  public static FheInt6Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt6> elements = values.stream()
                                          .map(FheInt6::encrypt)
                                          .toList();
    return new FheInt6Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt6Array other){
    List<FheInt6> thisElements = this.getElements();
    List<FheInt6> otherElements = other.getElements();
    List<FheUint6> thisUnsigned = thisElements.stream().map(FheInt6::castIntoFheUint6).toList();
    List<FheUint6> otherUnsigned = otherElements.stream().map(FheInt6::castIntoFheUint6).toList();
    FheUint6Array thisArray = new FheUint6Array(thisUnsigned);
    FheUint6Array otherArray = new FheUint6Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint6::destroy);
    otherUnsigned.forEach(FheUint6::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt6Array other){
    List<FheInt6> thisElements = this.getElements();
    List<FheInt6> otherElements = other.getElements();
    List<FheUint6> thisUnsigned = thisElements.stream().map(FheInt6::castIntoFheUint6).toList();
    List<FheUint6> otherUnsigned = otherElements.stream().map(FheInt6::castIntoFheUint6).toList();
    FheUint6Array thisArray = new FheUint6Array(thisUnsigned);
    FheUint6Array otherArray = new FheUint6Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint6::destroy);
    otherUnsigned.forEach(FheUint6::destroy);
    return result;
  }

  @Override
  public FheInt6 sum(){
    FheInt6 result = new FheInt6();
    execute(() -> fhe_int6_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt6Array add(FheInt6Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt6> thisElements = this.getElements();
    List<FheInt6> otherElements = other.getElements();
    List<FheInt6> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt6Array(result);
  }

  @Override
  public FheInt6Array subtract(FheInt6Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt6> thisElements = this.getElements();
    List<FheInt6> otherElements = other.getElements();
    List<FheInt6> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt6Array(result);
  }
}
