package com.pauldaniv.promotion.yellowtaxi.persistence;


import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class TaxiTripDAOPostgresTest {
    private TaxiTripDAO taxiTripDAO;

    @Test
    public void getsRecordsSuccessfully() {
        log.info("msg=test_persistence");
    }
}
