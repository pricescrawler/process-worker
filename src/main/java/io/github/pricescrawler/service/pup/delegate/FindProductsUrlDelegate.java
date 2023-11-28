package io.github.pricescrawler.service.pup.delegate;

import io.github.pricescrawler.content.repository.product.history.ProductHistoryDataRepository;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.github.pricescrawler.config.ConstValues.PRODUCTS_IDS;

@Log4j2
public class FindProductsUrlDelegate implements JavaDelegate {
    @Autowired
    private ProductHistoryDataRepository productHistoryDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var products = productHistoryDataRepository.findAll();

        var productIds = products.stream().map(productDao ->
                String.format("/%s/%s/%s", productDao.getLocale(), productDao.getCatalog(), encodeValue(productDao.getProductUrl()))
        ).toList();

        delegateExecution.setVariable(PRODUCTS_IDS.getName(), Spin.JSON(productIds));
        log.info("Product Urls List Size: {}", productIds.size());
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
