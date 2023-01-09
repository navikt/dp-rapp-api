plugins {
    id("java");
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-test:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-quartz:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.1")
    implementation("io.micrometer:micrometer-registry-prometheus:1.10.2")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.flywaydb:flyway-core:9.10.2")
    implementation("org.yaml:snakeyaml:1.33")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("org.springframework.kafka:spring-kafka:2.9.3")
    implementation("no.nav.security:token-validation-spring:3.0.2")
    implementation("com.auth0:java-jwt:4.2.1")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}