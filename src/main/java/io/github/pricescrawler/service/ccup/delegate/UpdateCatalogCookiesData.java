package io.github.pricescrawler.service.ccup.delegate;

import io.github.pricescrawler.content.repository.catalog.CatalogDataRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.pricescrawler.config.ConstValues.CATALOG;
import static io.github.pricescrawler.config.ConstValues.COOKIES;

public class UpdateCatalogCookiesData implements JavaDelegate {
    @Autowired
    private CatalogDataRepository catalogDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var optionalCatalog = catalogDataRepository.findById(delegateExecution.getVariable(CATALOG.getName()).toString());

        if (optionalCatalog.isPresent()) {
            var catalog = optionalCatalog.get();
            catalog.getData().put("requestCookie", delegateExecution.getVariable(COOKIES.getName()).toString()
                    .replaceAll("\"\\[", "").replaceAll("\"\\]", ""));
            catalogDataRepository.save(optionalCatalog.get());
        }
    }
}
