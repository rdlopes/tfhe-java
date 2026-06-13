package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.I128;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt128Array extends NativeArray implements FheArray<FheInt128, FheInt128Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt128Array.class);
// @formatter:on

  public FheInt128Array(Collection<FheInt128> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt128Array encrypt(Collection<I128> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt128> elements = values.stream()
                                            .map(value -> FheInt128.encrypt(value, clientKey))
                                            .toList();
    return new FheInt128Array(elements);
  }

  public static FheInt128Array encrypt(Collection<I128> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt128> elements = values.stream()
                                            .map(value -> FheInt128.encrypt(value, publicKey))
                                            .toList();
    return new FheInt128Array(elements);
  }

  public static FheInt128Array encrypt(Collection<I128> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt128> elements = values.stream()
                                            .map(FheInt128::encrypt)
                                            .toList();
    return new FheInt128Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt128Array other){
    List<FheInt128> thisElements = this.getElements();
    List<FheInt128> otherElements = other.getElements();
    List<FheUint128> thisUnsigned = thisElements.stream().map(FheInt128::castIntoFheUint128).toList();
    List<FheUint128> otherUnsigned = otherElements.stream().map(FheInt128::castIntoFheUint128).toList();
    FheUint128Array thisArray = new FheUint128Array(thisUnsigned);
    FheUint128Array otherArray = new FheUint128Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint128::destroy);
    otherUnsigned.forEach(FheUint128::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt128Array other){
    List<FheInt128> thisElements = this.getElements();
    List<FheInt128> otherElements = other.getElements();
    List<FheUint128> thisUnsigned = thisElements.stream().map(FheInt128::castIntoFheUint128).toList();
    List<FheUint128> otherUnsigned = otherElements.stream().map(FheInt128::castIntoFheUint128).toList();
    FheUint128Array thisArray = new FheUint128Array(thisUnsigned);
    FheUint128Array otherArray = new FheUint128Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint128::destroy);
    otherUnsigned.forEach(FheUint128::destroy);
    return result;
  }

  @Override
  public FheInt128 sum(){
    FheInt128 result = new FheInt128();
    execute(() -> fhe_int128_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt128Array add(FheInt128Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt128> thisElements = this.getElements();
    List<FheInt128> otherElements = other.getElements();
    List<FheInt128> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt128Array(result);
  }

  @Override
  public FheInt128Array subtract(FheInt128Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt128> thisElements = this.getElements();
    List<FheInt128> otherElements = other.getElements();
    List<FheInt128> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt128Array(result);
  }
}
