ALTER TABLE region
    ADD COLUMN sido_code VARCHAR(2) GENERATED ALWAYS AS (SUBSTRING(region_code, 1, 2)) STORED,
    ADD COLUMN gugun_code VARCHAR(3) GENERATED ALWAYS AS (SUBSTRING(region_code, 3, 3)) STORED,
    ADD COLUMN dong_code VARCHAR(3) GENERATED ALWAYS AS (SUBSTRING(region_code, 6, 3)) STORED;

CREATE INDEX idx_region_full_code ON region(sido_code, gugun_code, dong_code);