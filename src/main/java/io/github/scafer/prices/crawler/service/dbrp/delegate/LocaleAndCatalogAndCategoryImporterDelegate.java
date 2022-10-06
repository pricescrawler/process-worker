package io.github.scafer.prices.crawler.service.dbrp.delegate;

import io.github.scafer.prices.crawler.service.dbrp.DatabaseRestoreService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class LocaleAndCatalogAndCategoryImporterDelegate implements JavaDelegate {
    @Autowired
    private DatabaseRestoreService databaseRestoreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        databaseRestoreService.localeRestore();
        databaseRestoreService.catalogRestore();
        databaseRestoreService.categoryRestore();
    }
}
