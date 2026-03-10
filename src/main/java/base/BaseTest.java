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

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
        options.setNoReset(false); // Wipe app state so OAuth browser cache doesn't persist
        options.setNewCommandTimeout(Duration.ofSeconds(30));

        // ── Session-creation timeouts (prevent infinite hang on slow CI emulators) ──
        // How long to wait for APK installation to finish (ms)
        options.setCapability("appium:androidInstallTimeout", 90000);
        // How long to wait for UiAutomator2 server APK to be installed on device (ms)
        options.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
        // How long to wait for UiAutomator2 server to launch and be ready (ms)
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
        // How long any single ADB command may take before being considered hung (ms)
        options.setCapability("appium:adbExecTimeout", 60000);

        // Wrap session creation in a 2-minute Future so a hung emulator
        // fails fast with a clear error instead of hanging indefinitely
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            final UiAutomator2Options finalOptions = options;
            Future<AndroidDriver> future = executor.submit(
                    () -> new AndroidDriver(new URL("http://127.0.0.1:4723/"), finalOptions));
            androidDriver = future.get(120, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new RuntimeException(
                    "AndroidDriver session creation timed out after 120s — emulator may be unresponsive", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create AndroidDriver session", e);
        } finally {
            executor.shutdownNow();
        }

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
