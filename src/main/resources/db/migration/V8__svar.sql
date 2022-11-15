---------------------------------------------------references----------------------------
-- Tabell            : svar
-- Beskrivelse       : Svar på spørsmål som er stilt når bruker sender inn meldekort
-------------------------------------------------------------------------------
CREATE TABLE svar
(
    svar_id                     SERIAL PRIMARY KEY          NOT NULL,
    sporsmal_id                 INT REFERENCES sporsmal     NOT NULL,
    dato                        DATE,
    svar                        TEXT                        NOT NULL
);


COMMENT ON COLUMN svar.svar_id IS 'Primærnøkkel';
COMMENT ON COLUMN svar.sporsmal_id IS 'Referanse til spørsmål. Fremmednøkkel';
COMMENT ON COLUMN svar.dato IS 'Hvilken dato svaret er gitt for. For eksempel ved timeføring for en dag';
COMMENT ON COLUMN svar.svar IS 'Innholdet i svaret';
