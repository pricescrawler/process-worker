package io.github.scafer.prices.crawler.service.dbrp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scafer.prices.crawler.content.domain.repository.dao.LocaleDao;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductDao;
import io.github.scafer.prices.crawler.content.domain.repository.dao.ProductIncidentDao;
import io.github.scafer.prices.crawler.content.domain.service.data.CatalogDataService;
import io.github.scafer.prices.crawler.content.domain.service.data.ProductDataService;
import io.github.scafer.prices.crawler.content.domain.service.data.ProductIncidentDataService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DatabaseRestoreService {
    private final CatalogDataService catalogDataService;
    private final ProductDataService productDataService;
    private final ProductIncidentDataService productIncidentDataService;

    private final ObjectMapper mapper = new ObjectMapper();

    public DatabaseRestoreService(CatalogDataService catalogDataService, ProductDataService productDataService, ProductIncidentDataService productIncidentDataService) {
        this.catalogDataService = catalogDataService;
        this.productDataService = productDataService;
        this.productIncidentDataService = productIncidentDataService;
    }

    public void localeAndCatalogueRestore() throws IOException {
        var fileContent = parseFileToString("locales.json");
        var locales = mapper.readValue(fileContent, new TypeReference<List<LocaleDao>>() {
        });
        catalogDataService.uploadData(locales);
    }

    public void productRestore() throws IOException {
        var fileContent = parseFileToString("products.json");
        var products = mapper.readValue(fileContent, new TypeReference<List<ProductDao>>() {
        });
        productDataService.uploadData(products);
    }

    public void productIncidentRestore() throws IOException {
        var fileContent = parseFileToString("products-incidents.json");
        var productsIncidents = mapper.readValue(fileContent, new TypeReference<List<ProductIncidentDao>>() {
        });
        productIncidentDataService.uploadData(productsIncidents);
    }

    private String parseFileToString(String filename) throws IOException {
        var location = "data/";
        var path = Paths.get(location + filename);
        return new String(Files.readAllBytes(path));
    }
}
