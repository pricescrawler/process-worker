package io.github.pricescrawler.service.pup;

import io.github.pricescrawler.service.pup.delegate.FindProductsUrlDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUpdateAdapter {

    @Bean
    public FindProductsUrlDelegate findProductsUrlDelegate() {
        return new FindProductsUrlDelegate();
    }
}
