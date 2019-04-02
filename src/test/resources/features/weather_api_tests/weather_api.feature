@APIRegressionPack
Feature: Weather API Tests

  Scenario Outline: GET Current Weather API - <Description>
    Given scenario executes <TestCase> to verify the journey
    When API is called
    Then Verify response
    Examples:
      | TestCase | Description                                           |
      | TC1      | Get current weather details with specific lat and lon |
      | TC2      | Get current weather details with specific lat and lon |

  Scenario Outline: GET Forecast Weather API - <Description>
    Given scenario executes <TestCase> to verify the journey
    When API is called
    Then Verify response
    Examples:
      | TestCase | Description                                         |
      | TC1      | Get forecast weather details with specific postcode |
      | TC2      | Get forecast weather details with specific postcode |