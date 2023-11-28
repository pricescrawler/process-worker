package io.github.pricescrawler.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConverterConfig {
    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory mongoFactory) {
        var dbRefResolver = new DefaultDbRefResolver(mongoFactory);
        var mongoMappingContext = new MongoMappingContext();

        var mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setMapKeyDotReplacement(".");

        return mongoConverter;
    }
}
