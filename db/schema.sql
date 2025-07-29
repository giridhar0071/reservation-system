

-- 0) Enable pgcrypto for gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 1) Patients
CREATE TABLE patient (
  id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email         TEXT UNIQUE NOT NULL,
  first_name    TEXT NOT NULL,
  last_name     TEXT NOT NULL,
  dob           DATE,
  phone         TEXT
);

-- 2) Representatives
CREATE TABLE representative (
  id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email         TEXT UNIQUE NOT NULL,
  first_name    TEXT NOT NULL,
  last_name     TEXT NOT NULL,
  dob           DATE,
  phone         TEXT
);

-- 3) Organizations
CREATE TABLE organization (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL
);

-- 4) Organization locations
CREATE TABLE organization_location (
  id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organization_id  UUID NOT NULL,
  address          TEXT,
  lat              DOUBLE PRECISION NOT NULL,
  lon              DOUBLE PRECISION NOT NULL,
  CONSTRAINT fk_orgloc_org
    FOREIGN KEY (organization_id)
    REFERENCES organization(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
CREATE INDEX idx_org_loc_lat ON organization_location(lat);
CREATE INDEX idx_org_loc_lon ON organization_location(lon);

-- 5) Slot availability
CREATE TABLE slot_availability (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  location_id UUID NOT NULL,
  date        DATE NOT NULL,
  slot        VARCHAR(10) NOT NULL
                CHECK (slot IN ('MORNING','AFTERNOON','EVENING')),
  capacity    INTEGER NOT NULL,
  CONSTRAINT fk_slotavail_loc
    FOREIGN KEY (location_id)
    REFERENCES organization_location(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- 6) Appointments
CREATE TABLE appointment (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  patient_id  UUID NOT NULL,
  slot_id     UUID NOT NULL,
  rep_id      UUID NOT NULL,
  booked_at   TIMESTAMPTZ DEFAULT now(),
  CONSTRAINT fk_appt_pat
    FOREIGN KEY (patient_id)
    REFERENCES patient(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_appt_slot
    FOREIGN KEY (slot_id)
    REFERENCES slot_availability(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_appt_rep
    FOREIGN KEY (rep_id)
    REFERENCES representative(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- 7) Postal codes (ZIP→centroid)
CREATE TABLE postal_code (
  zip    VARCHAR(10) PRIMARY KEY,
  city   TEXT NOT NULL,
  state  TEXT NOT NULL,
  lat    DOUBLE PRECISION NOT NULL,
  lon    DOUBLE PRECISION NOT NULL
);
CREATE INDEX idx_postal_code_lat ON postal_code(lat);
CREATE INDEX idx_postal_code_lon ON postal_code(lon);
