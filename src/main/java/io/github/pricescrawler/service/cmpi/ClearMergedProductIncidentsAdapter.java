package io.github.pricescrawler.service.cmpi;

import io.github.pricescrawler.service.cmpi.delegate.ClearMergedProductIncidentsDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClearMergedProductIncidentsAdapter {
    @Bean
    public ClearMergedProductIncidentsDelegate clearMergedProductIncidentsDelegate() {
        return new ClearMergedProductIncidentsDelegate();
    }
}
