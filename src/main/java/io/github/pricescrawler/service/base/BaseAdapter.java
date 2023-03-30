package io.github.pricescrawler.service.base;

import io.github.pricescrawler.service.base.delegate.FindProductsDelegate;
import io.github.pricescrawler.service.base.delegate.IncidentsLoopDelegate;
import io.github.pricescrawler.service.base.delegate.ProductsLoopDelegate;
import io.github.pricescrawler.service.base.delegate.SaveProductDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseAdapter {
    @Bean
    public FindProductsDelegate findProductsDelegate() {
        return new FindProductsDelegate();
    }

    @Bean
    public SaveProductDelegate saveProductDelegate() {
        return new SaveProductDelegate();
    }

    @Bean
    public IncidentsLoopDelegate incidentsLoopDelegate() {
        return new IncidentsLoopDelegate();
    }

    @Bean
    public ProductsLoopDelegate productsLoopDelegate() {
        return new ProductsLoopDelegate();
    }
}
