-------------------------------------------------------------------------------
-- Tabell            : kontroll_logg
-- Beskrivelse       : Logg over kontroller utført på rapporteringer.
-------------------------------------------------------------------------------
CREATE TABLE kontroll_logg
(
    kontroll_logg_id            SERIAL PRIMARY KEY            NOT NULL,
    rapportering_id             INT REFERENCES rapportering   NOT NULL,
    korrelasjon_id              UUID                          NOT NULL,
    kontroll_status             VARCHAR(20)                   NOT NULL,
    kontroll_info               TEXT
);

-- Indekser
CREATE INDEX kolo_1 ON kontroll_logg (kontroll_logg_id);
CREATE INDEX kolo_2 ON kontroll_logg (rapportering_id);
CREATE INDEX kolo_3 ON kontroll_logg (korrelasjon_id);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE kontroll_logg IS 'Logg over kontroller utført på rapporteringer.';
COMMENT ON COLUMN kontroll_logg.kontroll_logg_id IS 'Primærnøkkel';
COMMENT ON COLUMN kontroll_logg.rapportering_id IS 'Referanse til rapporteringen kontrollen er utført på. Fremmednøkkel';
COMMENT ON COLUMN kontroll_logg.korrelasjon_id IS 'Korrelasjonsid til requesten der kontrollen er utført';
COMMENT ON COLUMN kontroll_logg.kontroll_status IS 'Status på kontrollen. Kodeverk';
COMMENT ON COLUMN kontroll_logg.kontroll_info IS 'Ytterligere informasjon om kontrollen. Eks feilmelding';
