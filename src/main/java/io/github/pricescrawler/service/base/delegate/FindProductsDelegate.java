package io.github.pricescrawler.service.base.delegate;

import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class FindProductsDelegate implements JavaDelegate {
    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var products = productHistoryRepository.findAll();
        var productsIds = products.stream().map(ProductHistoryDao::getId).toList();

        delegateExecution.setVariable("productsIds", Spin.JSON(productsIds));
        log.info("Products List Size: {}", products.size());
    }
}
