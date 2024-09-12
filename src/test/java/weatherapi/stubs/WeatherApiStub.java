package weatherapi.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import weatherapi.util.WireMockServerManager;

public class WeatherApiStub {

  private static final String API_KEY = "b4841befc1f54c8b86b114833241209";

  @BeforeClass
  public static void setup() {
    WireMockServerManager.startServer(8082); // Запускаем сервер на порту 8082
    WireMock.configureFor("localhost", 8082);
    setupPositiveStubs();
    setupNegativeStubs();
  }

  private static void setupPositiveStubs() {
    String[] cities = {"Saint Petersburg", "Yerevan", "Vanadzor", "Tbilisi"};
    for (String city : cities) {
      stubFor(get(urlPathEqualTo("/current"))
          .withQueryParam("q", equalTo(city))
          .withQueryParam("key", equalTo(API_KEY))
          .willReturn(aResponse().withStatus(200).withBodyFile("weather_api_" + city.toLowerCase().replace(" ", "_") + ".json")));
    }
  }

  private static void setupNegativeStubs() {
    stubFor(get(urlPathEqualTo("/current"))
        .withQueryParam("key", notMatching(API_KEY))
        .willReturn(aResponse().withStatus(401).withBodyFile("error_invalid_api_key.json")));

    stubFor(get(urlPathEqualTo("/current"))
        .withQueryParam("q", notMatching("ValidCityName"))
        .willReturn(aResponse().withStatus(404).withBodyFile("error_city_not_found.json")));

    stubFor(get(urlPathEqualTo("/incorrect_endpoint"))
        .willReturn(aResponse().withStatus(404).withBodyFile("error_incorrect_endpoint.json")));

    stubFor(get(urlPathEqualTo("/current"))
        .withQueryParam("key", absent())
        .willReturn(aResponse().withStatus(400).withBodyFile("error_missing_parameters.json")));
  }

  @AfterClass
  public static void teardown() {
    WireMockServerManager.stopServer();
  }
}
