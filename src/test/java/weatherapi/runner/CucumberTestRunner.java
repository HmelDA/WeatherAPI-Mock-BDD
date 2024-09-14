package weatherapi.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import weatherapi.util.CustomLogger;
import weatherapi.util.WireMockServerManager;

@CucumberOptions(
    features = "src/test/resources/weatherapi/features",
    glue = "weatherapi.steps",
    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

  @BeforeClass
  public static void setUp() {
    CustomLogger.info("Start of project configuration...");
    CustomLogger.logReportUrl();
    WireMockServerManager.startServer();
    CustomLogger.info("Configuration completed successfully.");
  }

  @AfterClass
  public static void tearDown() {
    CustomLogger.info("Stopping the configuration...");
    WireMockServerManager.stopServer();
    CustomLogger.info("Configuration stopped.");
    CustomLogger.logReportUrl();
  }

  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}
