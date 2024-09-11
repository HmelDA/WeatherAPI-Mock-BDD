Feature: Weather API - Positive Tests

  Scenario Outline: Fetch current weather for different cities
    Given the API is available
    When I request the current weather for "<city>"
    Then the response for "<city>" should contain weather data

    Examples:
      | city           |
      | Saint Petersburg |
      | Yerevan        |
      | Vanadzor       |
      | Tbilisi        |
