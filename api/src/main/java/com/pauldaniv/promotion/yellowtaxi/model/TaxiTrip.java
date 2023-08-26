package com.pauldaniv.promotion.yellowtaxi.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity class for a single taxi trip
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaxiTrip {
    @Column(name = "id")
    private UUID id;
    @Column(name = "vendor_id")
    private Long vendorId;
    @Column(name = "rate_code_id")
    private Long rateCodeId;
    @Column(name = "payment_type_id")
    private Long paymentTypeId;
    @Column(name = "tpep_pickup_datetime")
    private Instant tPepPickupDatetime;
    @Column(name = "tpep_dropoff_datetime")
    private Instant tPepDropOffDatetime;
    @Column(name = "drop_off_year")
    private Integer dropOffYear;
    @Column(name = "drop_off_month")
    private Integer dropOffMonth;
    @Column(name = "drop_off_day")
    private Integer dropOffDay;
    @Column(name = "passenger_count")
    private Integer passengerCount;
    @Column(name = "trip_distance")
    private Double tripDistance;
    @Column(name = "pulocationid")
    private Integer puLocationId;
    @Column(name = "dolocationid")
    private Integer doLocationId;
    @Column(name = "store_and_fwd_flag")
    private Boolean storeAndFwdFlag;
    @Column(name = "fare_amount")
    private BigDecimal fareAmount;
    @Column(name = "extra")
    private BigDecimal extra;
    @Column(name = "mta_tax")
    private BigDecimal mtaTax;
    @Column(name = "improvement_surcharge")
    private BigDecimal improvementSurcharge;
    @Column(name = "tip_amount")
    private BigDecimal tipAmount;
    @Column(name = "tolls_amount")
    private BigDecimal tollsAmount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
}
