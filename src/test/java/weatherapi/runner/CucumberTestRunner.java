package weatherapi.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import weatherapi.util.WireMockServerManager;

@CucumberOptions(
    features = "src/test/resources/weatherapi/features",
    glue = "weatherapi.steps",
    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

  @BeforeClass
  public static void setUp() {
    WireMockServerManager.startServer();
  }

  @AfterClass
  public static void tearDown() {
    WireMockServerManager.stopServer();
  }

  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}
