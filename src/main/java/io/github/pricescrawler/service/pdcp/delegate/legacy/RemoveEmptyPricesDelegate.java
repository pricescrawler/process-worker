package io.github.pricescrawler.service.pdcp.delegate.legacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.product.ProductDao;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

@Log4j2
public class RemoveEmptyPricesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productData = delegateExecution.getVariable("productData");
        var productJson = new ObjectMapper().readValue(productData.toString(), ProductDao.class);
        var prices = productJson.getPrices();
        var quantity = prices.size();

        prices.removeIf(price -> (price.getRegularPrice() == null || price.getRegularPrice().isEmpty()) && (price.getCampaignPrice() == null || price.getCampaignPrice().isEmpty()));
        prices.removeIf(price -> price.getRegularPrice() != null && price.getCampaignPrice() != null && price.getRegularPrice().equals(price.getCampaignPrice()));

        if (quantity != prices.size()) {
            log.info("RemoveEmptyPrices - {}", productJson.getId());
        }

        productJson.setPrices(prices);
        delegateExecution.setVariable("productData", Spin.JSON(productJson));
    }
}
