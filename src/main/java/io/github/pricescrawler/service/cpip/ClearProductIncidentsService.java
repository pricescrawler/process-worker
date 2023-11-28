package io.github.pricescrawler.service.cpip;

import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryDataRepository;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataRepository;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClearProductIncidentsService {
    private final ProductHistoryDataRepository productHistoryDataRepository;
    private final ProductIncidentDataRepository productIncidentDataRepository;
    private final ProductIncidentDataService productIncidentDataService;

    public List<ProductIncidentDao> findAllProductIncidents() {
        return productIncidentDataRepository.findAll();
    }

    public Optional<ProductHistoryDao> findProduct(String key) {
        return productHistoryDataRepository.findById(key);
    }

    public Optional<ProductIncidentDao> findProductIncident(String key) {
        return productIncidentDataRepository.findById(key);
    }

    public void closeAndMergeIncident(String key) {
        productIncidentDataService.closeIncident(key, true);
    }

    public boolean checkIfIncidentWarMerged(String key) {
        var optionalProduct = productHistoryDataRepository.findById(key);
        var optionalIncident = productIncidentDataRepository.findById(key);

        if (optionalProduct.isPresent() && optionalIncident.isPresent()) {
            var product = optionalProduct.get();
            var incident = optionalIncident.get();

            for (var data : incident.getProducts()) {
                var price = product.getPrices().stream().filter(value -> value.getDate().equals(data.getDate())).findFirst();

                if (price.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void deleteIncident(String key) {
        productIncidentDataRepository.deleteById(key);
    }

    public void closeIncident(String key) {
        productIncidentDataService.closeIncident(key, false);
    }
}
