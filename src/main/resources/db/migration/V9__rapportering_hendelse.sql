---------------------------------------------------references----------------------------
-- Tabell            : rapportering_hendelse
-- Beskrivelse       : Hendelse for rapporteringer som sendes ut på kø
-------------------------------------------------------------------------------
CREATE TABLE rapportering_hendelse
(
    rapportering_hendelse_id    SERIAL PRIMARY KEY              NOT NULL,
    rapportering_id             INT REFERENCES rapportering     NOT NULL,
    korrelasjon_id              UUID                            NOT NULL,
    status                      VARCHAR(20)
        CHECK (status in ('NY', 'SENDT', 'RETRY', 'FEILET'))    NOT NULL,
    status_tidspunkt            TIMESTAMPTZ                     NOT NULL,
    retry_teller                INT                             NOT NULL,
    retry_tidspunkt             TIMESTAMPTZ                     NOT NULL,
    hendelse                    TEXT                            NOT NULL,
    feilinformasjon             TEXT
);


COMMENT ON COLUMN rapportering_hendelse.rapportering_hendelse_id IS 'Primærnøkkel';
COMMENT ON COLUMN rapportering_hendelse.rapportering_id IS 'Referanse til rapporteringen. Fremmednøkkel';
COMMENT ON COLUMN rapportering_hendelse.korrelasjon_id IS 'Korrelasjonsid fra innkommende request, eller ny dersom den har feilet og gått i retry';
COMMENT ON COLUMN rapportering_hendelse.status IS 'Status på sending av hendelsen (NY, SENDT, RETRY, FEILET)';
COMMENT ON COLUMN rapportering_hendelse.status_tidspunkt IS 'Tidspunkt for forrige statusendring';
COMMENT ON COLUMN rapportering_hendelse.retry_teller IS 'Angir hvor mange ganger hendelsen er forsøkt sendt';
COMMENT ON COLUMN rapportering_hendelse.retry_tidspunkt IS 'Tidspunktet for siste retry';
COMMENT ON COLUMN rapportering_hendelse.hendelse IS 'Hendelsen som blir lagt på kø i json-format';
COMMENT ON COLUMN rapportering_hendelse.feilinformasjon IS 'Eventuell feilmeldig dersom det har feilet å legge meldinge på kø';
