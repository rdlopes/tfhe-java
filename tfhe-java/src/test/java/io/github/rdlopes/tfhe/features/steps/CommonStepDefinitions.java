package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.ffm.NativeAddress;
import org.junit.jupiter.api.Assumptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class CommonStepDefinitions {

  private static final Logger logger = LoggerFactory.getLogger(CommonStepDefinitions.class);

  private final TfheTestContext context;

  public CommonStepDefinitions(TfheTestContext context) {
    this.context = context;
  }

  @Before
  public void skipUnsupportedScenarios(Scenario scenario) {
    if (Boolean.getBoolean("tfhe.gpu")) {
      Collection<String> tags = scenario.getSourceTagNames();
      if (tags.contains("@compact-list")
          || tags.contains("@advanced-workflows")
          || tags.contains("@fhe-shortint")
          || tags.contains("@benchmarks")
          || tags.contains("@standard-workflow")) {
        logger.info("Skipping CPU-only scenario '{}' in GPU mode", scenario.getName());
        Assumptions.assumeTrue(false, "Skipping CPU-only scenario in GPU mode");
      }
    }
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
    context.keySet.getServerKey().use();
    context.publicKey = context.track(new PublicKey(context.keySet.getClientKey()));
  }
}
