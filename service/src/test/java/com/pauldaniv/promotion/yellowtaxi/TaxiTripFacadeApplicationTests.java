package com.pauldaniv.promotion.yellowtaxi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Slf4j
@SpringBootTest
public class TaxiTripFacadeApplicationTests extends AbstractTestNGSpringContextTests {

    @Test
    public void contextLoads() {
        log.info("msg=loaded_contexts");
    }
}
