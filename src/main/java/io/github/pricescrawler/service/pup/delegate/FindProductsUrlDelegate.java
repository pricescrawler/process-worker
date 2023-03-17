package io.github.pricescrawler.service.pup.delegate;

import io.github.pricescrawler.content.repository.product.ProductDataRepository;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Log4j2
public class FindProductsUrlDelegate implements JavaDelegate {
    @Autowired
    private ProductDataRepository productDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var products = productDataRepository.findAll();

        var productIds = products.stream().map(productDao ->
                String.format("/%s/%s/%s", productDao.getLocale(), productDao.getCatalog(), encodeValue(productDao.getProductUrl()))
        ).toList();


        delegateExecution.setVariable("productsIds", Spin.JSON(productIds));
        log.info("Product Urls List Size: {}", productIds.size());
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
