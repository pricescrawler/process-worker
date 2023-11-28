package io.github.pricescrawler.service.pdcp.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.util.stream.Collectors;

import static io.github.pricescrawler.config.ConstValues.PRODUCT_DATA;

public class RemoveDuplicatedSearchTermsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable(PRODUCT_DATA.getName());
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductHistoryDao.class);

        var searchTerms = productJson.getSearchTerms().stream().map(value -> value.trim().toLowerCase())
                .distinct().collect(Collectors.toList());
        searchTerms.removeIf(String::isBlank);

        productJson.setSearchTerms(searchTerms);
        delegateExecution.setVariable(PRODUCT_DATA.getName(), Spin.JSON(productJson));
    }
}
