package weatherapi.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * WireMockServerManager manages the WireMock server instance.
 * It starts and stops the server for testing purposes.
 */
public class WireMockServerManager {

  private static WireMockServer wireMockServer;

  /**
   * Starts the WireMock server if it is not already running.
   */
  public static void startServer() {
    if (wireMockServer == null) {
      CustomLogger.info("Starting WireMock server...");
      // Configure WireMock to work with stub files from the wiremock folder
      wireMockServer = new WireMockServer(
          WireMockConfiguration.options()
              .port(8082)
              .usingFilesUnderClasspath("wiremock")
      );
      wireMockServer.start();
      CustomLogger.info("WireMock server started on port 8082.");
    } else {
      CustomLogger.info("WireMock server is already running.");
    }
  }

  /**
   * Stops the WireMock server if it is running.
   */
  public static void stopServer() {
    if (wireMockServer != null) {
      CustomLogger.info("Stopping WireMock server...");
      wireMockServer.stop();
      wireMockServer = null; // Clearing the server reference
      CustomLogger.info("WireMock server stopped.");
    } else {
      CustomLogger.info("WireMock server is not running.");
    }
  }
}
