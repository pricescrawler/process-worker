package io.github.pricescrawler.service.cmpi;

import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ClearMergedProductIncidentsService {
    private final ProductIncidentDataRepository productIncidentDataRepository;

    public ClearMergedProductIncidentsService(ProductIncidentDataRepository productIncidentDataRepository) {
        this.productIncidentDataRepository = productIncidentDataRepository;
    }

    public void run() {
        var mergedIncidents = productIncidentDataRepository.findAll().stream().filter(ProductIncidentDao::isMerged).toList();
        productIncidentDataRepository.deleteAll(mergedIncidents);
    }
}
