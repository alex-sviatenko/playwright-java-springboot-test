package com.browser;

import com.configuration.TestProperties;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class BrowserFactory {

    public Browser createBrowserInstance(BrowserTypes browserType, Playwright playwright, TestProperties testProperties) {
        log.info("Creating browser instance of type: {}", browserType);

        BrowserCreator browserCreator;
        switch (browserType) {
            case CHROMIUM -> browserCreator = p -> p.chromium().launch(createOptions(testProperties));
            case FIREFOX -> browserCreator = p -> p.firefox().launch(createOptions(testProperties));
            case WEBKIT -> browserCreator = p -> p.webkit().launch(createOptions(testProperties));
            default -> throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        return browserCreator.create(playwright);
    }

    private BrowserType.LaunchOptions createOptions(TestProperties testProperties) {
        log.info("Creating browser launch options: Headless = {}, Slow Motion = {}ms",
                testProperties.isHeadless(), testProperties.getSlow().getMotion());
        return new BrowserType.LaunchOptions()
                .setHeadless(testProperties.isHeadless())
                .setSlowMo(testProperties.getSlow().getMotion());
    }
}
