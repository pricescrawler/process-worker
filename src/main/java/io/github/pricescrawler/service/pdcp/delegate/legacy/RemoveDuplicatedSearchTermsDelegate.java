package io.github.pricescrawler.service.pdcp.delegate.legacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.product.ProductDao;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.util.stream.Collectors;

public class RemoveDuplicatedSearchTermsDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductDao.class);

        var searchTerms = productJson.getSearchTerms().stream().map(value -> value.trim().toLowerCase())
                .distinct().collect(Collectors.toList());
        searchTerms.removeIf(String::isBlank);

        productJson.setSearchTerms(searchTerms);
        delegateExecution.setVariable("productData", Spin.JSON(productJson));
    }
}
