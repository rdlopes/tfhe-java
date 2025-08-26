open module tfhe.core.test {
  uses java.security.Provider;

  requires tfhe.core;
  requires org.junit.jupiter.engine;
  requires org.junit.jupiter.api;
  requires org.assertj.core;
  requires org.junit.jupiter.params;
}
