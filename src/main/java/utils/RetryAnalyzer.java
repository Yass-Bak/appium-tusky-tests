package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Allure;

/**
 * Automatically intercepts failed UI tests and softly retries them after wiping
 * state context.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3;
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            String message = "Appium/TestNG Attempting Retry (" + retryCount + " of " + maxRetryCount
                    + ") for test execution: " + result.getName();

            // Broadcast the retry action exactly into the AppiumListener logging ecosystem
            logger.warn(message);

            // Annotate the Allure report tree visually with the Retry metadata step
            Allure.step("FLAKY TEST RETRY TRIGGERED: " + message);

            try {
                Thread.sleep(3000); // Wait 3 seconds to let Android Web/Activities cool off
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Test Retry interrupted via timeout", e);
            }
            return true;
        }
        return false;
    }
}
