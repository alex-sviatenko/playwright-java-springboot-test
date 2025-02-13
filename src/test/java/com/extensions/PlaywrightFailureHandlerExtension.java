package com.extensions;

import com.browser.BrowserManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.Optional;

@Log4j2
@Component
public class PlaywrightFailureHandlerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        // Retrieve Spring ApplicationContext from JUnit 5's SpringExtension
        ApplicationContext springContext = SpringExtension.getApplicationContext(context);

        // Get BrowserManager bean
        BrowserManager browserManager = springContext.getBean(BrowserManager.class);

        boolean testFailed = context.getExecutionException().isPresent();
        Optional<Method> testMethod = context.getTestMethod();

        if (testMethod.isPresent()) {
            if (testFailed) {
                // Log message for test failure with the method name
                log.error("Test method = '{}' failed. Taking screenshot and enabling video capture.", testMethod.get().getName());
            } else {
                // Log message for test success with the method name
                log.info("Test method = '{}' completed successfully.", testMethod.get().getName());
            }
        }

        if (testFailed) {
            browserManager.captureScreenshotOnFailure();
            browserManager.needVideo = true;
        }
    }
}
