plugins {
    kotlin("jvm")
}

group = "pt.isel.daw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Jetty dependencies
    implementation("org.eclipse.jetty:jetty-server:11.0.24")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.24")

    // Tomcat dependencies
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.28")

    // For logging purposes
    implementation("org.slf4j:slf4j-api:2.0.0")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}