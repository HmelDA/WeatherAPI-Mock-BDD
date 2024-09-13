package weatherapi.util;

import com.github.tomakehurst.wiremock.client.WireMock;

public class StubConfigurator {

  private static final String API_KEY = "b4841befc1f54c8b86b114833241209";
  private static final String CONTENT_TYPE_JSON = "application/json";

  public static void configureStubs() {
    // Positive
    createWeatherStub("SaintPetersburg", "5", "Clear");
    createWeatherStub("Yerevan", "20", "Sunny");
    createWeatherStub("Vanadzor", "15", "Partly Cloudy");
    createWeatherStub("Tbilisi", "25", "Warm");

    // Negative
    createErrorStub("/current?city=SaintPetersburg&apiKey=wrongApiKey", 401, "{\"error\":\"Invalid API Key\"}");
    createErrorStub("/current?city=UnknownCity&apiKey=" + API_KEY, 404, "{\"error\":\"City not found\"}");
    createErrorStub("/invalid-endpoint", 404, "{\"error\":\"Endpoint not found\"}");
    createErrorStub("/current", 400, "{\"error\":\"Missing city parameter\"}");
  }

  private static void createWeatherStub(String city, String temperature, String description) {
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/current?city=" + city + "&apiKey=" + API_KEY))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", CONTENT_TYPE_JSON)
            .withBody(
                String.format("{\"city\":\"%s\",\"temperature\":\"%s\",\"description\":\"%s\"}", city, temperature,
                    description))));
  }

  private static void createErrorStub(String url, int status, String errorMessage) {
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
        .willReturn(WireMock.aResponse()
            .withStatus(status)
            .withHeader("Content-Type", CONTENT_TYPE_JSON)
            .withBody(errorMessage)));
  }
}
