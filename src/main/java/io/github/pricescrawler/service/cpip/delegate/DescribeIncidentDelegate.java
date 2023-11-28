package io.github.pricescrawler.service.cpip.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.config.ConstValues;
import io.github.pricescrawler.utils.JsonUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.pricescrawler.config.ConstValues.INCIDENTS_IDS;
import static io.github.pricescrawler.config.ConstValues.PRODUCT_DATA;

public class DescribeIncidentDelegate implements JavaDelegate {
    public static final String DIF = "%s | ";

    private final ObjectMapper mapper = new ObjectMapper();
    private final String[] properties = new String[]{"name", "brand", "quantity", "description", "productUrl", "eanUpcList"};

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var numberOfConflicts = 0;
        var diffOld = new StringBuilder();
        var diffNew = new StringBuilder();
        var incidentFields = new StringBuilder();

        var incidents = delegateExecution.getVariable(INCIDENTS_IDS.getName());
        var activeIncident = delegateExecution.getVariable("productIncidentData");
        var productData = delegateExecution.getVariable(PRODUCT_DATA.getName());

        var incidentJson = mapper.readTree(incidents.toString());
        var activeIncidentJson = mapper.readTree(activeIncident.toString());
        var productJson = mapper.readTree(productData.toString());

        var products = activeIncidentJson.get("products");
        var incidentProperty = new ArrayList<String>();

        for (var node : products) {
            for (var property : properties) {
                if (node.get(property).isTextual() && !productJson.get(property).asText().equalsIgnoreCase(node.get(property).asText())) {
                    numberOfConflicts++;
                    incidentProperty.add(property);
                    incidentFields.append(property).append(" | ");
                    diffOld.append(String.format(DIF, productJson.get(property).asText()));
                    diffNew.append(String.format(DIF, node.get(property).asText()));
                } else if (node.get(property).isArray() && !productJson.get(property).asText().equalsIgnoreCase(node.get(property).asText(JsonUtils.convertObjectToString(node.get(property))))) {
                    numberOfConflicts++;
                    incidentProperty.add(property);
                    incidentFields.append(property).append(" | ");
                    diffOld.append(String.format(DIF, JsonUtils.convertObjectToString(productJson.get(property))));
                    diffNew.append(String.format(DIF, JsonUtils.convertObjectToString(node.get(property))));
                }
            }
        }

        var isNameIncident = incidentProperty.stream().allMatch(value -> value.equalsIgnoreCase("name"));
        var isBrandIncident = incidentProperty.stream().allMatch(value -> value.equalsIgnoreCase("brand"));
        var isEanUpcListIncident = incidentProperty.stream().allMatch(value -> value.equalsIgnoreCase("eanUpcList"));
        var isProductUrlIncident = incidentProperty.stream().allMatch(value -> value.equalsIgnoreCase("productUrl"));

        var mergeUrlIncident = System.getenv(ConstValues.IGNORE_URL_INCIDENT.getName()) != null
                && Boolean.parseBoolean(System.getenv(ConstValues.IGNORE_URL_INCIDENT.getName()));

        if (mergeUrlIncident &&
                (isProductUrlIncident || isEanUpcListIncident
                        || (isNameIncident
                        && equalWordsAndCountsIgnoringOrder(diffOld.toString(), diffNew.toString()))
                        || isNameAndBrandIncident(productJson, products)) || isBrandIncident) {
            delegateExecution.setVariable("approveIncidentMerge", true);
        } else {
            delegateExecution.setVariable("approveIncidentMerge", false);
            delegateExecution.setVariable("incidentNumberOfConflictsDescribe", numberOfConflicts);
            delegateExecution.setVariable("incidentFieldsDescribe", Spin.JSON(incidentFields));
            delegateExecution.setVariable("incidentShowDiffOldDescribe", Spin.JSON(diffOld));
            delegateExecution.setVariable("incidentShowDiffNewDescribe", Spin.JSON(diffNew));
            delegateExecution.setVariable("incidentNumberOfPricesDescribe", products.size());
            delegateExecution.setVariable("totalNumberOfIncidents", incidentJson.size());
        }
    }

    private boolean isNameAndBrandIncident(JsonNode productJson, JsonNode products) {
        if (products.isArray() && !products.isEmpty()) {
            return equalWordsAndCountsIgnoringOrder(productJson.get("name").asText(),
                    products.get(0).get("name").asText() + " " + products.get(0).get("brand").asText());
        }

        return false;
    }

    private boolean equalWordsAndCountsIgnoringOrder(String phrase, String phrase2) {
        var wordCount = Arrays.stream(phrase.toLowerCase().split("\\W+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var wordCount2 = Arrays.stream(phrase2.toLowerCase().split("\\W+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return wordCount.equals(wordCount2);
    }
}
