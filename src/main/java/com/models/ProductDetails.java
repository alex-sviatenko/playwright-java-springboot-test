package com.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetails {
    private String productName;
    private String productCategory;
    private String price;
    private String productAvailability;
    private String productCondition;
    private String brand;
}
