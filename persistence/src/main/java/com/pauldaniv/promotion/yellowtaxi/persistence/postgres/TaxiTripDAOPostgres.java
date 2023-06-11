package com.pauldaniv.promotion.yellowtaxi.persistence.postgres;

import com.pauldaniv.promotion.yellowtaxi.jooq.tables.records.TaxiTripsRecord;
import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pauldaniv.promotion.yellowtaxi.jooq.Tables.TAXI_TRIPS;

@Repository
@RequiredArgsConstructor
public class TaxiTripDAOPostgres implements TaxiTripDAO {
    private final DSLContext db;

    @Override
    public List<TaxiTrip> getAll() {
        return db.selectFrom(TAXI_TRIPS)
                .fetchInto(TaxiTrip.class);
    }

    @Override
    public TaxiTrip store(final TaxiTrip taxiTrip) {
        final TaxiTripsRecord taxiTripsRecord = db.newRecord(TAXI_TRIPS);
        taxiTripsRecord.from(taxiTrip);
        return db.insertInto(TAXI_TRIPS)
                .set(taxiTripsRecord)
                .returning()
                .fetchOneInto(TaxiTrip.class);
    }
}
