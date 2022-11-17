-------------------------------------------------------------------------------
-- Tabell            : rapportering_hendelse
-- Beskrivelse       : Melding som trigges av rapporteringshendelse.
-------------------------------------------------------------------------------
CREATE TABLE rapportering_hendelse
(
    rapportering_hendelse_id    SERIAL PRIMARY KEY              NOT NULL,
    rapportering_id             INT REFERENCES rapportering     NOT NULL,
    meldingstype                VARCHAR(10)
        CHECK (status in ('RAPPORT', 'ARKIVERING'))             NOT NULL,
    korrelasjon_id              UUID                            NOT NULL,
    status                      VARCHAR(20)
        CHECK (status in ('NY', 'SENDT', 'RETRY', 'FEILET'))    NOT NULL,
    status_tidspunkt            TIMESTAMPTZ                     NOT NULL,
    retry_teller                INT,
    retry_tidspunkt             TIMESTAMPTZ,
    hendelse                    TEXT                            NOT NULL,
    feilinformasjon             TEXT
);

-- Indekser
CREATE INDEX hend_1 ON rapportering_hendelse (rapportering_hendelse_id);
CREATE INDEX hend_2 ON rapportering_hendelse (rapportering_id, meldingstype);
CREATE INDEX hend_3 ON rapportering_hendelse (korrelasjon_id);
CREATE INDEX hend_4 ON rapportering_hendelse (status);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE rapportering_hendelse IS 'Melding som trigges av rapporteringshendelse.';
COMMENT ON COLUMN rapportering_hendelse.rapportering_hendelse_id IS 'Primærnøkkel';
COMMENT ON COLUMN rapportering_hendelse.rapportering_id IS 'Referanse til rapporteringen. Fremmednøkkel.';
COMMENT ON COLUMN rapportering_hendelse.meldingstype IS 'Type melding som skal sendes (RAPPORT, ARKIVERING).';
COMMENT ON COLUMN rapportering_hendelse.korrelasjon_id IS 'Korrelasjonsid fra innkommende request, eller ny dersom den har feilet og gått i retry.';
COMMENT ON COLUMN rapportering_hendelse.status IS 'Status på sending av hendelsen (NY, SENDT, RETRY, FEILET).';
COMMENT ON COLUMN rapportering_hendelse.status_tidspunkt IS 'Tidspunkt for siste statusendring.';
COMMENT ON COLUMN rapportering_hendelse.retry_teller IS 'Angir hvor mange resendingsforsøk som gjenstår før meldingen får status FEILET.';
COMMENT ON COLUMN rapportering_hendelse.retry_tidspunkt IS 'Tidspunktet for neste retry.';
COMMENT ON COLUMN rapportering_hendelse.hendelse IS 'Medingen som skal sendes. Vil trolig være på json-format.';
COMMENT ON COLUMN rapportering_hendelse.feilinformasjon IS 'Eventuell feilmeldig dersom status er RETRY eller FEILET.';
