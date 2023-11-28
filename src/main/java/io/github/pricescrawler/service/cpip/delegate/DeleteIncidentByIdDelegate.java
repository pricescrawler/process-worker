package io.github.pricescrawler.service.cpip.delegate;

import io.github.pricescrawler.service.cpip.ClearProductIncidentsService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.pricescrawler.config.ConstValues.PRODUCT_ID;

public class DeleteIncidentByIdDelegate implements JavaDelegate {
    @Autowired
    private ClearProductIncidentsService clearProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productId = delegateExecution.getVariable(PRODUCT_ID.getName());
        clearProductIncidentsService.deleteIncident(productId.toString());
    }
}
