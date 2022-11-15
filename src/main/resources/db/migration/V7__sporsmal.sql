---------------------------------------------------references----------------------------
-- Tabell            : sporsmal
-- Beskrivelse       : Spørsmål som er stilt når bruker sender inn meldekort
-------------------------------------------------------------------------------
CREATE TABLE sporsmal
(
    sporsmal_id                 SERIAL PRIMARY KEY            NOT NULL,
    kontroll_log_id             INT REFERENCES sporsmal  NOT NULL,
    tekst_id_sporsmal           VARCHAR(40)                   NOT NULL,
    tekst_id_veiledning         VARCHAR(40)                   NOT NULL
);


COMMENT ON COLUMN sporsmal.sporsmal_id IS 'Primærnøkkel';
COMMENT ON COLUMN sporsmal.kontroll_log_id IS 'Referanse til kontroll logg. Fremmednøkkel';
COMMENT ON COLUMN sporsmal.tekst_id_sporsmal IS 'Referanse til teksten som inneholder spørsmålet';
COMMENT ON COLUMN sporsmal.tekst_id_sporsmal IS 'Referanse til teksten som inneholder veiledning';
