package com.pauldaniv.promotion.yellowtaxi.api;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class TaxiTripAPITest {

    @Test
    public void healthCheck() {
        log.info("msg=tests_health_check");
    }
}
