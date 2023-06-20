package com.pauldaniv.promotion.yellowtaxi.persistence;

import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;

import java.util.List;

public interface TaxiTripDAO {
    List<TaxiTrip> getAll();
    TaxiTrip store(TaxiTrip taxiTrip);
}
