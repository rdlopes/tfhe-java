@asciidoc
@order-03
@voting-flow
Feature: Privacy-Preserving Voting Simulation

  Scenario: Simulating a secure election with valid and invalid ballots
    Given a privacy-preserving election setup is prepared
    When voter "Alice" votes for candidate 0 with ZK proof
    And voter "Bob" votes for candidate 1 with ZK proof
    And voter "Charlie" votes for candidate 2 with ZK proof
    And voter "Dave" votes for candidate 0 with ZK proof
    And voter "Eve" submits an invalid blank ballot with ZK proof
    And voter "Frank" submits an invalid double vote ballot with ZK proof
    And voter "Grace" votes for candidate 2 with ZK proof
    Then the tally server processes and aggregates the ballots inside a Rayon context
    And the decryption authority decrypts and verifies the results:
      | candidate_0 | 2 |
      | candidate_1 | 1 |
      | candidate_2 | 2 |
      | invalid     | 2 |
