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
public class FheInt2Array extends NativeArray implements FheArray<FheInt2, FheInt2Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt2Array.class);
// @formatter:on

  public FheInt2Array(Collection<FheInt2> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt2Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt2> elements = values.stream()
                                          .map(value -> FheInt2.encrypt(value, clientKey))
                                          .toList();
    return new FheInt2Array(elements);
  }

  public static FheInt2Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt2> elements = values.stream()
                                          .map(value -> FheInt2.encrypt(value, publicKey))
                                          .toList();
    return new FheInt2Array(elements);
  }

  public static FheInt2Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt2> elements = values.stream()
                                          .map(FheInt2::encrypt)
                                          .toList();
    return new FheInt2Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt2Array other){
    List<FheInt2> thisElements = this.getElements();
    List<FheInt2> otherElements = other.getElements();
    List<FheUint2> thisUnsigned = thisElements.stream().map(FheInt2::castIntoFheUint2).toList();
    List<FheUint2> otherUnsigned = otherElements.stream().map(FheInt2::castIntoFheUint2).toList();
    FheUint2Array thisArray = new FheUint2Array(thisUnsigned);
    FheUint2Array otherArray = new FheUint2Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint2::destroy);
    otherUnsigned.forEach(FheUint2::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt2Array other){
    List<FheInt2> thisElements = this.getElements();
    List<FheInt2> otherElements = other.getElements();
    List<FheUint2> thisUnsigned = thisElements.stream().map(FheInt2::castIntoFheUint2).toList();
    List<FheUint2> otherUnsigned = otherElements.stream().map(FheInt2::castIntoFheUint2).toList();
    FheUint2Array thisArray = new FheUint2Array(thisUnsigned);
    FheUint2Array otherArray = new FheUint2Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint2::destroy);
    otherUnsigned.forEach(FheUint2::destroy);
    return result;
  }

  @Override
  public FheInt2 sum(){
    FheInt2 result = new FheInt2();
    execute(() -> fhe_int2_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt2Array add(FheInt2Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt2> thisElements = this.getElements();
    List<FheInt2> otherElements = other.getElements();
    List<FheInt2> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt2Array(result);
  }

  @Override
  public FheInt2Array subtract(FheInt2Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt2> thisElements = this.getElements();
    List<FheInt2> otherElements = other.getElements();
    List<FheInt2> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt2Array(result);
  }
}
