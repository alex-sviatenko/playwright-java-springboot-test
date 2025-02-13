package com.pages;

import com.annotation.Page;
import com.browser.BrowserManager;
import com.configuration.ApplicationProperties;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Page
public class HomePage extends BasePage {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public HomePage(BrowserManager browserManager, ApplicationProperties applicationProperties) {
        super(browserManager);
        this.applicationProperties = applicationProperties;
    }

    @Step("Open the web application")
    public HomePage open() {
        log.info("Navigate to Home page");
        navigate(applicationProperties.getUrl());

        return this;
    }

    @Step("Verify the category section is visible")
    public boolean isCategoryVisible() {
        return getPage().getByText("Category").isVisible();
    }
}
