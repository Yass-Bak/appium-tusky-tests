package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TestListener class to log test start, success, failure, and skipped events
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("[TEST] Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("[TEST] Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("[TEST] Test failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("[TEST] Test skipped: " + result.getName());
    }

}
