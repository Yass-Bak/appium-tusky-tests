package utils;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * DriverFactory class to create AndroidDriver session
 */
public class DriverFactory {

    public static AndroidDriver createAndroidDriver() {
        try {
            return new AndroidDriver(getClientConfig(), getUiAutomator2Options());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create AndroidDriver session: " + e.getMessage(), e);
        }
    }

    private static UiAutomator2Options getUiAutomator2Options() {
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
        options.setCapability("appium:androidInstallTimeout", 90000);
        options.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
        options.setCapability("appium:adbExecTimeout", 60000);

        return options;
    }

    private static AppiumClientConfig getClientConfig() throws MalformedURLException {
        return AppiumClientConfig.defaultConfig()
                .baseUrl(new URL("http://127.0.0.1:4723/"))
                .readTimeout(Duration.ofMinutes(3))
                .connectionTimeout(Duration.ofMinutes(1));
    }
}
