package io.github.pricescrawler.service.ccup.delegate;

import io.github.pricescrawler.content.repository.catalog.CatalogDataRepository;
import io.github.pricescrawler.utils.SimpleSeleniumWebDriver;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.github.pricescrawler.config.ConstValues.CATALOG;
import static io.github.pricescrawler.config.ConstValues.COOKIES;

public class RequestCatalogCookies implements JavaDelegate {
    @Autowired
    private CatalogDataRepository catalogDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var catalog = catalogDataRepository.findById(delegateExecution.getVariable(CATALOG.getName()).toString());

        if (catalog.isPresent()) {
            var simpleSeleniumWebDriver = new SimpleSeleniumWebDriver();
            delegateExecution.setVariable(COOKIES.getName(),
                    Spin.JSON(List.of(simpleSeleniumWebDriver.getCookies(catalog.get().getBaseUrl()).toString())));
        }
    }
}
