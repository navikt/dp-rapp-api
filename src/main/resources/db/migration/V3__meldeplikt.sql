---------------------------------------------------references----------------------------
-- Tabell            : meldeplikt
-- Beskrivelse       : Tabell som forteller om hvilken periode en person har meldeplikt
-------------------------------------------------------------------------------
CREATE TABLE meldeplikt
(
    meldeplikt_id     SERIAL PRIMARY KEY        NOT NULL,
    person_id         INT REFERENCES person     NOT NULL,
    fom_dato          date                      NOT NULL,
    tom_dato          date
);


COMMENT ON COLUMN meldeplikt.meldeplikt_id IS 'primærnøkkel';
COMMENT ON COLUMN meldeplikt.person_id IS 'referanse til person-tabellen. Fremmednøkkel';
COMMENT ON COLUMN meldeplikt.fom_dato IS 'fra og med dato for meldeplikten';
COMMENT ON COLUMN meldeplikt.tom_dato IS 'til og med dato for meldeplikten';
