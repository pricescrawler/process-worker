package io.github.scafer.prices.crawler.service.pdcp.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scafer.prices.crawler.content.common.dao.product.ProductDao;
import io.github.scafer.prices.crawler.content.repository.product.ProductDataRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveProductDelegate implements JavaDelegate {

    @Autowired
    private ProductDataRepository productDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductDao.class);
        productDataRepository.save(productJson);
    }
}
