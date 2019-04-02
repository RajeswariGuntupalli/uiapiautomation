@UIRegressionPack
Feature: UI Locate Service Tests

  Background:
    Given User opens URL

  Scenario Outline: Find service location - <Description>
    Given scenario executes <TestCase> to verify the journey
    When User searches for specific service
    Then Validates the navigation to appropriate page
    And User locates service by searching with specific location criteria
    Then Verifies expected service center is displayed on results page
    Examples:
      | TestCase | Description                                      |
      | TC1      | Find service location by searching with suburb   |
      | TC2      | Find service location by searching with postcode |