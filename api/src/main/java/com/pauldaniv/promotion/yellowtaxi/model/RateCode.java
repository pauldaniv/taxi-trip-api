package com.pauldaniv.promotion.yellowtaxi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Entity class for a single taxi trip
 */
@Data
@Builder
public class RateCode {
    private Long id;
    private String name;
}
