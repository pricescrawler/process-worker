package io.github.pricescrawler.service.base.delegate;

import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryDataRepository;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.pricescrawler.config.ConstValues.PRODUCTS_IDS;

@Log4j2
public class FindProductsDelegate implements JavaDelegate {
    @Autowired
    private ProductHistoryDataRepository productHistoryDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var products = productHistoryDataRepository.findAll();
        var productsIds = products.stream().map(ProductHistoryDao::getId).toList();

        delegateExecution.setVariable(PRODUCTS_IDS.getName(), Spin.JSON(productsIds));
        log.info("Products List Size: {}", products.size());
    }
}
