package com.tests;

import com.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest extends BaseTest {

    @BeforeEach
    public void login() {
        homePage.open();
        shopMenuComponent.clickLogin();
    }

    @Test
    public void loginUserWithCorrectCredentials() {
        assertEquals("Automation Exercise - Signup / Login", homePage.getTitle());

        loginPage.loginAs(applicationProperties.getEmail(), applicationProperties.getPassword());

        assertTrue(shopMenuComponent.isUserLoggedIn(), "user should be logged in");
    }

    @Test
    public void loginUserWithWrongEmail() {
        loginPage.loginAs("decinid675777@bmixr.com", applicationProperties.getPassword());

        assertThat(loginPage.getErrorMessage()).hasText("Your email or password is incorrect!");
    }

    @Test
    public void loginUserWithWrongPassword() {
        loginPage.loginAs(applicationProperties.getEmail(), "12345");

        assertThat(loginPage.getErrorMessage()).hasText("Your email or password is incorrect!");
    }

    @Test
    public void logoutUser() {
        loginPage.loginAs(applicationProperties.getEmail(), applicationProperties.getPassword());
        shopMenuComponent.clickLogout();

        assertEquals("Automation Exercise - Signup / Login", homePage.getTitle());
    }
}
