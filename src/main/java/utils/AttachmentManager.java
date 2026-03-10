package utils;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Utility class dedicated to strictly managing Log, Screenshot, and Video
 * attachments for Allure context injection.
 */
public class AttachmentManager {

    private static final Logger logger = LogManager.getLogger(AttachmentManager.class);

    /**
     * Attaches a PNG screenshot to the active Allure test result.
     */
    public static void attachScreenshot(AndroidDriver driver) {
        if (driver == null)
            return;
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Page Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            logger.error("Failed to attach screenshot", e);
        }
    }

    /**
     * Attaches an MP4 screen recording to the active Allure test result.
     */
    public static void attachVideo(AndroidDriver driver) {
        if (driver == null)
            return;
        try {
            String base64Video = driver.stopRecordingScreen();
            if (base64Video != null && !base64Video.isEmpty()) {
                byte[] video = Base64.getDecoder().decode(base64Video);
                Allure.addAttachment("Screen Recording", "video/mp4", new ByteArrayInputStream(video), ".mp4");
            }
        } catch (Exception e) {
            // Devices with API < 27 do not natively support Appium screen recording
            // plugins.
            // This safely bypasses testing on the legacy Android 8.0 emulator while
            // preserving tests.
            logger.warn("Skipped Video Attachment (API < 27 devices do not support screen recording natively): "
                    + e.getMessage());
        }
    }

    /**
     * Reads the specific Log4j2 execution log file for the current test
     * and dumps the static text history into the Allure report step history.
     */
    public static void attachLogs() {
        try {
            String testName = org.apache.logging.log4j.ThreadContext.get("testName");
            File logFile = new File("logs/test-" + testName + ".log");

            if (!logFile.exists()) {
                logFile = new File("logs/appium-default.log");
            }

            if (logFile.exists()) {
                byte[] logData = Files.readAllBytes(logFile.toPath());
                Allure.addAttachment("Test Execution Log", "text/plain", new ByteArrayInputStream(logData),
                        ".log");
            }
        } catch (Exception e) {
            logger.error("Failed to attach specific test log action history.", e);
        }
    }
}
