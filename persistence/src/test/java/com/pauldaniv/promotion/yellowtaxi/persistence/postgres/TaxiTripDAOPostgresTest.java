package com.pauldaniv.promotion.yellowtaxi.persistence.postgres;

import com.pauldaniv.promotion.yellowtaxi.jooq.tables.records.TaxiTripsRecord;
import com.pauldaniv.promotion.yellowtaxi.model.TaxiTrip;
import com.pauldaniv.promotion.yellowtaxi.persistence.TaxiTripDAO;
import com.pauldaniv.promotion.yellowtaxi.persistence.config.DbConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.pauldaniv.promotion.yellowtaxi.jooq.Tables.TAXI_TRIPS;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@JooqTest
@ContextConfiguration(classes = DbConfiguration.class)
public class TaxiTripDAOPostgresTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TaxiTripDAO taxiTripDAO;
    @Autowired
    private DSLContext db;

    @Test
    public void getsRecordsSuccessfully() {
        final TaxiTripsRecord taxiTripsRecord = db.newRecord(TAXI_TRIPS);
        taxiTripsRecord.from(TaxiTrip.builder()
                .tPepPickupDatetime(LocalDateTime.now())
                .tPepDropOffDatetime(LocalDateTime.now())
                .build());
        db.insertInto(TAXI_TRIPS)
                .set(taxiTripsRecord)
                .returning()
                .fetchOneInto(TaxiTrip.class);
        log.info("Records={}", taxiTripDAO.getAll());
    }

    @Test
    public void storesSuccessfully() {
        final UUID id = UUID.randomUUID();
        final TaxiTrip taxiTrip = TaxiTrip.builder()
                .id(id)
                .tPepPickupDatetime(LocalDateTime.now())
                .tPepDropOffDatetime(LocalDateTime.now())
                .build();

        taxiTripDAO.store(taxiTrip);
        assertThat(taxiTripDAO.getAll().stream().filter(it -> it.getId().equals(id)).findAny()).isPresent();

        log.info("Records={}", taxiTripDAO.getAll());
    }
}
