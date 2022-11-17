-------------------------------------------------------------------------------
-- Tabell            : sporsmal
-- Beskrivelse       : Spørsmål som er stilt når bruker rapporterer for meldeperiode.
-------------------------------------------------------------------------------
CREATE TABLE sporsmal
(
    sporsmal_id                 SERIAL PRIMARY KEY           NOT NULL,
    kontroll_logg_id            INT REFERENCES kontroll_logg NOT NULL,
    tekst_id_sporsmal           VARCHAR(40)                  NOT NULL,
    tekst_id_veiledning         VARCHAR(40)
);

-- Indekser
CREATE INDEX spor_1 ON sporsmal (sporsmal_id);
CREATE INDEX spor_2 ON sporsmal (kontroll_logg_id);
CREATE INDEX spor_3 ON sporsmal (tekst_id_sporsmal);
CREATE INDEX spor_4 ON sporsmal (tekst_id_veiledning);

-- Tabell- og kolonnekommentarer
COMMENT ON TABLE sporsmal IS 'Spørsmål som er stilt når bruker rapporterer for meldeperiode.';
COMMENT ON COLUMN sporsmal.sporsmal_id IS 'Primærnøkkel';
COMMENT ON COLUMN sporsmal.kontroll_logg_id IS 'Referanse til kontroll logg. Fremmednøkkel.';
COMMENT ON COLUMN sporsmal.tekst_id_sporsmal IS 'Referanse til teksten som inneholder spørsmålet.';
COMMENT ON COLUMN sporsmal.tekst_id_sporsmal IS 'Referanse til teksten som inneholder veiledning.';
