package io.github.scafer.prices.crawler.service.pdcp;

import io.github.scafer.prices.crawler.service.pdcp.delegate.FindProductsDelegate;
import io.github.scafer.prices.crawler.service.pdcp.delegate.SaveProductDelegate;
import io.github.scafer.prices.crawler.service.pdcp.delegate.legacy.CheckAndAddQuantityToLastPrice;
import io.github.scafer.prices.crawler.service.pdcp.delegate.legacy.RemoveDuplicatedSearchTermsDelegate;
import io.github.scafer.prices.crawler.service.pdcp.delegate.legacy.RemoveEmptyPrices;
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
    public CheckAndAddQuantityToLastPrice checkAndAddQuantityToLastPrice() {
        return new CheckAndAddQuantityToLastPrice();
    }

    @Bean
    public RemoveDuplicatedSearchTermsDelegate removeDuplicatedSearchTermsDelegate() {
        return new RemoveDuplicatedSearchTermsDelegate();
    }

    @Bean
    public RemoveEmptyPrices removeEmptyPrices() {
        return new RemoveEmptyPrices();
    }
}
