CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE vendors (
    id   BIGSERIAL primary key,
    name TEXT
);

CREATE TABLE rate_codes (
    id   BIGSERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE payment_types (
    id   BIGSERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE taxi_trips (
    id                    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    vendor_id             INTEGER REFERENCES vendors (id),
    rate_code_id          INTEGER REFERENCES rate_codes (id),
    payment_type_id       INTEGER REFERENCES payment_types (id),
    tpep_pickup_datetime  TIMESTAMP WITHOUT TIME ZONE,
    tpep_dropoff_datetime TIMESTAMP WITHOUT TIME ZONE,
    drop_off_year         INTEGER,
    drop_off_month        INTEGER,
    drop_off_day          INTEGER,
    passenger_count       INTEGER,
    trip_distance         DECIMAL,
    pulocationid          INTEGER,
    dolocationid          INTEGER,
    store_and_fwd_flag    BOOLEAN,
    fare_amount           DECIMAL,
    extra                 DECIMAL,
    mta_tax               DECIMAL,
    improvement_surcharge DECIMAL,
    tip_amount            DECIMAL,
    tolls_amount          DECIMAL,
    total_amount          DECIMAL
);
