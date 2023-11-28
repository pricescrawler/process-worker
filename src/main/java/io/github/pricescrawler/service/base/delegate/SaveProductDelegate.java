package io.github.pricescrawler.service.base.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryDataRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.pricescrawler.config.ConstValues.PRODUCT_DATA;

public class SaveProductDelegate implements JavaDelegate {
    @Autowired
    private ProductHistoryDataRepository productHistoryDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable(PRODUCT_DATA.getName());
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductHistoryDao.class);
        productHistoryDataRepository.save(productJson);
    }
}
