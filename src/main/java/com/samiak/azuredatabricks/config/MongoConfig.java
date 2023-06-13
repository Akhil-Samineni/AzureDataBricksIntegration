package com.samiak.azuredatabricks.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.samiak.azuredatabricks.respository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final String MONGODB_PREFIX ="mongodb+srv://";
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        String mongoUri = MONGODB_PREFIX + username + ":" +password + "@" + mongoHost + databaseName;
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .build());
    }
}
