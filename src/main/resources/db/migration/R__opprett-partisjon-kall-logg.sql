-------------------------------------------------------------------------------
-- Funksjon          : opprett_partisjon_kall_logg
-- Beskrivelse       : Oppretter partisjoner for kall_logg.
-------------------------------------------------------------------------------

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


CREATE TABLE kall_logg_1000_01_01 PARTITION OF kall_logg DEFAULT;

-- Ved første kjøring opprett partisjoner for i dag og neste dager
SELECT opprett_partisjon_kall_logg(CURRENT_DATE);
SELECT opprett_partisjon_kall_logg(CURRENT_DATE + 1);
SELECT opprett_partisjon_kall_logg(CURRENT_DATE + 2);