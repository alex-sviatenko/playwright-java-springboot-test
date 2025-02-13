package com.pages;

import com.annotation.Page;
import com.browser.BrowserManager;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@Page
public class ProductsPage extends BasePage {

    private final ProductDetailsPage productDetailsPage;

    @Autowired
    public ProductsPage(BrowserManager browserManager, ProductDetailsPage productDetailsPage) {
        super(browserManager);
        this.productDetailsPage = productDetailsPage;
    }

    @Step("Get product list on the Products page")
    public Locator getProductList() {
        return getPage().locator(".features_items > div.col-sm-4");
    }

    @Step("Open <productName> item on the Products page")
    public ProductDetailsPage openProductByName(String productName) {
        log.info("Open <{}> item on the Products page", productName);
        Locator product = this.getProductList()
                .filter(new Locator.FilterOptions().setHasText(productName));

        if ((product.isVisible())) {
            product.getByText("View Product").click();
        } else throw new RuntimeException("Item not found on the Products page");

        return productDetailsPage;
    }

    @Step("Search the product with text <searchText>")
    public ProductsPage searchProduct(String searchText) {
        return this.typeSearchField(searchText)
                .clickSearchButton();
    }

    @Step("Type <searchText> in Search field")
    public ProductsPage typeSearchField(String searchText) {
        getPage().getByPlaceholder("Search Product").fill(searchText);

        return this;
    }

    @Step("Click on the Search button")
    public ProductsPage clickSearchButton() {
        click("#submit_search");

        return this;
    }

    @Step("Get the title of items on the Products page")
    public Locator getItemsTitle() {
        return getPage().locator(".features_items .title");
    }

}
