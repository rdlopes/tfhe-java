package io.github.rdlopes.tfhe.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record JextractSymbols(Map<Type, Collection<String>> includes, Map<String, String> definitions) {
  public boolean isEmpty() {
    return ObjectUtils.isEmpty(includes);
  }

  public Collection<String> functions() {
    return includes.getOrDefault(Type.function, Collections.emptyList());
  }

  public Collection<String> constants() {
    return includes.getOrDefault(Type.constant, Collections.emptyList());
  }

  public Collection<String> structs() {
    return includes.getOrDefault(Type.struct, Collections.emptyList());
  }

  public enum Type {
    function, constant, struct, union, typedef, var
  }

}
