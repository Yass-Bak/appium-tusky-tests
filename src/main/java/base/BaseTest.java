package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.AppiumListener;
import utils.TestListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners(TestListener.class)
public class BaseTest {
    public static AndroidDriver androidDriver;
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName("Android Emulator");
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");

        // Relative path to the app in the project directory
        options.setApp(System.getProperty("user.dir") + "/apps/tusky.apk");

        options.setAppPackage("com.keylesspalace.tusky");
        options.setAppActivity("com.keylesspalace.tusky.MainActivity");
        options.setNoReset(false); // Changed to false to wipe the OAuth browser state from previous runs
        options.setNewCommandTimeout(Duration.ofSeconds(30));

        try {
            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Start recording the screen for the test
            try {
                androidDriver.startRecordingScreen();
            } catch (Exception e) {
                System.out.println("Warning: Screen recording not supported on this device - " + e.getMessage());
            }

            // Decorate the driver to intercept and log all interactions automatically
            driver = new EventFiringDecorator<>(new AppiumListener()).decorate(androidDriver);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Appium server URL is invalid", e);
        }
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
