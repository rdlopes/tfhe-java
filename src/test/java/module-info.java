open module tfhe.core.test {
  uses java.security.Provider;

  requires tfhe.core;
  requires transitive org.junit.jupiter.engine;
  requires transitive org.junit.jupiter.api;
  requires transitive org.assertj.core;
  requires transitive org.junit.jupiter.params;
}
