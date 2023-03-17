package io.github.pricescrawler.service.cpip.delegate;

import io.github.pricescrawler.service.cpip.ClearProductIncidentsService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

public class FindProductByIdDelegate implements JavaDelegate {

    @Autowired
    private ClearProductIncidentsService clearProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productId = delegateExecution.getVariable("productId");
        var product = clearProductIncidentsService.findProduct(productId.toString());

        if (product.isPresent()) {
            delegateExecution.setVariable("productData", Spin.JSON(product.get()));
        } else {
            throw new NullPointerException(String.format("FindProductByIdDelegate: ProductId %s - product data is not present", productId));
        }
    }
}
