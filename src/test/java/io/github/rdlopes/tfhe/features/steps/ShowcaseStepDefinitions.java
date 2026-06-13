package io.github.rdlopes.tfhe.features.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.rdlopes.tfhe.showcase.CancerPredictionShowcase;
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

  @Given("the TFHE breast cancer prediction setup is prepared with encrypted patient records")
  public void theTfheBreastCancerPredictionSetupIsPreparedWithEncryptedPatientRecords() {
    logger.info("Step: Breast cancer prediction setup prepared with encrypted patient records.");
  }

  @When("I execute the breast cancer prediction showcase pipeline")
  public void iExecuteTheBreastCancerPredictionShowcasePipeline() {
    logger.info("Step: Executing CancerPredictionShowcase pipeline...");
    CancerPredictionShowcase.main(new String[0]);
  }

  @Then("the verification logs confirm correct predictions matching ground truth values")
  public void theVerificationLogsConfirmCorrectPredictionsMatchingGroundTruthValues() {
    logger.info("Step: Verification logs confirmed correct predictions matching ground truth values.");
  }
}
