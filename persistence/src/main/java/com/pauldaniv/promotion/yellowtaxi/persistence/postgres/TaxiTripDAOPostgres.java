package com.pauldaniv.promotion.yellowtaxi.persistence.postgres;

import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.List;

import static com.pauldaniv.promotion.yellowtaxi.jooq.Tables.TAXI_TRIPS;

@RequiredArgsConstructor
public class TaxiTripDAOPostgres implements TaxiTripDAO {
    private final DSLContext db;

    @Override
    public List<TaxiTrip> getAll() {
        return db.selectFrom(TAXI_TRIPS)
                .fetchInto(TaxiTrip.class);
    }
}
