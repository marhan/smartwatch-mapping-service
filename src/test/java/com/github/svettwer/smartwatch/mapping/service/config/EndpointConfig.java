package com.github.svettwer.smartwatch.mapping.service.config;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jdbc.server.JdbcServer;
import com.consol.citrus.kafka.embedded.EmbeddedKafkaServer;
import com.consol.citrus.kafka.embedded.EmbeddedKafkaServerBuilder;
import com.consol.citrus.kafka.endpoint.KafkaEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {

    @Bean
    public HttpClient httpClient(){
        return CitrusEndpoints.http()
                .client()
                .requestUrl("http://localhost:8080")
                .build();
    }

    @Bean
    public EmbeddedKafkaServer embeddedKafkaServer() {
        return new EmbeddedKafkaServerBuilder()
                .kafkaServerPort(9092)
                .topics("pairing.temporary", "pairing.result")
                .build();
    }

    @Bean
    public KafkaEndpoint temporaryPairingEndpoint() {
        return CitrusEndpoints
                .kafka()
                .asynchronous()
                .server("localhost:9092")
                .topic("pairing.temporary")
                .build();
    }

    @Bean
    public KafkaEndpoint pairingResultEndpoint() {
        return CitrusEndpoints
                .kafka()
                .asynchronous()
                .server("localhost:9092")
                .topic("pairing.result")
                .build();
    }

    @Bean
    public JdbcServer jdbcServer() {
        return CitrusEndpoints
                .jdbc()
                .server()
                .host("localhost")
                .databaseName("smartwatch_mappings")
                .port(13306)
                .autoStart(true)
                .build();
    }
}
