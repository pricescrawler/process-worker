package io.github.scafer.prices.crawler.examples.service;

import io.github.scafer.prices.crawler.content.repository.product.ProductDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private final ProductDataRepository productDataRepository;

    public ExampleService(ProductDataRepository productDataRepository) {
        this.productDataRepository = productDataRepository;
    }

    public String helloWorld(String str) {
        return String.format("TestService.helloWorld: %s", str);
    }

    public int numberOfProducts() {
        return productDataRepository.findAll().size();
    }
}
