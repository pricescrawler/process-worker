package io.github.pricescrawler.service.cpip.delegate;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

@Log4j2
public class IncidentsLoopDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var incidentsIdList = Spin.JSON(delegateExecution.getVariable("incidentsIds"));

        log.info("Number of Incidents: {}", incidentsIdList.elements().size());
        var currentIncident = incidentsIdList.elements().stream().findFirst();

        if (currentIncident.isPresent()) {
            var incidentId = currentIncident.get().value();
            incidentsIdList.remove(incidentId);

            delegateExecution.setVariable("productId", incidentId);
            delegateExecution.setVariable("incidentsIds", incidentsIdList);

            log.info("IncidentId: {}", incidentId);
        }

        delegateExecution.setVariable("hasNextProduct", currentIncident.isPresent());
    }
}
