package weatherapi.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class WeatherApiNegativeSteps {

  private Response response;

  @Before
  public void setup() {
    // Setup the base URI before each test
    RestAssured.baseURI = "http://localhost:8082";
  }

  @Given("the API is available for negative tests")
  public void theApiIsAvailable() {
    // This step ensures that the API is available. We can keep it simple since
    // the actual availability of the API is handled by WireMock.
    // No specific action needed here, since the API stub is already running.
  }

  @When("I request the weather data with an invalid API key")
  public void iRequestWeatherDataWithInvalidApiKey() {
    response = RestAssured.given()
        .queryParam("q", "Saint Petersburg")  // Using a valid city
        .queryParam("key", "invalid_key")     // Passing an invalid API key
        .when()
        .get("/current");
  }

  @Then("I should receive an {string} error")
  public void iShouldReceiveError(String errorMessage) {
    // Check the response status and error message depending on the error type
    switch (errorMessage) {
      case "Invalid API Key":
        response.then()
            .statusCode(401)
            .body("error.message", equalTo("Invalid API Key"));
        break;
      case "City Not Found":
        response.then()
            .statusCode(404)
            .body("error.message", equalTo("City Not Found"));
        break;
      case "404 Not Found":
        response.then()
            .statusCode(404)
            .body("error.message", equalTo("Not Found"));
        break;
      case "400 Bad Request":
        response.then()
            .statusCode(400)
            .body("error.message", equalTo("Bad Request"));
        break;
      default:
        throw new IllegalArgumentException("Unexpected error message: " + errorMessage);
    }
  }


  @When("I request the weather data for a non-existent city")
  public void iRequestWeatherDataForNonExistentCity() {
    response = RestAssured.given()
        .queryParam("q", "InvalidCity")  // Non-existent city
        .queryParam("key", "b4841befc1f54c8b86b114833241209")  // Valid API key
        .when()
        .get("/current");
  }

  @When("I request the weather data with an incorrect endpoint")
  public void iRequestWeatherDataWithIncorrectEndpoint() {
    response = RestAssured.given()
        .queryParam("q", "Saint Petersburg")
        .queryParam("key", "b4841befc1f54c8b86b114833241209")  // Valid API key
        .when()
        .get("/incorrect_endpoint");  // Incorrect endpoint
  }

  @When("I request the weather data with missing parameters")
  public void iRequestWeatherDataWithMissingParameters() {
    response = RestAssured.given()
        .when()
        .get("/current");
  }
}
