package com.tests;

import com.BaseTest;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactFormTest extends BaseTest {

    @Test
    public void sendContactForm() {
        homePage.open();
        shopMenuComponent.clickContactUs();

        contactPage
                .typeName("Sarah Thompson")
                .typeEmail("sarah.thompson@example.com")
                .typeSubject("Inquiry about product availability")
                .typeMessage("Call me back")
                .clickSubmitButton();

        assertThat(contactPage.getSuccessMessage()).hasText("Success! Your details have been submitted successfully.");
    }
}
