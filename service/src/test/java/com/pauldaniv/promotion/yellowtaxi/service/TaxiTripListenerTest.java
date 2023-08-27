package com.pauldaniv.promotion.yellowtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
public class TaxiTripListenerTest {

    @Mock
    private TaxiTripDAO taxiTripDAO;

    private TaxiTripListener taxiTripListener;

    @BeforeTest
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taxiTripListener = new TaxiTripListener(taxiTripDAO);
    }

    @Test
    public void consumesTaxiTripSuccessfully() {

        final TaxiTrip taxiTrip = TaxiTrip.builder().build();

        when(taxiTripDAO.store(taxiTrip))
                .thenReturn(taxiTrip);

        taxiTripListener.consumeTrip(taxiTrip);
        verify(taxiTripDAO, only()).store(taxiTrip);
    }

    @Test
    public void consumesTaxiTripExceptionally() {
        final TaxiTrip taxiTrip = TaxiTrip.builder().build();

        when(taxiTripDAO.store(taxiTrip)).thenThrow(new RuntimeException("test"));
        taxiTripListener.consumeTrip(taxiTrip);
    }

    @AfterMethod
    public void teardown() {
        Mockito.reset(taxiTripDAO);
    }
}
