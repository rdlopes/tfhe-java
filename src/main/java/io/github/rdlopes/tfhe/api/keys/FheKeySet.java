package io.github.rdlopes.tfhe.api.keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FheKeySet {
  private static final Logger logger = LoggerFactory.getLogger(FheKeySet.class);
  private final ClientKey clientKey;
  private final ServerKey serverKey;

  public FheKeySet() {
    logger.trace("init");
    this.clientKey = new ClientKey();
    this.serverKey = new ServerKey();
    ConfigBuilder builder = new ConfigBuilder();
    Config config = builder.build();
    config.initialize(clientKey, serverKey);
  }

  public ClientKey getClientKey() {
    logger.trace("getClientKey");
    return clientKey;
  }

  public ServerKey getServerKey() {
    logger.trace("getServerKey");
    return serverKey;
  }

}
