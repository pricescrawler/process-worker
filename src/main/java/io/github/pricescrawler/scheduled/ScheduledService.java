package io.github.pricescrawler.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduledService {
    public final RuntimeService runtimeService;

    @Value("${prices.crawler.process-worker.service.cron.catalog-cookies-update.catalogs}")
    private String[] catalogs;

    @Scheduled(cron = "${prices.crawler.process-worker.service.cron.clear-product-incidents:0 0 0 * * *}")
    public void startBackgroundService_clearProductIncidents() {
        log.info("Starting Background Service - Clear Product Incidents");
        runtimeService.startProcessInstanceByKey("clear-products-incidents-process-automated");
    }

    @Scheduled(cron = "${prices.crawler.process-worker.service.cron.catalog-cookies-update:0 0 0 * * *}")
    public void startBackgroundService_catalogCookiesUpdate() {
        log.info("Starting Background Service - Catalog Cookies Update");

        Arrays.stream(catalogs).toList().forEach(value -> {
            var properties = new HashMap<String, Object>();
            properties.put("catalog", value);

            runtimeService.startProcessInstanceByKey("catalog-update-cookies", properties);
        });
    }
}
