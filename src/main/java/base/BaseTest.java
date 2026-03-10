package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.AppiumListener;
import utils.DriverFactory;
import utils.TestListener;

import java.time.Duration;

/**
 * BaseTest class to handle test setup and teardown
 */
@Listeners(TestListener.class)
public class BaseTest {
    public static AndroidDriver androidDriver;
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        androidDriver = DriverFactory.createAndroidDriver();

        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Start recording the screen for the test
        try {
            androidDriver.startRecordingScreen();
        } catch (Exception e) {
            System.out.println("Warning: Screen recording not supported on this device - " + e.getMessage());
        }

        // Decorate the driver to intercept and log all interactions automatically
        driver = new EventFiringDecorator<>(new AppiumListener()).decorate(androidDriver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(org.testng.ITestResult result) {
        if (androidDriver != null) {

            // Centralized Allure attachments handling separating logic from core setup
            utils.AttachmentManager.attachScreenshot(androidDriver);
            utils.AttachmentManager.attachVideo(androidDriver);
            utils.AttachmentManager.attachLogs();

            androidDriver.quit();
        }
    }
}
