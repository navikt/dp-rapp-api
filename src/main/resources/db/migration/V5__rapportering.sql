---------------------------------------------------references----------------------------
-- Tabell            : rapportering
-- Beskrivelse       : Informasjon om brukers rapporterte arbeid for peridoen
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


COMMENT ON COLUMN rapportering.rapportering_id IS 'Primærnøkkel';
COMMENT ON COLUMN rapportering.person_id IS 'Intern person-identifikator. Fremmednøkkel';
COMMENT ON COLUMN rapportering.meldeperiode_id IS 'Meldeperioden rapporteringen gjelder. Fremmednøkkel';
COMMENT ON COLUMN rapportering.korrigert_rapportering_id IS 'Dersom dette er en korrigering av en tidligere rapportering vil dette referere til rapporteringen som korrigeres. Selv-refererende fremmednøkkel';
COMMENT ON COLUMN rapportering.kilde IS 'Kilde rapporteringen kommer fra. Kodeverk';
COMMENT ON COLUMN rapportering.kilde IS 'Status på rapporteringen';
