package io.github.pricescrawler.service.pdcp;

import io.github.pricescrawler.service.pdcp.delegate.FindProductsDelegate;
import io.github.pricescrawler.service.pdcp.delegate.SaveProductDelegate;
import io.github.pricescrawler.service.pdcp.delegate.legacy.RemoveDuplicatedSearchTermsDelegate;
import io.github.pricescrawler.service.pdcp.delegate.legacy.RemoveEmptyPricesDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductDataCleanerAdapter {
    @Bean
    public FindProductsDelegate findProductsDelegate() {
        return new FindProductsDelegate();
    }

    @Bean
    public SaveProductDelegate saveProductDelegate() {
        return new SaveProductDelegate();
    }

    @Bean
    public RemoveDuplicatedSearchTermsDelegate removeDuplicatedSearchTermsDelegate() {
        return new RemoveDuplicatedSearchTermsDelegate();
    }

    @Bean
    public RemoveEmptyPricesDelegate removeEmptyPricesDelegate() {
        return new RemoveEmptyPricesDelegate();
    }
}
