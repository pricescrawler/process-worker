package io.github.scafer.prices.crawler.service.pdcp.delegate.legacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductDao;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

@Log4j2
public class RemoveEmptyPrices implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductDao.class);
        var pricesHistory = productJson.getPricesHistory();
        var quantity = pricesHistory.size();

        pricesHistory.removeIf(price -> (price.getRegularPrice() == null || price.getRegularPrice().isEmpty()) && (price.getCampaignPrice() == null || price.getCampaignPrice().isEmpty()));
        pricesHistory.removeIf(price -> price.getRegularPrice() != null && price.getCampaignPrice() != null && price.getRegularPrice().equals(price.getCampaignPrice()));

        if (quantity != pricesHistory.size()) {
            log.info("RemoveEmptyPrices - {}", productJson.getId());
        }

        if (!pricesHistory.isEmpty()) {
            if (productJson.getCreated() == null || productJson.getCreated().isBlank()) {
                productJson.setCreated(pricesHistory.get(0).getDate());
            }

            if (productJson.getUpdated() == null || productJson.getUpdated().isBlank()) {
                productJson.setUpdated(pricesHistory.get(pricesHistory.size() - 1).getDate());
            }
        }

        productJson.setPricesHistory(pricesHistory);
        delegateExecution.setVariable("productData", Spin.JSON(productJson));
    }
}
