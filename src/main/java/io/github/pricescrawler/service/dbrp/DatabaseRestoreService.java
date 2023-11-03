package io.github.pricescrawler.service.dbrp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pricescrawler.content.common.dao.catalog.CatalogDao;
import io.github.pricescrawler.content.common.dao.catalog.CategoryDao;
import io.github.pricescrawler.content.common.dao.catalog.LocaleDao;
import io.github.pricescrawler.content.common.dao.product.ProductHistoryDao;
import io.github.pricescrawler.content.common.dao.product.incident.ProductIncidentDao;
import io.github.pricescrawler.content.repository.catalog.CatalogDataRepository;
import io.github.pricescrawler.content.repository.catalog.CategoryDataRepository;
import io.github.pricescrawler.content.repository.catalog.LocaleDataRepository;
import io.github.pricescrawler.content.repository.product.history.ProductHistoryRepository;
import io.github.pricescrawler.content.repository.product.incident.ProductIncidentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseRestoreService {
    private final LocaleDataRepository localeDataRepository;
    private final CatalogDataRepository catalogDataRepository;
    private final CategoryDataRepository categoryDataRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final ProductIncidentDataRepository productIncidentDataRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public void localeRestore() throws IOException {
        var fileContent = parseFileToString("locales.json");
        var locales = mapper.readValue(fileContent, new TypeReference<List<LocaleDao>>() {
        });
        localeDataRepository.saveAll(locales);
    }

    public void categoryRestore() throws IOException {
        var fileContent = parseFileToString("categories.json");
        var categories = mapper.readValue(fileContent, new TypeReference<List<CategoryDao>>() {
        });
        categoryDataRepository.saveAll(categories);
    }

    public void catalogRestore() throws IOException {
        var fileContent = parseFileToString("catalogs.json");
        var catalogs = mapper.readValue(fileContent, new TypeReference<List<CatalogDao>>() {
        });
        catalogDataRepository.saveAll(catalogs);
    }

    public void productRestore() throws IOException {
        var fileContent = parseFileToString("products.json");
        var products = mapper.readValue(fileContent, new TypeReference<List<ProductHistoryDao>>() {
        });
        productHistoryRepository.saveAll(products);
    }

    public void productIncidentRestore() throws IOException {
        var fileContent = parseFileToString("products-incidents.json");
        var productsIncidents = mapper.readValue(fileContent, new TypeReference<List<ProductIncidentDao>>() {
        });
        productIncidentDataRepository.saveAll(productsIncidents);
    }

    private String parseFileToString(String filename) throws IOException {
        var location = "data/";
        var path = Paths.get(location + filename);
        return new String(Files.readAllBytes(path));
    }
}
