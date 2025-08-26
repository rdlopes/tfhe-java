module tfhe.core {
  exports io.github.rdlopes.tfhe.core.configuration;
  exports io.github.rdlopes.tfhe.jca;
  exports io.github.rdlopes.tfhe.core.keys;
  exports io.github.rdlopes.tfhe.core.serde;
  requires org.apache.commons.io;
  requires org.apache.commons.lang3;
  requires org.slf4j;
  provides java.security.Provider with io.github.rdlopes.tfhe.jca.TfheProvider;
}
