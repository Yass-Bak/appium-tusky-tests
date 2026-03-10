package utils;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StepSoftAssert extends SoftAssert {

    private static final Logger logger = LogManager.getLogger(StepSoftAssert.class);

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        String errorMsg = ex.getMessage() != null ? ex.getMessage() : "Soft Assertion Failed";
        logger.error("[ASSERT-FAIL]" + errorMsg);

        // 1. Report silently to Allure at the step level
        Allure.step("ASSERTION FAILED: " + errorMsg, io.qameta.allure.model.Status.FAILED);

        // 2. Attach Screenshot at this exact moment
        AttachmentManager.attachScreenshot(BaseTest.androidDriver);

        // 3. Attach the specific test log file
        String testName = ThreadContext.get("testName");
        Path logPath = Paths.get("logs", "test-" + testName + ".log");
        if (!Files.exists(logPath)) {
            logPath = Paths.get("logs", "appium-default.log");
        }

        if (Files.exists(logPath)) {
            try {
                byte[] logData = Files.readAllBytes(logPath);
                Allure.addAttachment("Step Execution Log", "text/plain", new ByteArrayInputStream(logData), ".log");
            } catch (Exception e) {
                logger.error("Failed to attach log to soft assertion", e);
            }
        }

        // 4. Pass to parent so the failure is recorded for assertAll()
        super.onAssertFailure(assertCommand, ex);
    }
}
