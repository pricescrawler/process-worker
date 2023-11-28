package io.github.pricescrawler.service.cmpip;

import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearMergedProductIncidentsService {
    private final ProductIncidentDataRepository productIncidentDataRepository;

    public void run() {
        var mergedIncidents = productIncidentDataRepository.findAll().stream().filter(ProductIncidentDao::isMerged).toList();
        productIncidentDataRepository.deleteAll(mergedIncidents);
    }
}
