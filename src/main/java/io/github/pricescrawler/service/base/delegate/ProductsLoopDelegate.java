package io.github.pricescrawler.service.base.delegate;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

@Log4j2
public class ProductsLoopDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int numberOfRequests = Integer.parseInt(delegateExecution.getVariable("numberOfRequests").toString());
        var productsIdList = Spin.JSON(delegateExecution.getVariable("productsIds"));

        log.info("Number of Products: {}", productsIdList.elements().size());
        var currentProduct = productsIdList.elements().stream().findFirst();

        if (currentProduct.isPresent()) {
            var productId = currentProduct.get().value();
            productsIdList.remove(productId);

            delegateExecution.setVariable("productId", productId);
            delegateExecution.setVariable("productsIds", productsIdList);

            log.info("ProductId: {}", productId);
        }

        if (numberOfRequests > 1000) {
            numberOfRequests = 0;
            delegateExecution.setVariable("waitTimer", "PT5S");
        } else {
            numberOfRequests++;
            delegateExecution.setVariable("waitTimer", "PT0S");
        }

        delegateExecution.setVariable("numberOfRequests", numberOfRequests);
        delegateExecution.setVariable("hasNextProduct", currentProduct.isPresent());
    }
}
