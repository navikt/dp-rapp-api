# dp-rapp-api
dp-rapp-api er en backend applikasjon for rapporteringsløsningen for dagpenger.
Appen støtter følgende funksjoner:
* funksjon 1
* funksjon 2
* osv

## Dokumentasjon
Dokumentasjon finnes i ....
Swagger er tilgjengelig på ....

## For NAV-ansatte
Interne henvendelser kan sendes via Slack i kanalen #meldekort.

## For utviklere

### Lokal kjøring
0. For å være sikker på at man får en ny tom database, kan man kjøre kommandoen: `docker-compose down -v`.
1. Start lokal instans av Postgres ved å kjøre `docker-compose up -d`.
2. Start MainTest. Her vil det settes en del systemproperties i PropertySetup. En av disse er testmode som settes til true.