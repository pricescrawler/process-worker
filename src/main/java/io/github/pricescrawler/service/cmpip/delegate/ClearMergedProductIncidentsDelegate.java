package io.github.pricescrawler.service.cmpip.delegate;

import io.github.pricescrawler.service.cmpip.ClearMergedProductIncidentsService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class ClearMergedProductIncidentsDelegate implements JavaDelegate {
    @Autowired
    private ClearMergedProductIncidentsService clearMergedProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        clearMergedProductIncidentsService.run();
    }
}
