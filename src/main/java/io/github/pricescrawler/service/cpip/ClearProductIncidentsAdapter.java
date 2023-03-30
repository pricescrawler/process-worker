package io.github.pricescrawler.service.cpip;

import io.github.pricescrawler.service.cpip.delegate.*;
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
    public FindProductsIncidentsDelegate findIncidentsDelegate() {
        return new FindProductsIncidentsDelegate();
    }

    @Bean
    public MergeIncidentDelegate mergeIncidentDelegate() {
        return new MergeIncidentDelegate();
    }

    @Bean
    public CloseIncidentDelegate closeIncidentDelegate() {
        return new CloseIncidentDelegate();
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
