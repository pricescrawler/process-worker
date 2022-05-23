package io.github.scafer.prices.crawler.service.pdcp.delegate.legacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductDao;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

@Log4j2
public class CheckAndAddQuantityToLastPrice implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductDao.class);
        var prices = productJson.getPricesHistory();

        if (!prices.isEmpty()) {
            var lastPrice = prices.get(prices.size() - 1);

            if (lastPrice.getQuantity() == null) {
                prices.remove(lastPrice);
                lastPrice.setQuantity(productJson.getQuantity() != null && !productJson.getQuantity().isEmpty() ? productJson.getQuantity() : productJson.getName());
                prices.add(lastPrice);
                productJson.setPricesHistory(prices);
                log.info("Adding quantity to last price: {}", productJson.getId());
            }
        }

        delegateExecution.setVariable("productData", Spin.JSON(productJson));
    }
}
