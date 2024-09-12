package weatherapi.runner;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class WireMockTest {
  public static void main(String[] args) {
    WireMockServer server = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8082));
    server.start();
    if (server.isRunning()) {
      System.out.println("WireMock server started successfully on port " + server.port() + ".");
    } else {
      System.out.println("Failed to start WireMock server.");
    }
    RestAssured.baseURI = "http://localhost:8082";
    Response response = RestAssured.get("/current?q=Tbilisi&key=b4841befc1f54c8b86b114833241209");
    System.out.println(response.getStatusCode());
    System.out.println(response.getBody().asString());
    server.stop();
  }
}
