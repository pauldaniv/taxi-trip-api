-- Create a temporary TIMESTAMP column
ALTER TABLE taxi_trips ADD COLUMN create_time_holder TIMESTAMP without time zone NULL;

-- Copy casted value over to the temporary column
UPDATE taxi_trips SET create_time_holder = tpep_pickup_datetime::TIMESTAMP;

-- Modify original column using the temporary column
ALTER TABLE taxi_trips ALTER COLUMN tpep_pickup_datetime TYPE TIMESTAMP without time zone USING create_time_holder;

-- Drop the temporary column (after examining altered column values)
ALTER TABLE taxi_trips DROP COLUMN create_time_holder;


-- Create a temporary TIMESTAMP column
ALTER TABLE taxi_trips ADD COLUMN create_time_holder TIMESTAMP without time zone NULL;

-- Copy casted value over to the temporary column
UPDATE taxi_trips SET create_time_holder = tpep_dropoff_datetime::TIMESTAMP;

-- Modify original column using the temporary column
ALTER TABLE taxi_trips ALTER COLUMN tpep_dropoff_datetime TYPE TIMESTAMP without time zone USING create_time_holder;

-- Drop the temporary column (after examining altered column values)
ALTER TABLE taxi_trips DROP COLUMN create_time_holder;
