package io.github.pricescrawler.service.base.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveProductDelegate implements JavaDelegate {
    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductHistoryDao.class);
        productHistoryRepository.save(productJson);
    }
}
