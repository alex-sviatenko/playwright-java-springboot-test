package com.components;

import com.browser.BrowserManager;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseComponent {

    protected BrowserManager browserManager;

    @Autowired
    public BaseComponent(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    protected Page getPage() {
        return browserManager.getPage();
    }
}
