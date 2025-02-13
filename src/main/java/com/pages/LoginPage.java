package com.pages;

import com.annotation.Page;
import com.browser.BrowserManager;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Page
public class LoginPage extends BasePage {

    private final HomePage homePage;

    @Autowired
    public LoginPage(BrowserManager browserManager, HomePage homePage) {
        super(browserManager);
        this.homePage = homePage;
    }

    @Step("Type <email> into 'Email Address' textbox")
    public LoginPage typeEmail(final String email) {
        getPage().locator("form[action='/login']").getByPlaceholder("Email Address").fill(email);

        return this;
    }

    @Step("Type <password> into 'Password' textbox")
    public LoginPage typePassword(String password) {
        getPage().getByPlaceholder("Password").fill(password);

        return this;
    }

    @Step("Click on the 'Login' button")
    public HomePage submitLogin() {
        click("button[data-qa=login-button]");

        return homePage;
    }

    @Step("Login to application")
    public HomePage loginAs(String email, String password) {
        log.info("Login as email: {}, password: {}",email, password);
        return this.typeEmail(email)
                .typePassword(password)
                .submitLogin();
    }

    @Step("Get error message")
    public Locator getErrorMessage() {
        return getPage().locator("form[action='/login'] > p");
    }
}
