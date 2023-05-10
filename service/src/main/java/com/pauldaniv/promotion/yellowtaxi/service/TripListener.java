package com.pauldaniv.promotion.yellowtaxi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TripListener {

    @KafkaListener(id = "test", topics = {"testTopic"})
    public void consumeTrip(String tripRequest) {
        log.info("msg=received_trip trip={}", tripRequest);
    }
}
