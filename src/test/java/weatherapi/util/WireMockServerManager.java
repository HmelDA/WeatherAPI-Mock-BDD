package weatherapi.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockServerManager {

  private static WireMockServer wireMockServer;

  public static void startServer() {
    if (wireMockServer == null) {
      // Configure WireMock to work with stub files from the wiremock folder
      wireMockServer = new WireMockServer(
          WireMockConfiguration.options()
              .port(8082)
              .usingFilesUnderClasspath("wiremock")
      );
      wireMockServer.start();
    }
  }

  public static void stopServer() {
    if (wireMockServer != null) {
      wireMockServer.stop();
      wireMockServer = null; // Clearing the server reference
    }
  }
}
