package com.pauldaniv.promotion.yellowtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxiTripListener {

    private final TaxiTripDAO taxiTripDAO;
    private final ObjectMapper objectMapper;

    @KafkaListener(id = "main", topics = {"taxi-trips"})
    public void consumeTrip(final String tripRequest) {
        try {
            final TaxiTrip taxiTrip = objectMapper.readValue(tripRequest, TaxiTrip.class);
            taxiTripDAO.store(taxiTrip);
            log.info("msg=received_trip trip={}", taxiTrip);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse object. Raw data: {}. Reason: {}", tripRequest, e.getMessage(), e);
        }
    }
}
