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
public class FheInt64Array extends NativeArray implements FheArray<FheInt64, FheInt64Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt64Array.class);
// @formatter:on

  public FheInt64Array(Collection<FheInt64> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt64Array encrypt(Collection<Long> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt64> elements = values.stream()
                                          .map(value -> FheInt64.encrypt(value, clientKey))
                                          .toList();
    return new FheInt64Array(elements);
  }

  public static FheInt64Array encrypt(Collection<Long> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt64> elements = values.stream()
                                          .map(value -> FheInt64.encrypt(value, publicKey))
                                          .toList();
    return new FheInt64Array(elements);
  }

  public static FheInt64Array encrypt(Collection<Long> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt64> elements = values.stream()
                                          .map(FheInt64::encrypt)
                                          .toList();
    return new FheInt64Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt64Array other){
    List<FheInt64> thisElements = this.getElements();
    List<FheInt64> otherElements = other.getElements();
    List<FheUint64> thisUnsigned = thisElements.stream().map(FheInt64::castIntoFheUint64).toList();
    List<FheUint64> otherUnsigned = otherElements.stream().map(FheInt64::castIntoFheUint64).toList();
    FheUint64Array thisArray = new FheUint64Array(thisUnsigned);
    FheUint64Array otherArray = new FheUint64Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint64::destroy);
    otherUnsigned.forEach(FheUint64::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt64Array other){
    List<FheInt64> thisElements = this.getElements();
    List<FheInt64> otherElements = other.getElements();
    List<FheUint64> thisUnsigned = thisElements.stream().map(FheInt64::castIntoFheUint64).toList();
    List<FheUint64> otherUnsigned = otherElements.stream().map(FheInt64::castIntoFheUint64).toList();
    FheUint64Array thisArray = new FheUint64Array(thisUnsigned);
    FheUint64Array otherArray = new FheUint64Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint64::destroy);
    otherUnsigned.forEach(FheUint64::destroy);
    return result;
  }

  @Override
  public FheInt64 sum(){
    FheInt64 result = new FheInt64();
    execute(() -> fhe_int64_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt64Array add(FheInt64Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt64> thisElements = this.getElements();
    List<FheInt64> otherElements = other.getElements();
    List<FheInt64> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt64Array(result);
  }

  @Override
  public FheInt64Array subtract(FheInt64Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt64> thisElements = this.getElements();
    List<FheInt64> otherElements = other.getElements();
    List<FheInt64> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt64Array(result);
  }
}
