package com.pauldaniv.promotion.yellowtaxi.controller;

import com.pauldaniv.promotion.yellowtaxi.model.TripRequest;
import com.pauldaniv.promotion.yellowtaxi.model.TripResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/v1/trips")
public class TripController {
    @GetMapping
    public ResponseEntity<List<TripResponse>> handleTripEvent(final TripRequest request) {
        log.info("Received request: {}", request);
        return ResponseEntity.ok(List.of(TripResponse.builder()
                .id(new Random().nextLong(10000))
                .build()));
    }
}
