-------------------------------------------------------------------------------
-- Tabell            : KALL_LOGG
-- Beskrivelse       : Loggtabell for API-kall og Kafka hendelser.
-------------------------------------------------------------------------------
CREATE TABLE kall_logg
(
    kall_logg_id   BIGINT GENERATED ALWAYS AS IDENTITY,
    korrelasjon_id VARCHAR(54)                            NOT NULL,
    tidspunkt      TIMESTAMP(9) DEFAULT current_timestamp NOT NULL,
    type           VARCHAR(10)                            NOT NULL,
    retning        VARCHAR(10)                            NOT NULL,
    metode         VARCHAR(10),
    path           VARCHAR(100)                           NOT NULL,
    status         INTEGER,
    kalltid        BIGINT                                 NOT NULL,
    request        TEXT,
    response       TEXT,
    info           TEXT
) PARTITION BY RANGE ((tidspunkt::date));
-- Det er mulig å ha partisjonerte tabeller i PostgreSQL:
-- PARTITION BY RANGE (tidspunkt)
--
-- Men selve partisjonene må opprettes manuelt. Dette er ikke egnet for prod.
-- Men dette er ikke så viktig å ha partisjoner i PosgreSQL siden den brukes kun når appen kjører lokalt
--

-- Indekser
CREATE INDEX kalo_1 ON kall_logg (path, retning);

CREATE INDEX kalo_2 ON kall_logg (korrelasjon_id);

CREATE INDEX kalo_3 ON kall_logg (status);

-- Constraints
ALTER TABLE kall_logg
    ADD CONSTRAINT type_ck1 CHECK ( type IN ('REST', 'KAFKA') );

ALTER TABLE kall_logg
    ADD CONSTRAINT kall_retning_ck1 CHECK ( retning IN ('INN', 'UT') );

-- Tabell og kolonnekommentarer
COMMENT ON TABLE kall_logg IS 'Loggtabell for API-kall og Kafka hendelser.';

COMMENT ON COLUMN kall_logg.kall_logg_id IS 'Autogenerert sekvens';
COMMENT ON COLUMN kall_logg.korrelasjon_id IS 'Unik ID som kan brukes for å korrelere logginnslag med logging til Kibana.';
COMMENT ON COLUMN kall_logg.tidspunkt IS 'Tidspunkt for når kallet bli mottatt.';
COMMENT ON COLUMN kall_logg.type IS 'Grensesnittype: REST/KAFKA';
COMMENT ON COLUMN kall_logg.retning IS 'Kallretning: INN: API-kall til applikasjonen. UT: Kall til underliggende tjeneste eller hendelser ut på Kafka.';
COMMENT ON COLUMN kall_logg.metode IS 'HTTP-metode. (GET, POST osv.)';
COMMENT ON COLUMN kall_logg.path IS 'REST: Ressursstien (request URI) til kallet. KAFKA: Navn på Kafka-topic.';
COMMENT ON COLUMN kall_logg.status IS 'HTTP-statuskode returnert fra kallet. For Kafka-grensesnitt: N/A.';
COMMENT ON COLUMN kall_logg.kalltid IS 'Målt tid for utførelse av kallet i millisekunder.';
COMMENT ON COLUMN kall_logg.request IS 'Sendt Kafka-hendelse eller REST-kall.';
COMMENT ON COLUMN kall_logg.response IS 'Komplett HTTP-respons m/ status, headere og responsdata.';
COMMENT ON COLUMN kall_logg.info IS 'Tilleggsinformasjon til fri bruk. Kan typisk brukes for feilmeldinger, stacktrace eller annet som kan være nyttig å logge.';
