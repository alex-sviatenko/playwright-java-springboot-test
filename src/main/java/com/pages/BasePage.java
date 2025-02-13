package com.pages;

import com.browser.BrowserManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;

public class BasePage {

    protected BrowserManager browserManager;

    @Autowired
    public BasePage(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    protected Page getPage() {
        return browserManager.getPage();
    }

    protected void navigate(String url) {
        getPage().navigate(url);
        waitForPageLoad();
    }

    protected void waitForPageLoad() {
        getPage().waitForLoadState(LoadState.LOAD);
    }

    protected void waitForElementToBeVisible(String selector) {
        getPage().waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    protected void click(String selector) {
        waitForElementToBeVisible(selector);
        getPage().locator(selector).click();
    }

    @Step("Get title of the page")
    public String getTitle() {
        return getPage().title();
    }
}
