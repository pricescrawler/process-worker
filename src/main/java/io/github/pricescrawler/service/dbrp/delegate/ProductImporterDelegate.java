package io.github.pricescrawler.service.dbrp.delegate;

import io.github.pricescrawler.service.dbrp.DatabaseRestoreService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductImporterDelegate implements JavaDelegate {
    @Autowired
    private DatabaseRestoreService databaseRestoreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        databaseRestoreService.productRestore();
    }
}
