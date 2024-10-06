plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "pt.isel.daw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Module dependencies
    implementation(project(":tic-tac-toe:domain"))
    implementation(project(":tic-tac-toe:services"))

    // To use Spring MVC and the Servlet API
    implementation("org.springframework:spring-webmvc:6.1.13")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // To use SLF4J
    implementation("org.slf4j:slf4j-api:2.0.16")

    // To use Kotlin specific date and time functions
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
}

tasks.test {
    useJUnitPlatform()
    if (System.getenv("DB_URL") == null) {
        environment("DB_URL", "jdbc:postgresql://localhost:5432/db?user=dbuser&password=changeit")
    }
}

kotlin {
    jvmToolchain(21)
}