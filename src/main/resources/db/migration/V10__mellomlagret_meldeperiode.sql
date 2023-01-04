-------------------------------------------------------------------------------
-- Tabell            : mellomlgagret_meldeperiode
-- Beskrivelse       : Kodeverkstabell som inneholder mellomlagrede meldeperioder
-------------------------------------------------------------------------------
CREATE TABLE mellomlgagret_meldeperiode
(
    meldeperiode_id INTEGER PRIMARY KEY NOT NULL,
    data            TEXT                NOT NULL
);

-- Indekser
CREATE INDEX memepe_1 ON meldeperiode (meldeperiode_id);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE meldeperiode IS 'Kodeverkstabell som inneholder mellomlagrede meldeperioder.';
COMMENT ON COLUMN meldeperiode.meldeperiode_id IS 'Primærnøkkel';
COMMENT ON COLUMN meldeperiode.fom_dato IS 'JSON';
