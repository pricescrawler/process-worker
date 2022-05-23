package io.github.scafer.prices.crawler.examples;

import io.github.scafer.prices.crawler.examples.delegate.LoggerDelegate;
import io.github.scafer.prices.crawler.examples.delegate.ServiceExampleDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelegateAdapter {

    @Bean
    public ServiceExampleDelegate serviceExampleDelegateBean() {
        return new ServiceExampleDelegate();
    }

    @Bean
    public LoggerDelegate loggerDelegateBean() {
        return new LoggerDelegate();
    }
}
