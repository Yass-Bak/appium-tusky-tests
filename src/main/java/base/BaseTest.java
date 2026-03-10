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
import utils.StepSoftAssert;

import java.time.Duration;
import java.lang.reflect.Method;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseTest class to handle test setup and teardown
 */
@Listeners(TestListener.class)
public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    public static ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<>();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected StepSoftAssert softAssert;

    public static AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setUp(Method method) {
        ThreadContext.put("testName", method.getName());
        logger.info("======================================================");
        logger.info("[ENV] Starting test execution: " + method.getName());

        logger.info("[ENV] Initializing AndroidDriver session...");
        androidDriver.set(DriverFactory.createAndroidDriver());
        logger.info("[ENV] AndroidDriver session established successfully.");

        softAssert = new StepSoftAssert();

        getAndroidDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Start recording the screen for the test
        try {
            getAndroidDriver().startRecordingScreen();
        } catch (Exception e) {
            System.out.println("Warning: Screen recording not supported on this device - " + e.getMessage());
        }

        // Decorate the driver to intercept and log all interactions automatically
        driver.set(new EventFiringDecorator<>(new AppiumListener()).decorate(getAndroidDriver()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(org.testng.ITestResult result) {
        if (getAndroidDriver() != null) {
            logger.info("[ENV] Tearing down AndroidDriver session for test: " + result.getName());
            // Centralized Allure attachments handling separating logic from core setup
            utils.AttachmentManager.attachScreenshot(getAndroidDriver());
            utils.AttachmentManager.attachVideo(getAndroidDriver());
            utils.AttachmentManager.attachLogs();

            getAndroidDriver().quit();
            androidDriver.remove();
            driver.remove();
            logger.info("[ENV] AndroidDriver successfully closed.");
            logger.info("======================================================");
        }
        ThreadContext.clearMap();
    }
}
