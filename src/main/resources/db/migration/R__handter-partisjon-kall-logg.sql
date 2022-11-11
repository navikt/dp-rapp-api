-------------------------------------------------------------------------------
-- Funksjon          : handter_partisjoner_kall_logg
-- Beskrivelse       : Handterer sletting opp oppretting i partisjoner for kall_logg.
-------------------------------------------------------------------------------
-- Oppretter en partisjon for i morgen og sletter gamle partisjoner
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
