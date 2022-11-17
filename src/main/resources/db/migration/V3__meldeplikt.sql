-------------------------------------------------------------------------------
-- Tabell            : meldeplikt
-- Beskrivelse       : Tabell som forteller hvilke perioder en person har
--                     meldeplikt som dagpengemottaker.
-------------------------------------------------------------------------------
CREATE TABLE meldeplikt
(
    meldeplikt_id     SERIAL PRIMARY KEY        NOT NULL,
    person_id         INT REFERENCES person     NOT NULL,
    fom_dato          DATE                      NOT NULL,
    tom_dato          DATE
);

-- Indekser
CREATE INDEX mpli_1 ON meldeplikt (meldeplikt_id);
CREATE INDEX mpli_2 ON meldeplikt (person_id, fom_dato);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE meldeplikt IS 'Inneholder perioder der personbruker har hatt meldeplikt som dagpengemottaker.';
COMMENT ON COLUMN meldeplikt.meldeplikt_id IS 'Primærnøkkel';
COMMENT ON COLUMN meldeplikt.person_id IS 'Referanse til person-tabellen. Fremmednøkkel';
COMMENT ON COLUMN meldeplikt.fom_dato IS 'Fra og med dato for meldeplikten';
COMMENT ON COLUMN meldeplikt.tom_dato IS 'Til og med dato for meldeplikten';
