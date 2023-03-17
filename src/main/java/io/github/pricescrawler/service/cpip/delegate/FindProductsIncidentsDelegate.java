package io.github.pricescrawler.service.cpip.delegate;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.service.cpip.ClearProductIncidentsService;
import io.github.pricescrawler.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class FindProductsIncidentsDelegate implements JavaDelegate {
    @Autowired
    private ClearProductIncidentsService clearProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws JsonProcessingException {
        var incidents = clearProductIncidentsService.findAllProductIncidents();
        var incidentsIds = incidents.stream().map(ProductIncidentDao::getId).toList();
        var json = JsonUtils.convertObjectToString(incidents);
        delegateExecution.setVariable("incidents", Spin.JSON(json));
        delegateExecution.setVariable("incidentsIds", Spin.JSON(incidentsIds));
    }
}
