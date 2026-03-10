# Appium Tusky Automation Framework

[![Build & Test](https://github.com/Yass-Bak/appium-tusky-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/Yass-Bak/appium-tusky-tests/actions)
[![Allure Report](https://img.shields.io/badge/Allure-Report-informational)](https://yass-bak.github.io/appium-tusky-tests/)


![Opening](Docs/1.jpg)
This project is a high-performance, industrial-grade Mobile Automation Framework built for the **Tusky Mastodon** Android application. Designed with scalability and reliability in mind, it leverages a modern stack to provide robust UI validation across local and cloud environments.

---

## Architecture & Core Features

### 1. Hybrid Execution Engine
- **Local Run**: Optimized for developer speed using local emulators (UiAutomator2).
- **Cloud Run (BrowserStack)**: Automated integration with BrowserStack App Automate for executing tests on real devices (Samsung Galaxy S22, Pixel 8, OnePlus 11R).

![Opening](Docs/5.jpg)
![Opening](Docs/6.jpg)

### 2. Intelligent CI/CD Pipeline
- **GitHub Actions Integration**: Fully automated pipeline that handles:
    - Dedicated APK Upload via `curl` with persistent `custom_id` referencing.
    - Security-first credential management using GitHub Secrets.
    - Allure History persistence across runs using GitHub Cache.
    - Automated report deployment to GitHub Pages.

### 3. Advanced Assertion & Logging
- **StepSoftAssert**: Custom soft assertion engine allowing tests to continue after failure, capturing all issues before marking a test as failed.

![Opening](Docs/7.jpg)

- **Action-Level Logging**: Intercepts every click, swipe, and input using an `EventFiringDecorator` for granular traceability.

![Opening](Docs/8.jpg)


- **Per-Test Logs**: Log4j2 routing that creates dedicated log files for every single test case, attached directly to Allure reports.

![Opening](Docs/12.jpg)

---

### Reporting Dashboard

We use **Allure Report** to provide deep insights into test execution, trends, and stability.

![Allure Dashboard - 100% Pass](Docs/Capture%20d'écran%202026-03-09%20075642.png)

![Suites View - Successful Execution](Docs/Capture%20d'écran%202026-03-09%20075659.png)

![Packages View - Test Breakdown](Docs/Capture%20d'écran%202026-03-09%20080534.png)

### Failure Analysis
When a test fails, the framework captures the exact state of the application via high-resolution screenshots and detailed execution logs, enabling instant debugging.

![Assertion Failure Details](Docs/Capture%20d'écran%202026-03-10%20071009.png)

![Trend Matrix](Docs/Capture%20d'écran%202026-03-10%20070929.png)

![Categories View](Docs/Capture%20d'écran%202026-03-10%20071214.png)

---

## Getting Started

### Prerequisites
![Opening](Docs/13.jpg)

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

## Best Practices Implemented

![Opening](Docs/9.jpg)
![Opening](Docs/14.jpg)

## 📬 Contact & Contributions
For any questions regarding the framework architecture or contribution guidelines, please feel free to reach me.

---
*Built with ❤️ and 🚬☕ for the mobile automation community.*
                     ⭐ B.Yassine ⭐
