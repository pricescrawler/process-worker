package io.github.scafer.prices.crawler.service.cpip.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scafer.prices.crawler.config.ConstValues;
import io.github.scafer.prices.crawler.content.domain.repository.ProductIncidentDataRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class DescribeIncidentDelegate implements JavaDelegate {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String[] properties = new String[]{"name", "brand", "quantity", "description", "productUrl", "eanUpcList"};


    @Autowired
    private ProductIncidentDataRepository productIncidentDataRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var numberOfConflicts = 0;
        var diffOld = new StringBuilder();
        var diffNew = new StringBuilder();
        var incidentFields = new StringBuilder();

        var incidents = delegateExecution.getVariable("incidentsIds");
        var activeIncident = delegateExecution.getVariable("productIncidentData");
        var productData = delegateExecution.getVariable("productData");

        var incidentJson = mapper.readTree(incidents.toString());
        var activeIncidentJson = mapper.readTree(activeIncident.toString());
        var productJson = mapper.readTree(productData.toString());

        var newProducts = activeIncidentJson.get("newProducts");
        var incidentProperty = new ArrayList<String>();

        for (var node : newProducts) {
            for (var property : properties) {
                if (!productJson.get(property).asText().equalsIgnoreCase(node.get(property).asText())) {
                    numberOfConflicts++;
                    incidentProperty.add(property);
                    incidentFields.append(property).append(" | ");
                    diffOld.append(String.format("%s | ", productJson.get(property).asText()));
                    diffNew.append(String.format("%s | ", node.get(property).asText()));
                }
            }
        }

        var isProductUrlIncident = incidentProperty.stream().allMatch(value -> value.equalsIgnoreCase("productUrl"));
        var mergeUrlIncident = System.getenv(ConstValues.IGNORE_URL_INCIDENT.getName()) != null
                && Boolean.parseBoolean(System.getenv(ConstValues.IGNORE_URL_INCIDENT.getName()));

        if (isProductUrlIncident && mergeUrlIncident) {
            delegateExecution.setVariable("approveIncidentMerge", true);
        } else {
            delegateExecution.setVariable("approveIncidentMerge", false);
            delegateExecution.setVariable("incidentNumberOfConflictsDescribe", numberOfConflicts);
            delegateExecution.setVariable("incidentFieldsDescribe", Spin.JSON(incidentFields));
            delegateExecution.setVariable("incidentShowDiffOldDescribe", Spin.JSON(diffOld));
            delegateExecution.setVariable("incidentShowDiffNewDescribe", Spin.JSON(diffNew));
            delegateExecution.setVariable("incidentNumberOfPricesDescribe", newProducts.size());
            delegateExecution.setVariable("totalNumberOfIncidents", incidentJson.size());
        }
    }
}
