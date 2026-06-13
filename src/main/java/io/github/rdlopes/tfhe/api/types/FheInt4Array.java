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
public class FheInt4Array extends NativeArray implements FheArray<FheInt4, FheInt4Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt4Array.class);
// @formatter:on

  public FheInt4Array(Collection<FheInt4> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt4Array encrypt(Collection<Byte> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt4> elements = values.stream()
                                          .map(value -> FheInt4.encrypt(value, clientKey))
                                          .toList();
    return new FheInt4Array(elements);
  }

  public static FheInt4Array encrypt(Collection<Byte> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt4> elements = values.stream()
                                          .map(value -> FheInt4.encrypt(value, publicKey))
                                          .toList();
    return new FheInt4Array(elements);
  }

  public static FheInt4Array encrypt(Collection<Byte> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt4> elements = values.stream()
                                          .map(FheInt4::encrypt)
                                          .toList();
    return new FheInt4Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt4Array other){
    List<FheInt4> thisElements = this.getElements();
    List<FheInt4> otherElements = other.getElements();
    List<FheUint4> thisUnsigned = thisElements.stream().map(FheInt4::castIntoFheUint4).toList();
    List<FheUint4> otherUnsigned = otherElements.stream().map(FheInt4::castIntoFheUint4).toList();
    FheUint4Array thisArray = new FheUint4Array(thisUnsigned);
    FheUint4Array otherArray = new FheUint4Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint4::destroy);
    otherUnsigned.forEach(FheUint4::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt4Array other){
    List<FheInt4> thisElements = this.getElements();
    List<FheInt4> otherElements = other.getElements();
    List<FheUint4> thisUnsigned = thisElements.stream().map(FheInt4::castIntoFheUint4).toList();
    List<FheUint4> otherUnsigned = otherElements.stream().map(FheInt4::castIntoFheUint4).toList();
    FheUint4Array thisArray = new FheUint4Array(thisUnsigned);
    FheUint4Array otherArray = new FheUint4Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint4::destroy);
    otherUnsigned.forEach(FheUint4::destroy);
    return result;
  }

  @Override
  public FheInt4 sum(){
    FheInt4 result = new FheInt4();
    execute(() -> fhe_int4_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt4Array add(FheInt4Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt4> thisElements = this.getElements();
    List<FheInt4> otherElements = other.getElements();
    List<FheInt4> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt4Array(result);
  }

  @Override
  public FheInt4Array subtract(FheInt4Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt4> thisElements = this.getElements();
    List<FheInt4> otherElements = other.getElements();
    List<FheInt4> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt4Array(result);
  }
}
