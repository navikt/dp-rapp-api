-------------------------------------------------------------------------------
-- Tabell            : kall_logg
-- Beskrivelse       : Loggtabell for API-kall og Kafka hendelser.
--                     På grunn av at partisjoner må opprettes før tabellen tas
--                     i bruk, må funksjonene for dette, samt statement for å
--                     opprette initielle partisjoner kjøres ved opprettelse av
--                     tabellen.
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

-- Indekser
CREATE INDEX kalo_1 ON kall_logg (kall_logg_id);
CREATE INDEX kalo_2 ON kall_logg (korrelasjon_id);
CREATE INDEX kalo_3 ON kall_logg (path, retning);
CREATE INDEX kalo_4 ON kall_logg (status);

-- Constraints
ALTER TABLE kall_logg
    ADD CONSTRAINT type_ck1 CHECK ( type IN ('REST', 'KAFKA') );
ALTER TABLE kall_logg
    ADD CONSTRAINT kall_retning_ck1 CHECK ( retning IN ('INN', 'UT') );

-- Tabell- og kolonnekommentarer
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

CREATE OR REPLACE FUNCTION handter_partisjoner_kall_logg()
    RETURNS void
    LANGUAGE plpgsql AS
$$
DECLARE
rec RECORD;
BEGIN
    -- Partisjon for i morgen
EXECUTE opprett_partisjon_kall_logg(CURRENT_DATE+1);

-- Slett partisjon som er eldre enn 30 dager
FOR rec IN
SELECT right(cast (inhrelid::regclass as text), 10) AS date_in_name
FROM pg_catalog.pg_inherits
WHERE inhparent = 'kall_logg'::regclass
  AND to_date(right (cast (inhrelid::regclass as text), 10), 'YYYY_MM_DD') < CURRENT_DATE - 30
  AND to_date(right (cast (inhrelid::regclass as text), 10), 'YYYY_MM_DD') > CURRENT_DATE - 1000 -- Ikke slett DEFAULT partisjonen
    LOOP
    EXECUTE format(
    'DROP TABLE IF EXISTS kall_logg_%s',
    rec.date_in_name
    );
END LOOP;
END;
$$;

CREATE OR REPLACE FUNCTION opprett_partisjon_kall_logg(fromDate DATE)
    RETURNS void
    LANGUAGE plpgsql AS
$$
BEGIN
EXECUTE format(
        'CREATE TABLE IF NOT EXISTS kall_logg_%s PARTITION OF kall_logg FOR VALUES FROM (%L) TO (%L)',
        to_char(fromDate, 'YYYY_MM_DD'),
        to_char(fromDate, 'YYYY-MM-DD'),
        to_char(fromDate + 1, 'YYYY-MM-DD')
    );
END;
$$;

-- Utføres ved første kjøring:
-- Opprett partisjon for i dag
SELECT opprett_partisjon_kall_logg(CURRENT_DATE);
-- Opprett partisjon for i morgen
SELECT opprett_partisjon_kall_logg(CURRENT_DATE+1);
