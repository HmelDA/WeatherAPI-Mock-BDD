Feature: Weather API - Negative Tests

  Scenario: Handle invalid API key
    Given the API is available for negative tests
    When I request the weather data with an invalid API key
    Then I should receive an "Invalid API Key" error

  Scenario: Handle city not found
    Given the API is available for negative tests
    When I request the weather data for a non-existent city
    Then I should receive an "City Not Found" error

  Scenario: Handle incorrect endpoint
    Given the API is available for negative tests
    When I request the weather data with an incorrect endpoint
    Then I should receive an "404 Not Found" error

  Scenario: Handle missing parameters
    Given the API is available for negative tests
    When I request the weather data with missing parameters
    Then I should receive an "400 Bad Request" error
