package io.github.pricescrawler.service.cpip.delegate;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.service.cpip.ClearProductIncidentsService;
import io.github.pricescrawler.utils.JsonUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

import static io.github.pricescrawler.config.ConstValues.INCIDENTS_IDS;

public class FindProductsIncidentsDelegate implements JavaDelegate {
    @Autowired
    private ClearProductIncidentsService clearProductIncidentsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws JsonProcessingException {
        var incidents = clearProductIncidentsService.findAllProductIncidents();
        var comparator = Comparator.comparingInt(obj -> ((ProductIncidentDao) obj).getHits());
        var incidentsIds = incidents.stream().sorted(comparator.reversed()).map(ProductIncidentDao::getId).toList();
        var json = JsonUtils.convertObjectToString(incidents);
        delegateExecution.setVariable("incidents", Spin.JSON(json));
        delegateExecution.setVariable(INCIDENTS_IDS.getName(), Spin.JSON(incidentsIds));
    }
}
