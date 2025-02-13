package com.tests;

import com.BaseTest;
import com.models.ProductDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.enums.ProductAvailability.IN_STOCK;
import static com.enums.ProductConditions.NEW;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsTest extends BaseTest {

    @BeforeEach
    public void openProductsPage() {
        homePage.open();
        shopMenuComponent.clickProducts();
    }

    @Test
    public void checkProductDetails() {
        String productName = "Summer White Top";

        productsPage.openProductByName(productName);

        ProductDetails productExpected = ProductDetails.builder()
                .productName(productName)
                .productCategory("Women > Tops")
                .price("Rs. 400")
                .productAvailability(IN_STOCK.getAvailability())
                .productCondition(NEW.getCondition())
                .brand("H&M")
                .build();

        ProductDetails productActual = productDetailsPage.getProductDetails();

        assertEquals(productExpected, productActual);
    }

    @Test
    public void checkSearchProduct() {
        String productName = "Lace Top For Women";

        assertThat(productsPage.getItemsTitle()).hasText("All Products");

        productsPage.searchProduct(productName);

        assertThat(productsPage.getItemsTitle()).hasText("Searched Products");
        assertThat(productsPage.getProductList()).hasCount(1);
        assertThat(productsPage.getProductList().first()).containsText(productName);
    }
}
