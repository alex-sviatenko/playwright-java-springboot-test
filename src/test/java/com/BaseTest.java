package com;

import com.browser.BrowserManager;
import com.components.ShopMenuComponent;
import com.configuration.ApplicationProperties;
import com.configuration.TestConfiguration;
import com.configuration.TestProperties;
import com.pages.*;
import com.extensions.PlaywrightFailureHandlerExtension;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@SpringBootTest(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(PlaywrightFailureHandlerExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    // properties
    @Autowired
    protected ApplicationProperties applicationProperties;
    @Autowired
    protected TestProperties testProperties;

    // pages and components
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected LoginPage loginPage;
    @Autowired
    protected ContactPage contactPage;
    @Autowired
    protected ProductsPage productsPage;
    @Autowired
    protected ProductDetailsPage productDetailsPage;
    @Autowired
    protected ShopMenuComponent shopMenuComponent;

    @Autowired
    private BrowserManager browserManager;

    @BeforeAll
    public void beforeAll() {
        log.info("Init browser");
        browserManager.getBrowser();
    }

    @BeforeEach
    public void beforeEach() {
        log.info("Create browser context before test");
        browserManager.getPage();
    }

    @AfterEach
    public void afterEach() {
        log.info("Close browser context after test");
        browserManager.closeBrowserContext();

        if (testProperties.getVideo().isEnabled() && browserManager.needVideo) {
            browserManager.captureVideo();
            browserManager.needVideo = false;
        }
    }
}
