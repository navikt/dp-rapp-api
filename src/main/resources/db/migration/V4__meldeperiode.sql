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

COMMENT ON COLUMN meldeperiode.meldeperiode_id IS 'primærnøkkel';
COMMENT ON COLUMN meldeperiode.fom_dato IS 'fra og med dato for meldeperioden';
COMMENT ON COLUMN meldeperiode.tom_dato IS 'til og med dato for meldeperioden';
