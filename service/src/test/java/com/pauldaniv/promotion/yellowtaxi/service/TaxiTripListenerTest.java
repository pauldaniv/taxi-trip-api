package com.pauldaniv.promotion.yellowtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
public class TaxiTripListenerTest {

    @Mock
    private TaxiTripDAO taxiTripDAO;
    @Mock
    private ObjectMapper objectMapper;

    private TaxiTripListener taxiTripListener;

    @BeforeTest
    public void setup() {
        MockitoAnnotations.openMocks(this);
        taxiTripListener = new TaxiTripListener(taxiTripDAO, objectMapper);
    }

    @Test
    public void consumesTaxiTripSuccessfully() throws JsonProcessingException {

        final String tripData = "aoeu";
        final TaxiTrip taxiTrip = TaxiTrip.builder().build();

        when(objectMapper.readValue(tripData, TaxiTrip.class))
                .thenReturn(taxiTrip);

        when(taxiTripDAO.store(taxiTrip))
                .thenReturn(taxiTrip);

        taxiTripListener.consumeTrip(tripData);
        verify(objectMapper, only()).readValue(tripData, TaxiTrip.class);
        verify(taxiTripDAO, only()).store(taxiTrip);
    }

    @Test
    public void consumesTaxiTripExceptionally() throws JsonProcessingException {

        final String tripData = "aoeu";

        when(objectMapper.readValue(tripData, TaxiTrip.class))
                .thenThrow(new JsonMappingException(null, "test exception"));

        taxiTripListener.consumeTrip(tripData);
        verify(objectMapper, only()).readValue(tripData, TaxiTrip.class);
    }

    @AfterMethod
    public void teardown() {
        Mockito.reset(taxiTripDAO, objectMapper);
    }
}
