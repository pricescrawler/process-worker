package io.github.pricescrawler.service.cpip.delegate;

import io.github.pricescrawler.service.cpip.ClearProductIncidentsService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class CloseIncidentDelegate implements JavaDelegate {
    @Autowired
    private ClearProductIncidentsService clearProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var productId = delegateExecution.getVariable("productId");
        clearProductIncidentsService.closeIncident(productId.toString());
    }
}
