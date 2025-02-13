package com.pages;

import com.annotation.Page;
import com.browser.BrowserManager;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Page
public class ContactPage extends BasePage {

    @Autowired
    public ContactPage(BrowserManager browserManager) {
        super(browserManager);
    }

    @Step("Type <name> into 'Name' textbox")
    public ContactPage typeName(String name) {
        getPage().locator("#form-section").getByPlaceholder("Name").fill(name);

        return this;
    }

    @Step("Type <email> into 'Email' textbox")
    public ContactPage typeEmail(String email) {
        getPage().locator("#form-section").getByPlaceholder("Email").fill(email);

        return this;
    }

    @Step("Type <subject> into 'Subject' textbox")
    public ContactPage typeSubject(String subject) {
        getPage().locator("#form-section").getByPlaceholder("Subject").fill(subject);

        return this;
    }

    @Step("Type <message> into 'Message' textbox")
    public ContactPage typeMessage(String message) {
        getPage().locator("#form-section").getByPlaceholder("Your Message Here").fill(message);

        return this;
    }

    @Step("Click Submit button on the Contact page")
    public ContactPage clickSubmitButton() {
        getPage().onDialog(dialog -> {
            log.info("Dialog triggered with message: {}", dialog.message());
            dialog.accept();
        });
        click("#form-section input[type='submit']");

        return this;
    }

    @Step("Get success message")
    public Locator getSuccessMessage() {
        return getPage().locator(".contact-form .alert-success");
    }
}
