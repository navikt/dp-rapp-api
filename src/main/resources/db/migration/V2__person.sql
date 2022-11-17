-------------------------------------------------------------------------------
-- Tabell            : person
-- Beskrivelse       : Oversikt over personbrukere
-------------------------------------------------------------------------------
CREATE TABLE person
(
    person_id      SERIAL PRIMARY KEY                     NOT NULL,
    fnr            VARCHAR(11)                            NOT NULL
);

-- Indekser
CREATE INDEX pers_1 ON person (person_id);
CREATE INDEX pers_2 ON person (fnr);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE person IS 'Unike personbrukere som benytter rapporteringsløsningen.';
COMMENT ON COLUMN person.person_id IS 'Intern identifikator for person i meldeplikt-verdikjeden';
COMMENT ON COLUMN person.fnr IS 'Brukers gjeldende fødselsnummer eller D-nummer. Oppdateres fra PDL ved behov når personen bruker rapporteringsløsningen.';
