package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonStepDefinitions {

  private static final Logger logger = LoggerFactory.getLogger(CommonStepDefinitions.class);

  private final TfheTestContext context;

  public CommonStepDefinitions(TfheTestContext context) {
    this.context = context;
  }
  
  @After
  public void tearDown() {
    logger.trace("Tear down scenario - cleaning up native resources");
    for (NativeAddress obj : context.getNativeObjects()) {
      try {
        obj.close();
      } catch (Exception _) {
        // Ignored during native resource clean up
      }
    }
    if (context.keySet != null) {
      try {
        context.keySet.close();
      } catch (Exception _) {
        // Ignored during native resource clean up
      }
    }
  }

  @Given("a ClientKey and a PublicKey are initialized")
  public void aClientKeyAndAPublicKeyAreInitialized() {
    context.keySet = KeySet.builder().build();
    context.keySet.getCompressedServerKey().use();
    context.publicKey = context.track(new PublicKey(context.keySet.getClientKey()));
  }
}
