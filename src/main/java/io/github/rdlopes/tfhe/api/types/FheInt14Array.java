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
public class FheInt14Array extends NativeArray implements FheArray<FheInt14, FheInt14Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt14Array.class);
// @formatter:on

  public FheInt14Array(Collection<FheInt14> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt14Array encrypt(Collection<Short> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt14> elements = values.stream()
                                          .map(value -> FheInt14.encrypt(value, clientKey))
                                          .toList();
    return new FheInt14Array(elements);
  }

  public static FheInt14Array encrypt(Collection<Short> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt14> elements = values.stream()
                                          .map(value -> FheInt14.encrypt(value, publicKey))
                                          .toList();
    return new FheInt14Array(elements);
  }

  public static FheInt14Array encrypt(Collection<Short> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt14> elements = values.stream()
                                          .map(FheInt14::encrypt)
                                          .toList();
    return new FheInt14Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt14Array other){
    List<FheInt14> thisElements = this.getElements();
    List<FheInt14> otherElements = other.getElements();
    List<FheUint14> thisUnsigned = thisElements.stream().map(FheInt14::castIntoFheUint14).toList();
    List<FheUint14> otherUnsigned = otherElements.stream().map(FheInt14::castIntoFheUint14).toList();
    FheUint14Array thisArray = new FheUint14Array(thisUnsigned);
    FheUint14Array otherArray = new FheUint14Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint14::destroy);
    otherUnsigned.forEach(FheUint14::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt14Array other){
    List<FheInt14> thisElements = this.getElements();
    List<FheInt14> otherElements = other.getElements();
    List<FheUint14> thisUnsigned = thisElements.stream().map(FheInt14::castIntoFheUint14).toList();
    List<FheUint14> otherUnsigned = otherElements.stream().map(FheInt14::castIntoFheUint14).toList();
    FheUint14Array thisArray = new FheUint14Array(thisUnsigned);
    FheUint14Array otherArray = new FheUint14Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint14::destroy);
    otherUnsigned.forEach(FheUint14::destroy);
    return result;
  }

  @Override
  public FheInt14 sum(){
    FheInt14 result = new FheInt14();
    execute(() -> fhe_int14_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt14Array add(FheInt14Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt14> thisElements = this.getElements();
    List<FheInt14> otherElements = other.getElements();
    List<FheInt14> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt14Array(result);
  }

  @Override
  public FheInt14Array subtract(FheInt14Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt14> thisElements = this.getElements();
    List<FheInt14> otherElements = other.getElements();
    List<FheInt14> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt14Array(result);
  }
}
