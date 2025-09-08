package io.github.rdlopes.tfhe.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.function.Predicates.falsePredicate;

public class Predicates {
  public static Predicate<String> either(List<String> patterns) {
    return patterns.stream()
                   .filter(Objects::nonNull)
                   .filter(pattern -> !pattern.isBlank())
                   .map(Pattern::compile)
                   .map(Pattern::asPredicate)
                   .reduce(Predicate::or)
                   .orElse(falsePredicate());
  }
}
