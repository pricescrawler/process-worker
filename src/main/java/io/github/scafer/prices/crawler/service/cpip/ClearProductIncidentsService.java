package io.github.scafer.prices.crawler.service.cpip;

import io.github.scafer.prices.crawler.content.domain.repository.ProductDataRepository;
import io.github.scafer.prices.crawler.content.domain.repository.ProductIncidentDataRepository;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductDao;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductIncidentDao;
import io.github.scafer.prices.crawler.content.domain.service.ProductIncidentDataService;
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

            for (var data : incident.getNewProducts()) {
                var price = product.getPricesHistory().stream().filter(x -> x.getDate().equalsIgnoreCase(data.getDate())).findFirst();
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
}
