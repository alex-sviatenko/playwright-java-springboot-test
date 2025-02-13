package com.browser;

import com.configuration.TestProperties;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Component
public class BrowserManager {

    private final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    private final BrowserFactory browserFactory;
    private final TestProperties testProperties;

    public boolean needVideo;
    private Path recordedVideoPath;

    @Autowired
    public BrowserManager(BrowserFactory browserFactory, TestProperties testProperties) {
        this.browserFactory = browserFactory;
        this.testProperties = testProperties;
    }

    public Playwright getPlaywright() {
        if (playwrightThreadLocal.get() == null) {
            playwrightThreadLocal.set(Playwright.create());
        }

        return playwrightThreadLocal.get();
    }

    public Browser getBrowser() {
        if (browserThreadLocal.get() == null) {
            browserThreadLocal.set(createBrowser(getPlaywright()));
        }

        return browserThreadLocal.get();
    }

    private Browser createBrowser(Playwright playwright) {
        BrowserTypes browserType = BrowserTypes.valueOf(testProperties.getBrowser().toUpperCase());

        browserThreadLocal.set(browserFactory.createBrowserInstance(browserType,
                playwright,
                testProperties));

        return browserThreadLocal.get();
    }

    public BrowserContext getBrowserContext() {
        if (browserContextThreadLocal.get() == null) {

            if (testProperties.getVideo().isEnabled()) {
                BrowserContext browserContext = getBrowser()
                        .newContext(new Browser.NewContextOptions()
                                .setRecordVideoDir(Paths.get(testProperties.getVideo().getPath()))
                                .setRecordVideoSize(testProperties.getVideo().getSize().getWidth(),
                                        testProperties.getVideo().getSize().getHeight())
                        );
                browserContextThreadLocal.set(browserContext);

                log.info("Video recording enabled. Saving to directory: {} with size {}x{}.",
                        testProperties.getVideo().getPath(),
                        testProperties.getVideo().getSize().getWidth(),
                        testProperties.getVideo().getSize().getHeight());

            } else {
                browserContextThreadLocal.set(getBrowser().newContext());
                log.info("Video recording is disabled.");
            }
        }

        return browserContextThreadLocal.get();
    }

    public Page getPage() {
        if (pageThreadLocal.get() == null) {
            Page page = getBrowserContext().newPage();
            page.setViewportSize(testProperties.getScreen().getSize().getWidth(),
                    testProperties.getScreen().getSize().getHeight());
            pageThreadLocal.set(page);
        }

        return pageThreadLocal.get();
    }

    @Attachment(value = "Test Video", type = "video/webm")
    @SneakyThrows
    public byte[] captureVideo() {
        log.info("Capturing a video for the test report");
        return Files.readAllBytes(recordedVideoPath);
    }

    @Attachment(value = "Failed Test Case Screenshot", type = "image/png")
    public byte[] captureScreenshotOnFailure() {
        log.info("Capturing a screenshot on test failure");
        return pageThreadLocal.get().screenshot();
    }

    public void closeBrowserContext() {
        if (pageThreadLocal.get() != null) {
            recordedVideoPath = pageThreadLocal.get().video().path();
            pageThreadLocal.get().close();
            pageThreadLocal.remove();
        }
        if (browserContextThreadLocal.get() != null) {
            browserContextThreadLocal.get().close();
            browserContextThreadLocal.remove();
        }
    }
}
