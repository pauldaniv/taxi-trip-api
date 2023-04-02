package com.pauldaniv.promotion.yellowtaxi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripResponse {
    private String id;
    private Long distance;
}
