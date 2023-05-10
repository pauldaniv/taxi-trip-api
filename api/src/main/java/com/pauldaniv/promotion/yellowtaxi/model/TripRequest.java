package com.pauldaniv.promotion.yellowtaxi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
    public class TripRequest {
    private String id;
    private Long distance;
}
