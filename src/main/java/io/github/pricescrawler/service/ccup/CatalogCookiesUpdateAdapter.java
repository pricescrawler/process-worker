package io.github.pricescrawler.service.ccup;

import io.github.pricescrawler.service.ccup.delegate.RequestCatalogCookies;
import io.github.pricescrawler.service.ccup.delegate.UpdateCatalogCookiesData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogCookiesUpdateAdapter {
    @Bean
    public RequestCatalogCookies requestCatalogCookies() {
        return new RequestCatalogCookies();
    }

    @Bean
    public UpdateCatalogCookiesData updateCatalogCookiesData() {
        return new UpdateCatalogCookiesData();
    }
}
