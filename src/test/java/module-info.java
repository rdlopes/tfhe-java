open module tfhe.core.test {
  requires transitive tfhe.core;
  requires transitive org.junit.jupiter.engine;
  requires transitive org.junit.jupiter.api;
  requires transitive org.assertj.core;
  requires transitive org.junit.jupiter.params;

  exports io.github.rdlopes.tfhe.test.assertions;
}
