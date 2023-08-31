package com.pauldaniv.promotion.yellowtaxi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;

import org.springframework.kafka.transaction.KafkaTransactionManager;

@Configuration
public class TaxiTripConfig {

    @Bean
    public KafkaTransactionManager<?, ?> kafkaTransactionManager(ProducerFactory<?, ?> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("taxi-trips")
                .partitions(2)
                .replicas(3)
                .build();
    }
}
