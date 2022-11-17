-------------------------------------------------------------------------------
-- Tabell            : meldeperiode
-- Beskrivelse       : Kodeverkstabell som inneholder gyldige meldeperioder
-------------------------------------------------------------------------------
CREATE TABLE meldeperiode
(
    meldeperiode_id   SERIAL PRIMARY KEY        NOT NULL,
    fom_dato          DATE                      NOT NULL,
    tom_dato          DATE                      NOT NULL,
    frist             DATE                      NOT NULL
);

-- Indekser
CREATE INDEX mepe_1 ON meldeperiode (meldeperiode_id);
CREATE INDEX mepe_2 ON meldeperiode (fom_dato, tom_dato);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE meldeperiode IS 'Kodeverkstabell som inneholder gyldige meldeperioder.';
COMMENT ON COLUMN meldeperiode.meldeperiode_id IS 'Primærnøkkel';
COMMENT ON COLUMN meldeperiode.fom_dato IS 'Fra og med dato for meldeperioden';
COMMENT ON COLUMN meldeperiode.tom_dato IS 'Til og med dato for meldeperioden';
