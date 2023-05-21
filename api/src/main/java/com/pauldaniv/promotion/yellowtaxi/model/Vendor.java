package com.pauldaniv.promotion.yellowtaxi.model;

import lombok.Builder;
import lombok.Data;

/**
 * Entity class for a single taxi trip
 * Not all fields are present yet
 */
@Data
@Builder
public class Vendor {
    private Long id;
    private String name;
}
