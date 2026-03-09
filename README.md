# Appium Tusky Automation Framework

This project is a complete Mobile Automation Framework built for the Tusky Mastodon Android application.

## Technologies Used
*   Appium (UiAutomator2)
*   TestNG (with custom AnnotationTransformer Retry execution logic)
*   Maven (`pom.xml` strictly bindings)
*   Log4j2 (Action-Listener Logging via EventFiringDecorator)
*   Allure (Comprehensive Reporting and Trend Matrix)

## How to Run Tests

Ensure you have your Android Emulator running (ideally API 27+), and the underlying Appium server running bounds to Port 4723.

```bash
mvn clean test
```

This *single command* binds everything sequentially. It will:
1.  Safely backup your previous Allure Trend/History metrics (Antrun `pre-clean`).
2.  Clear the project dynamically.
3.  Inject the backed-up history into the fresh allure-results dir (Antrun `generate-test-resources`).
4.  Spin up the Appium driver and run all UI Test Cases.
5.  Use Log4j2 to intercept and log native element interactions.
6.  Auto-retry any flaky Android elements up to 3 times automatically.
7.  Generate the raw Allure JSON results natively.
8.  Automatically compile the final HTML Allure report with historic trends attached to `target/site/allure-maven-plugin`!

*(Note: Even if tests fail, Maven will intentionally continue executing boundaries and generate the final report due to the injected `<testFailureIgnore>true</testFailureIgnore>` parameter!)*

## Viewing the Report
Once testing is finished, launch the rich dashboard using:
```bash
allure serve target/allure-results
```

## Known Limitations
Appium's `startRecordingScreen()` native command strictly requires an Android Emulator running **API level 27 or higher**. 
To preserve stability for devices on API 26 (like the typical legacy emulator runtime), the `AttachmentManager.java` utility safely traps the execution exception and gracefully omits the video matrix completely to prevent crashing the continuous integration suite. 
Page Screenshots and Log action files will still bind correctly!
