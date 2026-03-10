# Appium Tusky Automation Framework

[![Build & Test](https://github.com/Yass-Bak/appium-tusky-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/Yass-Bak/appium-tusky-tests/actions)
[![Allure Report](https://img.shields.io/badge/Allure-Report-informational)](https://yass-bak.github.io/appium-tusky-tests/)

This project is a high-performance, industrial-grade Mobile Automation Framework built for the **Tusky Mastodon** Android application. Designed with scalability and reliability in mind, it leverages a modern stack to provide robust UI validation across local and cloud environments.

---

## 🏗️ Architecture & Core Features

### 1. Hybrid Execution Engine
- **Local Run**: Optimized for developer speed using local emulators (UiAutomator2).
- **Cloud Run (BrowserStack)**: Automated integration with BrowserStack App Automate for executing tests on real devices (Samsung Galaxy S22, Pixel 8, OnePlus 11R).

### 2. Intelligent CI/CD Pipeline
- **GitHub Actions Integration**: Fully automated pipeline that handles:
    - Dedicated APK Upload via `curl` with persistent `custom_id` referencing.
    - Security-first credential management using GitHub Secrets.
    - Allure History persistence across runs using GitHub Cache.
    - Automated report deployment to GitHub Pages.

### 3. Advanced Assertion & Logging
- **StepSoftAssert**: Custom soft assertion engine allowing tests to continue after failure, capturing all issues before marking a test as failed.
- **Action-Level Logging**: Intercepts every click, swipe, and input using an `EventFiringDecorator` for granular traceability.
- **Per-Test Logs**: Log4j2 routing that creates dedicated log files for every single test case, attached directly to Allure reports.

---

## 📊 Reporting Dashboard

We use **Allure Report** to provide deep insights into test execution, trends, and stability.

````carousel
![Allure Dashboard - 100% Pass](C:/Users/souna/.gemini/antigravity/brain/366ee573-5589-4d36-82f7-178ab96d70e0/media__1773123676433.png)
<!-- slide -->
![Suites View - Successful Execution](C:/Users/souna/.gemini/antigravity/brain/366ee573-5589-4d36-82f7-178ab96d70e0/media__1773123676509.png)
<!-- slide -->
![Packages View - Test Breakdown](C:/Users/souna/.gemini/antigravity/brain/366ee573-5589-4d36-82f7-178ab96d70e0/media__1773123676467.png)
````

### Failure Analysis
When a test fails, the framework captures the exact state of the application via high-resolution screenshots and detailed execution logs, enabling instant debugging.

````carousel
![Assertion Failure Details](C:/Users/souna/.gemini/antigravity/brain/366ee573-5589-4d36-82f7-178ab96d70e0/media__1773123676256.png)
<!-- slide -->
![Trend Matrix](C:/Users/souna/.gemini/antigravity/brain/366ee573-5589-4d36-82f7-178ab96d70e0/media__1773123676397.png)
````

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 17+
- Maven 3.8+
- Appium Server 2.0+ (if running locally)
- Android Emulator / Real Device

### Running Locally
To launch tests on your local machine:
```bash
mvn clean test -P local
```

### Running on BrowserStack (CI)
The framework uses a dedicated Maven profile for cloud execution. This injects the BrowserStack SDK and manages Java agents automatically.
```bash
mvn clean test -P bstack -Denv=browserstack
```

---

## 🛠️ Best Practices Implemented

- **Retry Logic**: Automatically retries flaky tests up to 3 times using a custom `AnnotationTransformer`.
- **Parallel Execution Ready**: Thread-safe driver management for high-concurrency testing.
- **Dynamic Configuration**: YAML-based configuration for BrowserStack, enabling environment-specific settings.
- **Clean Reports**: Automated lifecycle management of `allure-results` and history to prevent report bloat.

---

## 📬 Contact & Contributions
For any questions regarding the framework architecture or contribution guidelines, please reach out to the maintainers.

---
*Built with ❤️ and 🚬☕ for the mobile automation community.*
⭐ B.Yassine
