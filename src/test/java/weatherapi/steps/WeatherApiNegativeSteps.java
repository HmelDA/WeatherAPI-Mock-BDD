package weatherapi.steps;

import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import weatherapi.util.CustomLogger;

public class WeatherApiNegativeSteps {

  private Response response;

  static {
    // Setup the base URI before each test
    RestAssured.baseURI = "http://localhost:8082";
    CustomLogger.info("Setup complete. Base URI set to " + RestAssured.baseURI);
  }

  @Given("the API is available for negative tests")
  public void theApiIsAvailable() {
    // Log that API is available
    CustomLogger.info("The API is available for negative tests at " + RestAssured.baseURI);
  }

  @When("I request the weather data with an invalid API key")
  public void iRequestWeatherDataWithInvalidApiKey() {
    CustomLogger.info("Requesting weather data with an invalid API key.");
    response = RestAssured.given()
        .queryParam("q", "Saint Petersburg")  // Using a valid city
        .queryParam("key", "invalid_key")     // Passing an invalid API key
        .when()
        .get("/current");
    CustomLogger.info("Received response with status code: " + response.getStatusCode());
  }

  @Then("I should receive an {string} error")
  public void iShouldReceiveError(String errorMessage) {
    CustomLogger.info("Validating response for error message: " + errorMessage);
    // Check the response status and error message depending on the error type
    switch (errorMessage) {
      case "Invalid API Key":
        response.then()
            .statusCode(401)
            .body("error.message", equalTo("Invalid API Key"));
        CustomLogger.info("Validation completed for 'Invalid API Key' error.");
        break;
      case "City Not Found":
        response.then()
            .statusCode(404)
            .body("error.message", equalTo("City Not Found"));
        CustomLogger.info("Validation completed for 'City Not Found' error.");
        break;
      case "404 Not Found":
        response.then()
            .statusCode(404)
            .body("error.message", equalTo("Not Found"));
        CustomLogger.info("Validation completed for '404 Not Found' error.");
        break;
      case "400 Bad Request":
        response.then()
            .statusCode(400)
            .body("error.message", equalTo("Bad Request"));
        CustomLogger.info("Validation completed for '400 Bad Request' error.");
        break;
      default:
        CustomLogger.error("Unexpected error message: " + errorMessage);
        throw new IllegalArgumentException("Unexpected error message: " + errorMessage);
    }
  }

  @When("I request the weather data for a non-existent city")
  public void iRequestWeatherDataForNonExistentCity() {
    CustomLogger.info("Requesting weather data for a non-existent city.");
    response = RestAssured.given()
        .queryParam("q", "InvalidCity")  // Non-existent city
        .queryParam("key", "b4841befc1f54c8b86b114833241209")  // Valid API key
        .when()
        .get("/current");
    CustomLogger.info("Received response with status code: " + response.getStatusCode());
  }

  @When("I request the weather data with an incorrect endpoint")
  public void iRequestWeatherDataWithIncorrectEndpoint() {
    CustomLogger.info("Requesting weather data with an incorrect endpoint.");
    response = RestAssured.given()
        .queryParam("q", "Saint Petersburg")
        .queryParam("key", "b4841befc1f54c8b86b114833241209")  // Valid API key
        .when()
        .get("/incorrect_endpoint");  // Incorrect endpoint
    CustomLogger.info("Received response with status code: " + response.getStatusCode());
  }

  @When("I request the weather data with missing parameters")
  public void iRequestWeatherDataWithMissingParameters() {
    CustomLogger.info("Requesting weather data with missing parameters.");
    response = RestAssured.given()
        .when()
        .get("/current");
    CustomLogger.info("Received response with status code: " + response.getStatusCode());
  }
}
