package com.pages;

import com.annotation.Page;
import com.browser.BrowserManager;
import com.microsoft.playwright.Locator;
import com.models.ProductDetails;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Page
public class ProductDetailsPage extends BasePage {

    @Autowired
    public ProductDetailsPage(BrowserManager browserManager) {
        super(browserManager);
    }

    private Locator productInformationBlock() {
        return getPage().locator(".product-information");
    }

    @Step("Get product name from Product information")
    public String getProductName() {
        return productInformationBlock().locator("h2").textContent();
    }

    @Step("Get product category from Product information")
    public String getCategoryName() {
        return productInformationBlock().getByText("Category").textContent().substring(10);
    }

    @Step("Get product price from Product information")
    public String getProductPrice() {
        return productInformationBlock().locator("span > span").textContent();
    }

    @Step("Get product availability from Product information")
    public String getProductAvailability() {
        return productInformationBlock()
                .getByText("Availability")
                .locator("..")
                .textContent()
                .substring(14);
    }

    @Step("Get product condition from Product information")
    public String getProductCondition() {
        return productInformationBlock()
                .getByText("Condition")
                .locator("..")
                .textContent()
                .substring(11);
    }

    @Step("Get product brand from Product information")
    public String getProductBrand() {
        return productInformationBlock()
                .getByText("Brand")
                .locator("..")
                .textContent()
                .substring(7);
    }

    @Step("Get product details on the Product details page")
    public ProductDetails getProductDetails() {
        log.info("Get product details on the Product details page");
        ProductDetails prooductDetails = ProductDetails.builder()
                .productName(getProductName())
                .productCategory(getCategoryName())
                .price(getProductPrice())
                .productAvailability(getProductAvailability())
                .productCondition(getProductCondition())
                .brand(getProductBrand())
                .build();

        return prooductDetails;
    }
}
