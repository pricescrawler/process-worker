package io.github.pricescrawler.service.cpip;

import io.github.pricescrawler.content.common.dao.product.ProductDao;
import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.content.repository.product.ProductDataRepository;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataRepository;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClearProductIncidentsService {

    private final ProductDataRepository productDataRepository;
    private final ProductIncidentDataRepository productIncidentDataRepository;
    private final ProductIncidentDataService productIncidentDataService;

    public ClearProductIncidentsService(ProductDataRepository productDataRepository, ProductIncidentDataRepository productIncidentDataRepository, ProductIncidentDataService productIncidentDataService) {
        this.productDataRepository = productDataRepository;
        this.productIncidentDataRepository = productIncidentDataRepository;
        this.productIncidentDataService = productIncidentDataService;
    }

    public List<ProductIncidentDao> findAllProductIncidents() {
        return productIncidentDataRepository.findAll();
    }

    public Optional<ProductDao> findProduct(String key) {
        return productDataRepository.findById(key);
    }

    public Optional<ProductIncidentDao> findProductIncident(String key) {
        return productIncidentDataRepository.findById(key);
    }

    public void closeAndMergeIncident(String key) {
        productIncidentDataService.closeIncident(key, true);
    }

    public boolean checkIfIncidentWarMerged(String key) {
        var optionalProduct = productDataRepository.findById(key);
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
