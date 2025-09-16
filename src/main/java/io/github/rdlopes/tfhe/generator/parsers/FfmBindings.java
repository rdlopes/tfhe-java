package io.github.rdlopes.tfhe.generator.parsers;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record FfmBindings(SortedSet<String> symbols) {

  public static FfmBindings from(String headerClassName) throws ClassNotFoundException {
    Class<?> headerClass = Class.forName(headerClassName);
    SortedSet<String> bindingsDefinitions = Arrays.stream(headerClass.getMethods())
                                                  .map(Method::getName)
                                                  .filter(name -> !name.contains("$"))
                                                  .collect(Collectors.toCollection(TreeSet::new));
    return new FfmBindings(bindingsDefinitions);
  }

  public Stream<String> filter(Predicate<String> predicate) {
    return symbols.stream()
                  .filter(predicate);
  }
}
