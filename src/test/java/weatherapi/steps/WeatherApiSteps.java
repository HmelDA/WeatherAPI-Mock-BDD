package weatherapi.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import weatherapi.util.CustomLogger;

public class WeatherApiSteps {

  private Response response;
  private String city;

  static {
    // Setup the base URI before each test
    RestAssured.baseURI = "http://localhost:8082";
    CustomLogger.info("Setup complete. Base URI set to " + RestAssured.baseURI);
  }

  @Given("the API is available")
  public void theApiIsAvailable() {
    // This step ensures that the API is available. We can keep it simple since
    // the actual availability of the API is handled by WireMock.
    CustomLogger.info("The API is available at " + RestAssured.baseURI);
  }

  @When("I request the current weather for {string}")
  public void iRequestTheCurrentWeatherFor(String city) {
    this.city = city;
    CustomLogger.info("Requesting current weather for city: " + city);
    // Ensure query parameters are included correctly
    response = RestAssured.given()
        .queryParam("q", city)
        .queryParam("key", "b4841befc1f54c8b86b114833241209")
        .when()
        .get("/current");
    CustomLogger.info("Received response with status code: " + response.getStatusCode());
  }

  @Then("the response for {string} should contain weather data")
  public void theResponseForShouldContainWeatherData(String city) {
    CustomLogger.info("Validating response for city: " + city);
    // Verify the response status and body content
    response.then()
        .statusCode(200)
        .body("location", equalTo(city))
        .body("weather", notNullValue());

    // Getting actual values
    String actualLocation = response.jsonPath().getString("location");
    Object actualWeather = response.jsonPath().get("weather");

    // Checking for discrepancies
    if (!city.equals(actualLocation)) {
      CustomLogger.error("Location mismatch: expected " + city + ", but got " + actualLocation);
    }
    if (actualWeather == null) {
      CustomLogger.error("Weather data is missing for city: " + city);
    }
    CustomLogger.info("Validation complete for city: " + city);
  }
}
