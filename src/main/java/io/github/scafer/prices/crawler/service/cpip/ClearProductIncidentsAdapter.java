package io.github.scafer.prices.crawler.service.cpip;

import io.github.scafer.prices.crawler.service.cpip.delegate.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClearProductIncidentsAdapter {
    @Bean
    public CheckIfIncidentWasMergedDelegate checkIfIncidentWasMergedDelegate() {
        return new CheckIfIncidentWasMergedDelegate();
    }

    @Bean
    public DeleteIncidentByIdDelegate deleteIncidentByIdDelegate() {
        return new DeleteIncidentByIdDelegate();
    }

    @Bean
    public DescribeIncidentDelegate describeIncidentDelegate() {
        return new DescribeIncidentDelegate();
    }

    @Bean
    public IncidentsLoopDelegate incidentsLoopDelegate() {
        return new IncidentsLoopDelegate();
    }

    @Bean
    public FindProductsIncidentsDelegate findIncidentsDelegate() {
        return new FindProductsIncidentsDelegate();
    }

    @Bean
    public MergeIncidentDelegate mergeIncidentDelegate() {
        return new MergeIncidentDelegate();
    }

    @Bean
    public FindProductByIdDelegate findProductByIdDelegate() {
        return new FindProductByIdDelegate();
    }

    @Bean
    public FindProductIncidentByIdDelegate findProductIncidentByIdDelegate() {
        return new FindProductIncidentByIdDelegate();
    }
}
