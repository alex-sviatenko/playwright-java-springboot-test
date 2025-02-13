package com.components;

import com.browser.BrowserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopMenuComponent extends BaseComponent {

    @Autowired
    public ShopMenuComponent(BrowserManager browserManager) {
        super(browserManager);
    }

    public void clickLogin() {
        getPage().getByText("Signup / Login").click();
    }

    public void clickContactUs() {
        getPage().getByText("Contact Us").click();
    }

    public void clickProducts() {
        getPage().getByText("Products").click();
    }

    public boolean isUserLoggedIn() {
        return getPage().getByText("Logged in as").isVisible();
    }

    public void clickLogout() {
        getPage().getByText("Logout").click();
    }
}
