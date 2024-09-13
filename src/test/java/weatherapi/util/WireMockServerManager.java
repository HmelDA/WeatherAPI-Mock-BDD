package weatherapi.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockServerManager {

  private static WireMockServer wireMockServer;

  public static void startServer() {
    if (wireMockServer == null) {
      wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8082));
      wireMockServer.start();
      WireMock.configureFor("localhost", 8082);
      StubConfigurator.configureStubs();
    }
  }

  public static void stopServer() {
    if (wireMockServer != null) {
      wireMockServer.stop();
      wireMockServer = null; // Очистка ссылки на сервер
    }
  }
}
