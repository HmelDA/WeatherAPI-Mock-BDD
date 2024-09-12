package weatherapi.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;

public class WireMockServerManager {

  private static WireMockServer wireMockServer;

  public static void startServer(int port) {
    if (wireMockServer == null) {
      wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
          .port(port)
          .notifier(new ConsoleNotifier(true))
          .withRootDirectory("src/test/resources"));
      try {
        wireMockServer.start();
        System.out.println("WireMock server started on port: " + wireMockServer.port());
      } catch (Exception e) {
        System.err.println("Failed to start WireMock server: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      System.out.println("WireMock server is already running on port: " + wireMockServer.port());
    }
  }

  public static void stopServer() {
    if (wireMockServer != null) {
      wireMockServer.stop();
      wireMockServer = null;
      System.out.println("WireMock server stopped.");
    } else {
      System.out.println("WireMock server is not running.");
    }
  }

  public static WireMockServer getServer() {
    return wireMockServer;
  }
}
