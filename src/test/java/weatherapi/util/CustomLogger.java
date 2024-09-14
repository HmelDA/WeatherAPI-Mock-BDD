package weatherapi.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CustomLogger is a utility class for logging using Log4j 2.x.
 * This class provides static methods for logging.
 */
public class CustomLogger {

  private static Logger logger;

  private static final String allureReportUrl = "http://localhost:8083/allure-report";

  // Static block to initialize the logger
  static {
    logger = LogManager.getLogger(CustomLogger.class);
  }

  /**
   * Logs a message at the INFO level.
   *
   * @param message The message to log.
   */
  public static void info(String message) {
    if (logger.isInfoEnabled()) {
      logger.info(message);
    }
  }

  /**
   * Logs a message at the ERROR level.
   *
   * @param message The message to log.
   */
  public static void error(String message) {
    if (logger.isErrorEnabled()) {
      logger.error(message);
    }
  }

  /**
   * Logs an exception at the ERROR level.
   *
   * @param message The message to log.
   * @param e       The exception to log.
   */
  public static void error(String message, Throwable e) {
    if (logger.isErrorEnabled()) {
      logger.error(message, e);
    }
  }

  public static void logReportUrl() {
    if (logger.isInfoEnabled()) {
      logger.info("Allure Report URL: " + allureReportUrl);
    }
  }
}
