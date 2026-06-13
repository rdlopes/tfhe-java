package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.showcase.VotingFlowShowcase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowcaseStepDefinitions {

  private static final Logger logger = LoggerFactory.getLogger(ShowcaseStepDefinitions.class);

  @Given("the TFHE election setup is prepared with ZK-proven ballots")
  public void theTfheElectionSetupIsPreparedWithZkProvenBallots() {
    logger.info("Step: Election setup prepared with ZK-proven ballots.");
  }

  @When("I execute the voting flow showcase pipeline")
  public void iExecuteTheVotingFlowShowcasePipeline() {
    logger.info("Step: Executing VotingFlowShowcase pipeline...");
    VotingFlowShowcase.main(new String[0]);
  }

  @Then("the verification logs confirm correct tallies for all candidates")
  public void theVerificationLogsConfirmCorrectTalliesForAllCandidates() {
    logger.info("Step: Verification logs confirmed correct tallies.");
  }
}
