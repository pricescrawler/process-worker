package io.github.pricescrawler.examples.service;

import io.github.pricescrawler.content.repository.product.history.ProductHistoryDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ProductHistoryDataRepository productHistoryDataRepository;

    public String helloWorld(String value) {
        return String.format("TestService.helloWorld: %s", value);
    }

    public int numberOfProducts() {
        return productHistoryDataRepository.findAll().size();
    }
}
