package com.pauldaniv.promotion.yellowtaxi.service;

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

    @KafkaListener(id = "main", topics = {"taxi-trips"})
    public void consumeTrip(final TaxiTrip taxiTrip) {
        try {
            taxiTripDAO.store(taxiTrip);
            log.info("msg=received_trip trip={}", taxiTrip);
        } catch (Exception e) {
            log.error("Failed to parse object. Raw data: {}. Reason: {}", taxiTrip, e.getMessage(), e);
        }
    }
}
