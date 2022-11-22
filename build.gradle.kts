plugins {
    id("java");
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:2.7.5")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.5")
    implementation("org.springframework.boot:spring-boot-starter-test:2.7.5")
    implementation("org.springframework.boot:spring-boot-starter-quartz:2.7.5")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.flywaydb:flyway-core:9.7.0")
    implementation("org.yaml:snakeyaml:1.33")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.projectlombok:lombok:1.18.22")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}