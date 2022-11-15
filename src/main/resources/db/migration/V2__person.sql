-------------------------------------------------------------------------------
-- Tabell            : person
-- Beskrivelse       : Oversikt over personbrukere
-------------------------------------------------------------------------------
CREATE TABLE person
(
    person_id      SERIAL PRIMARY KEY                     NOT NULL,
    fnr            VARCHAR(11)                            NOT NULL
);

COMMENT ON COLUMN person.person_id IS 'Intern identifikator for person i meldeplikt-verdikjeden';
COMMENT ON COLUMN person.fnr IS 'fødselsnummer';
