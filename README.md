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
#### Build
Kjør  
```./gradlew clean build```

#### Sette opp database
Den enkleste måten å sette opp en database for lokal kjøring på er å bruke docker-compose prosjektet som er laget for 
raptus-applikasjonene. Dette finnes her: https://github.com/navikt/raptus-docker-compose
Oppskriften forutsetter at du har Docker installert, og har tilgang til ghcr.io.
1. Først må du bygge docker imaget. Stå i roten til dette repoet (det som inneholder Dockerfile) og kjør kommandoen  
   `docker build -t dp-rapp-api .`  
(Dette steget vil bli unødvendig senere når vi bruker ghcr mer aktivt)
2. Bytt til ønsket mappe og klon repositoryet hit  
   `git clone https://github.com/navikt/raptus-iac.git`
3. Gå inn i den opprettede mappen  
   `cd raptus-iac`
4. Bruk Docker Compose til å starte en Postgres-container  
   `cd raptus-iac docker compose up postgres -d`
5. Om du kjører `docker compose ps` fra denne mappen skal det nå se slik ut:
```
NAME                COMMAND                  SERVICE             STATUS               PORTS
postgres-raptus     "docker-entrypoint.s…"   postgres            running (starting)   0.0.0.0:5432->5432/tcp, :::5432->5432/tcp
```

#### Kjøre applikasjonen lokalt
For å kjøre applikasjonen lokalt må database credentials settes opp via miljøvariabler. Disse er som følger:
- DB_HOST: "localhost"
- DB_PORT: "5432"
- DB_USERNAME: "dp-rapp-api-db-user"
- DB_PASSWORD: "raptus"
- DB_DATABASE: "dp-rapp-api-db"

I tillegg må man også sette opp credentials for Kafka-lytteren. Det enkleste her er å bruke køen som er opprettet i dev-gcp:
- Last først ned credentials-filene til aiven-dev i raptus namespacet:  
  ```
  gcloud auth login  
  kubectl config use-context dev-gcp  
  nais aiven get kafka aiven-dev-secret raptus
  ```
    - En rekke filer vil bli lastet ned lokalt. Outputen vil fortelle deg hvor de blir lagret til
    - Kopier filene til dette repoet, i en mappe som heter "secrets". Denne mappen er lag til i .gitignore. Hvis du   
legger dem et annet sted må du passe på at de ikke blir pushet til github, siden github-repoet vårt er åpent.
    - Deretter må du sette flere miljøvariabler:
      - KAFKA_BROKERS=nav-dev-kafka-nav-dev.aivencloud.com:26484
      - KAFKA_CREDSTORE_PASSWORD=changeme
      - KAFKA_KEYSTORE_PATH=./secrets/client.keystore.p12
      - KAFKA_TRUSTSTORE_PATH=./secrets/client.truststore.jks
      - KAFKA_GROUP_ID=dp-rapp-api-<unikt-navn>
      - Den siste variabelen, KAFKA_GROUP_ID, defaultes til dp-rapp-api, men siden det er samme gruppe-id som brukes av 
applikasjonen i dev-gcp må man konkurrere med disse podene for å få meldingene (hvis det i det hele tatt finnes nok 
kafka-partisjoner for lokal instans å koble seg på). Dersom du benytter en ny, unik gruppe-id applikasjonen konsumere 
alle meldingene på topicen ved oppstart, men kun nye meldinger ved påfølgende kjøringer.

Dessuten må man slå av sikkerheten og oppgi test FNR:
- BYPASS_SECURITY=true
- TEST_IDENT=01020312345

Om man starter applikasjonen fra kommandolinje settes de via export (eller tilsvarende funksjon i Windows).
For eksempel ```export DB_HOST=localhost```  
Deretter kjør følgende kommando mens du står i rot-mappa til prosjektet:  
`./gradlew bootRun`

Dvs. for å kjøre lokalt:
```
export DB_HOST=localhost
export DB_PORT=5432
export DB_USERNAME=dp-rapp-api-db-user
export DB_PASSWORD=raptus
export DB_DATABASE=dp-rapp-api-db
export KAFKA_BROKERS=nav-dev-kafka-nav-dev.aivencloud.com:26484
export KAFKA_CREDSTORE_PASSWORD=changeme
export KAFKA_KEYSTORE_PATH=./secrets/client.keystore.p12
export KAFKA_TRUSTSTORE_PATH=./secrets/client.truststore.jks
export KAFKA_GROUP_ID=dp-rapp-api
export BYPASS_SECURITY=true
export TEST_IDENT=01020312345
./gradlew bootRun
```

Om du kjører fra Intellij skal Intellij ha satt opp en kjørekonfigurasjon for spring boot for deg, men om du 
konfigurerer manuelt må du sette opp følgende:
- Java versjon 17
- cp: dp-rapp-api.main
- Main class: no.nav.raptus.dprapp.Main
- Environment variables: Som over (Hvis du ikke har valget om å sette environment variables må kan det velges via nedtrekkslisten modify options)

