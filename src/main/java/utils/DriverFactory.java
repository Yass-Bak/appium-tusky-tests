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
        String env = System.getProperty("env", "local");

        try {
            if ("browserstack".equalsIgnoreCase(env)) {
                return new AndroidDriver(getBrowserStackClientConfig(), getBrowserStackOptions());
            } else {
                return new AndroidDriver(getLocalClientConfig(), getLocalUiAutomator2Options());
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to create AndroidDriver session for env '" + env + "': " + e.getMessage(), e);
        }
    }

    private static UiAutomator2Options getLocalUiAutomator2Options() {
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

    private static AppiumClientConfig getLocalClientConfig() throws MalformedURLException {
        return AppiumClientConfig.defaultConfig()
                .baseUrl(new URL("http://127.0.0.1:4723/"))
                .readTimeout(Duration.ofMinutes(3))
                .connectionTimeout(Duration.ofMinutes(1));
    }

    private static UiAutomator2Options getBrowserStackOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        // The BrowserStack SDK uses browserstack.yml to inject the real devices and OS
        // versions.
        // We only need to provide the base Appium automation name here.
        options.setAutomationName("UiAutomator2");
        return options;
    }

    private static AppiumClientConfig getBrowserStackClientConfig() throws MalformedURLException {
        // The BrowserStack SDK automatically routes the Appium connection to the
        // BrowserStack Hub securely.
        // We provide a dummy localhost URL here purely to satisfy the
        // AppiumClientConfig builder,
        // as the SDK will intercept and override this URL under the hood anyway.
        return AppiumClientConfig.defaultConfig()
                .baseUrl(new URL("http://127.0.0.1:4723/"))
                .readTimeout(Duration.ofMinutes(3))
                .connectionTimeout(Duration.ofMinutes(1));
    }
}
