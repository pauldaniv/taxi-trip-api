package com.pauldaniv.promotion.yellowtaxi.persistence;

import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.model.TripResponse;

import java.util.List;

public interface TaxiTripDAO {
    List<TaxiTrip> getAll();
}
