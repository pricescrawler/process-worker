package io.github.pricescrawler.service.pdcp.delegate;

import io.github.pricescrawler.content.common.dao.product.ProductDao;
import io.github.pricescrawler.content.repository.product.ProductDataRepository;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class FindProductsDelegate implements JavaDelegate {
    @Autowired
    private ProductDataRepository productDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var products = productDataRepository.findAll();
        var productsIds = products.stream().map(ProductDao::getId).toList();

        delegateExecution.setVariable("productsIds", Spin.JSON(productsIds));
        log.info("Products List Size: {}", products.size());
    }
}
