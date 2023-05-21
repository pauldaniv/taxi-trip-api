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
    private Long vendorId;
    private Long rateCodeId;
    private Long paymentTypeId;
    private String tpepPickupDatetime;
    private String tpepDropoffDatetime;
    private Integer dropoffMonth;
    private Integer passengerCount;
    private Double tripDistance;
    private Integer pulocationid;
    private Integer dolocationid;
    private Boolean storeAndFwdFlag;
    private BigDecimal fareAmount;
    private BigDecimal extra;
    private BigDecimal mtaTax;
    private BigDecimal improvementSurcharge;
    private BigDecimal tipAmount;
    private BigDecimal tollsAmount;
    private BigDecimal totalAmount;
}
