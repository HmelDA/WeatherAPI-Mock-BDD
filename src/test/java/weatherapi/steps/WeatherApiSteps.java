package weatherapi.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class WeatherApiSteps {

  private Response response;
  private String city;

  @Before
  public void setup() {
    // Setup the base URI before each test
    RestAssured.baseURI = "http://localhost:8082";
  }

  @Given("the API is available")
  public void theApiIsAvailable() {
    // This step ensures that the API is available. We can keep it simple since
    // the actual availability of the API is handled by WireMock.
    // No specific action needed here, since the API stub is already running.
  }

  @When("I request the current weather for {string}")
  public void iRequestTheCurrentWeatherFor(String city) {
    this.city = city;
    // Make a GET request to the weather endpoint
    response = RestAssured.given()
        .when()
        .get("/weather?city=" + city);
  }

  @Then("the response for {string} should contain weather data")
  public void theResponseForShouldContainWeatherData(String city) {
    // Verify the response status and body content
    response.then()
        .statusCode(200)
        .body("city", equalTo(city))
        .body("weather", notNullValue());
  }
}
