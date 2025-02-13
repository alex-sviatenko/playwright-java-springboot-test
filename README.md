# Test Automation Framework - UI Automation with Playwright, Spring Boot and JUnit 5

## Overview

This UI Test Automation Framework is designed **to automate end-to-end UI testing of web applications**.
The framework leverages **Playwright** for browser automation, **JUnit 5** for test structuring and execution,
and **Gradle** as the build tool. **Spring Boot** is used for **Dependency Injection (DI) and
Configuration Management**.
**Log4j2** handles logging, and **Allure** is used to generate rich test reports.

Additionally, this framework supports parallel test execution, the ability to capture screenshots and videos during test
execution,
and integrates configuration from an `application.yml` file to set up environment-specific settings, such as browser
type and timeout.

## Key Features

- **Playwright Integration:** Automates UI testing across various browsers (Chromium, Firefox, and WebKit),
  simulating real user interactions like clicks, form submissions, and validation.
- **JUnit 5:** Organizes tests and provides advanced features such as parallel test execution, flexible assertions,
  and lifecycle management.
- **Parallel Execution:** The framework supports parallel execution of tests, improving test suite execution times.
  You can configure the number of concurrent threads and control execution modes for classes and tests.
- **Spring Boot Integration:** Leverages Spring Boot for managing dependencies and application configuration
  via @Configuration and @ConfigurationProperties annotations, which makes the setup process highly configurable and
  flexible.
- **Log4j2 for Logging:** Provides detailed logging to track test steps and errors. Configured via Log4j2’s XML
  configuration.
- **Allure Reporting:** Generates detailed, visually appealing test reports that include test status (pass/fail),
  execution times, logs, screenshots, and videos.
- **Video and Screenshots:** Capture videos and screenshots during test execution for easier debugging and issue
  tracking.
  These are automatically attached to the Allure reports.
- **JUnit 5 Configuration:** Supports configuration for parallel test execution, which helps improve test performance
  by running tests concurrently.

## Technologies Used

Given test automation project is built with next key frameworks and technologies:

- [Java 21](https://openjdk.org/projects/jdk/21/) as the programming language.
- [Gradle 8.12.1](https://gradle.org/) build tool for managing dependencies and running the test suite.
- [Spring Boot](https://spring.io/projects/spring-boot) for bean lifecycle management, dependency injection and
  configuration properties;
- [Playwright](https://playwright.dev/) a Node.js library that enables reliable browser automation.
  Supports Chromium, Firefox, and WebKit.
- [Log4j2](https://logging.apache.org/log4j/) a powerful logging library to manage logs from your automated tests.
- [Project Lombok](https://projectlombok.org/) is a Java library that reduces boilerplate code by generating getters,
  setters, constructors, and other common methods through annotations.
- [JUnit 5](https://junit.org/junit5/) a modern testing framework that supports features such as parallel test
  execution,
  flexible assertions, and annotations.
- [Allure](https://docs.qameta.io/allure/) a framework for generating beautiful and comprehensive test reports,
  including details like screenshots,
  videos, and execution times.

## Getting Started

### Prerequisites

- JDK 21 or higher.
- Gradle for build and dependency management.
- Playwright is included as a Gradle dependency and will be installed automatically during the build process

### Installation

1. [Fork](https://github.com/alex-sviatenko/playwright-java-springboot-test/fork) the repository.
2. Clone the repository and navigate to the project

```
$ git clone https://github.com/[your_username]/playwright-java-springboot-test.git
$ cd playwright-java-springboot-test
```

3. Create environment variables for your testing credentials.

To keep sensitive data (such as login credentials) secure and separate from your codebase, the test framework uses
**environment variables** for configuration. Specifically, the `email` and `password` required for logging into
[Automation Exercise](https://www.automationexercise.com/) are retrieved from environment variables.

For Linux/macOS:

```bash
  export TEST_EMAIL=your-temp-email@example.com
  export TEST_PASSWORD=your-temp-password
```

For Windows:

```bash
  set TEST_EMAIL=your-temp-email@example.com
  set TEST_PASSWORD=your-temp-password
```

4. Configure the test properties:

The `application.yml` will reference these environment variables. The email and password will be read at runtime.

**Example `application.yml`**:

```yaml
  test:
    email: ${TEST_EMAIL}
    password: ${TEST_PASSWORD}
```

5. Build the project and run tests using Gradle:

```shell
./gradlew test
```

Overriding YAML properties via Command Line in Spring Boot

```shell
./gradlew test -Dtest.browser=firefox -Dtest.headless=true
```

6. Generate Allure Reports:

```shell
./gradlew allureServe
```

## Parallel Test Execution with JUnit 5

The framework is configured to run tests in parallel to speed up execution.
The following settings are applied in the `junit-platform.properties` file:

- **Parallel Execution Enabled:** Tests are executed in parallel to improve speed.
- **Test Execution Configuration:**
    - **same_thread:** Ensures tests run on the same thread by default.
    - **concurrent:** Runs classes concurrently.
    - **fixed:** A fixed parallelism strategy is used with 2 threads running tests concurrently.

You can modify these properties in the `junit-platform.properties` file to adjust the parallel execution as needed.

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=same_thread
junit.jupiter.execution.parallel.mode.classes.default=concurrent
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=2
```

## Allure Reporting

Allure is used for generating comprehensive and visually appealing test reports. It displays test results,
execution times, logs, and attachments such as screenshots and videos.
To configure Allure reporting, follow these steps:

1. Allure Properties: Set the directory where Allure should store the test results by adding the following to the
   `allure.properties` file:

```properties
allure.results.directory=build/allure-results
```

2. Test Attachments (Screenshots and Videos):

- The framework is set to capture screenshots and record videos during test execution.
- Videos are stored in the directory specified in your `application.yml` configuration, under **test.video.path**.
- Allure automatically attaches the screenshots and videos to the report when test is failed.

3. Generate the Allure Report:

- After running your tests, you can view the Allure report with:

```shell
./gradlew allureServe
```

## Application Configuration

The test framework can be customized through the `application.yml` file.
Here you can define settings related to the test environment, such as the base URL, email,
password, browser settings, and video capture configurations.
It provides an organized way to configure test properties that are injected into the test classes.

- The ***@Configuration*** annotation allows you to create configuration classes that bind values from the
  `application.yml` file to fields in the class.
- The ***@ConfigurationProperties*** annotation is used to map the configuration values automatically to the fields in
  the class.

### Example Configuration (application.yml): ###

```yaml
application:
  url: https://www.automationexercise.com/
  email: ${TEST_EMAIL}
  password: ${TEST_PASSWORD}

test:
  browser: chromium
  headless: true
  slow:
    motion: 100
  timeout: 10000
  video:
    enabled: true
    path: build/test-video/
    size:
      width: 1280
      height: 720
  screen:
    size:
      width: 1920
      height: 1080
```

- **browser:** Specifies which browser to use (e.g., chromium, firefox, webkit).
- **headless:** Whether to run the browser in headless mode (no GUI).
- **timeout:** Time in milliseconds for test steps to complete before timing out.
- **video:** Configuration for enabling video recording, including the storage path and resolution.
- **screen:** Configuration for capturing screenshots, including screen size
