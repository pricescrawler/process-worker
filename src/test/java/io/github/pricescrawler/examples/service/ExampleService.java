package io.github.pricescrawler.examples.service;

import io.github.pricescrawler.content.repository.product.ProductDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private final ProductDataRepository productDataRepository;

    public ExampleService(ProductDataRepository productDataRepository) {
        this.productDataRepository = productDataRepository;
    }

    public String helloWorld(String value) {
        return String.format("TestService.helloWorld: %s", value);
    }

    public int numberOfProducts() {
        return productDataRepository.findAll().size();
    }
}