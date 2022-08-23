package io.github.scafer.prices.crawler.config;

import lombok.Getter;

@Getter
public enum ConstValues {
    IGNORE_URL_INCIDENT("MERGE_URL_INCIDENT");

    private final String name;

    ConstValues(String name) {
        this.name = name;
    }
}