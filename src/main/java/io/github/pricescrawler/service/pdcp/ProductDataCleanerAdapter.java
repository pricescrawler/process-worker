package io.github.pricescrawler.service.pdcp;

import io.github.pricescrawler.service.pdcp.delegate.RemoveDuplicatedSearchTermsDelegate;
import io.github.pricescrawler.service.pdcp.delegate.RemoveEmptyPricesDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductDataCleanerAdapter {
    @Bean
    public RemoveDuplicatedSearchTermsDelegate removeDuplicatedSearchTermsDelegate() {
        return new RemoveDuplicatedSearchTermsDelegate();
    }

    @Bean
    public RemoveEmptyPricesDelegate removeEmptyPricesDelegate() {
        return new RemoveEmptyPricesDelegate();
    }
}
