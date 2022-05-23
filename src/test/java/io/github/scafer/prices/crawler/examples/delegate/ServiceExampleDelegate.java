package io.github.scafer.prices.crawler.examples.delegate;

import io.github.scafer.prices.crawler.examples.service.ExampleService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class ServiceExampleDelegate implements JavaDelegate {
    @Autowired
    private ExampleService testService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info(testService.helloWorld("Java Delegate"));
        log.info("Products size: {}", testService.numberOfProducts());
    }
}
