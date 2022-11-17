-------------------------------------------------------------------------------
-- Tabell            : rapportering
-- Beskrivelse       : Brukers rapportering for meldeperiodene hen har hatt meldeplikt.
-------------------------------------------------------------------------------
CREATE TABLE rapportering
(
    rapportering_id             SERIAL PRIMARY KEY            NOT NULL,
    person_id                   INT REFERENCES person         NOT NULL,
    meldeperiode_id             INT REFERENCES meldeperiode   NOT NULL,
    korrigert_rapportering_id   INT REFERENCES rapportering,
    kilde                       VARCHAR(40)                   NOT NULL,
    status                      VARCHAR(20)                   NOT NULL
);

-- Indekser
CREATE INDEX rapp_1 ON rapportering (rapportering_id);
CREATE INDEX rapp_2 ON rapportering (person_id, meldeperiode_id);
CREATE INDEX rapp_3 ON rapportering (korrigert_rapportering_id);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE rapportering IS 'Brukers rapportering for meldeperiode der hen har hatt meldeplikt.';
COMMENT ON COLUMN rapportering.rapportering_id IS 'Primærnøkkel';
COMMENT ON COLUMN rapportering.person_id IS 'Intern person-identifikator. Fremmednøkkel.';
COMMENT ON COLUMN rapportering.meldeperiode_id IS 'Meldeperioden rapporteringen gjelder. Fremmednøkkel.';
COMMENT ON COLUMN rapportering.korrigert_rapportering_id IS 'Dersom dette er en korrigering av en tidligere rapportering, vil kolonnen referere til rapporteringen som korrigeres. Selv-refererende fremmednøkkel';
COMMENT ON COLUMN rapportering.kilde IS 'Kilde rapporteringen kommer fra. Kodeverk';
COMMENT ON COLUMN rapportering.kilde IS 'Status på rapporteringen';
