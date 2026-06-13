package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheArray;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.values.I256;
import io.github.rdlopes.tfhe.ffm.NativeArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class FheInt256Array extends NativeArray implements FheArray<FheInt256, FheInt256Array> {
  private static final Logger logger = LoggerFactory.getLogger(FheInt256Array.class);
// @formatter:on

  public FheInt256Array(Collection<FheInt256> elements) {
    logger.trace("init - elements: {}", elements);
    super(elements);
  }

  public static FheInt256Array encrypt(Collection<I256> values, ClientKey clientKey) {
    logger.trace("encrypt - values: {}, clientKey: {}", values, clientKey);
    Collection<FheInt256> elements = values.stream()
                                            .map(value -> FheInt256.encrypt(value, clientKey))
                                            .toList();
    return new FheInt256Array(elements);
  }

  public static FheInt256Array encrypt(Collection<I256> values, PublicKey publicKey) {
    logger.trace("encrypt - values: {}, publicKey: {}", values, publicKey);
    Collection<FheInt256> elements = values.stream()
                                            .map(value -> FheInt256.encrypt(value, publicKey))
                                            .toList();
    return new FheInt256Array(elements);
  }

  public static FheInt256Array encrypt(Collection<I256> values) {
    logger.trace("encrypt - values: {}", values);
    Collection<FheInt256> elements = values.stream()
                                            .map(FheInt256::encrypt)
                                            .toList();
    return new FheInt256Array(elements);
  }

  @Override
  public FheBool containsArray(FheInt256Array other){
    List<FheInt256> thisElements = this.getElements();
    List<FheInt256> otherElements = other.getElements();
    List<FheUint256> thisUnsigned = thisElements.stream().map(FheInt256::castIntoFheUint256).toList();
    List<FheUint256> otherUnsigned = otherElements.stream().map(FheInt256::castIntoFheUint256).toList();
    FheUint256Array thisArray = new FheUint256Array(thisUnsigned);
    FheUint256Array otherArray = new FheUint256Array(otherUnsigned);
    FheBool result = thisArray.containsArray(otherArray);
    thisUnsigned.forEach(FheUint256::destroy);
    otherUnsigned.forEach(FheUint256::destroy);
    return result;
  }

  @Override
  public FheBool equalsArray(FheInt256Array other){
    List<FheInt256> thisElements = this.getElements();
    List<FheInt256> otherElements = other.getElements();
    List<FheUint256> thisUnsigned = thisElements.stream().map(FheInt256::castIntoFheUint256).toList();
    List<FheUint256> otherUnsigned = otherElements.stream().map(FheInt256::castIntoFheUint256).toList();
    FheUint256Array thisArray = new FheUint256Array(thisUnsigned);
    FheUint256Array otherArray = new FheUint256Array(otherUnsigned);
    FheBool result = thisArray.equalsArray(otherArray);
    thisUnsigned.forEach(FheUint256::destroy);
    otherUnsigned.forEach(FheUint256::destroy);
    return result;
  }

  @Override
  public FheInt256 sum(){
    FheInt256 result = new FheInt256();
    execute(() -> fhe_int256_sum(getAddress(), getSize(), result.getAddress()));
    return result;
  }

  @Override
  public FheInt256Array add(FheInt256Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt256> thisElements = this.getElements();
    List<FheInt256> otherElements = other.getElements();
    List<FheInt256> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).add(otherElements.get(i)));
    }
    return new FheInt256Array(result);
  }

  @Override
  public FheInt256Array subtract(FheInt256Array other) {
    if (this.getSize() != other.getSize()) {
      throw new IllegalArgumentException("Array sizes must match");
    }
    List<FheInt256> thisElements = this.getElements();
    List<FheInt256> otherElements = other.getElements();
    List<FheInt256> result = new ArrayList<>();
    for (int i = 0; i < thisElements.size(); i++) {
      result.add(thisElements.get(i).subtract(otherElements.get(i)));
    }
    return new FheInt256Array(result);
  }
}
