package io.github.pricescrawler.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {
    @Value("${database.connectionString:}")
    private String connectionString;

    private static DataSourceBuilder<?> getDataSourceBuilder(URI dbUri) {
        var username = dbUri.getUserInfo().split(":")[0];
        var password = dbUri.getUserInfo().split(":")[1];
        var dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
        var driverClassName = "org.postgresql.Driver";

        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder;
    }

    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        if (!connectionString.isEmpty()) {
            var dbUri = new URI(connectionString);
            var dataSourceBuilder = getDataSourceBuilder(dbUri);
            return dataSourceBuilder.build();
        } else {
            var dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url("jdbc:h2:file:./camunda-h2-database");
            return dataSourceBuilder.build();
        }
    }
}
