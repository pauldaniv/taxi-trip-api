package com.pauldaniv.promotion.yellowtaxi.model;

import lombok.Builder;
import lombok.Data;

/**
 * Entity class for a single taxi trip
 * Not all fields are present yet
 */
@Data
@Builder
public class TaxiTrip {
    private String id;
    private Long distance;
}
