package io.github.pricescrawler.config;

import lombok.Getter;

@Getter
public enum ConstValues {
    CATALOG("catalog"),
    COOKIES("cookies"),
    INCIDENTS_IDS("incidentsIds"),
    PRODUCT_ID("productId"),
    PRODUCTS_IDS("productsIds"),
    PRODUCT_DATA("productData"),
    IGNORE_URL_INCIDENT("MERGE_URL_INCIDENT");

    private final String name;

    ConstValues(String name) {
        this.name = name;
    }
}