package com.pauldaniv.promotion.yellowtaxi.controller;

import com.pauldaniv.promotion.yellowtaxi.model.TripResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/trips")
public class TripController {
    @GetMapping
    public ResponseEntity<List<TripResponse>> health() {
        return ResponseEntity.ok(List.of(TripResponse.builder()
                .id("test6")
                .distance(123L)
                .build()));
    }
}
