plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "pt.isel.daw"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:6.1.12")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}