plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
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
    implementation(project(":tic-tac-toe:http"))
    implementation(project(":tic-tac-toe:services"))
    implementation(project(":tic-tac-toe:repository-jdbi"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // for JDBI and Postgres
    implementation("org.jdbi:jdbi3-core:3.37.1")
    implementation("org.postgresql:postgresql:42.7.2")

    // To use Kotlin specific date and time functions
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    // To get password encode
    implementation("org.springframework.security:spring-security-core:6.3.0")

    // To use WebTestClient on tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    if (System.getenv("DB_URL") == null) {
        environment("DB_URL", "jdbc:postgresql://localhost:5432/db?user=dbuser&password=changeit")
    }
    dependsOn(":tic-tac-toe:repository-jdbi:dbTestsWait")
    finalizedBy(":tic-tac-toe:repository-jdbi:dbTestsDown")
}

kotlin {
    jvmToolchain(21)
}

/**
 * Docker related tasks
 */
task<Copy>("extractUberJar") {
    dependsOn("assemble")
    // opens the JAR containing everything...
    from(zipTree(layout.buildDirectory.file("libs/host-$version.jar").get().toString()))
    // ... into the 'build/dependency' folder
    into("build/dependency")
}

val dockerImageTagJvm = "tic-tac-toe-jvm"
val dockerImageTagNginx = "tic-tac-toe-nginx"
val dockerImageTagPostgresTest = "tic-tac-toe-postgres-test"
val dockerImageTagUbuntu = "tic-tac-toe-ubuntu"

task<Exec>("buildImageJvm") {
    dependsOn("extractUberJar")
    commandLine("docker", "build", "-t", dockerImageTagJvm, "-f", "tests/Dockerfile-jvm", ".")
}

task<Exec>("buildImageNginx") {
    commandLine("docker", "build", "-t", dockerImageTagNginx, "-f", "tests/Dockerfile-nginx", ".")
}

task<Exec>("buildImagePostgresTest") {
    commandLine(
        "docker",
        "build",
        "-t",
        dockerImageTagPostgresTest,
        "-f",
        "tests/Dockerfile-postgres-test",
        "../repository-jdbi",
    )
}

task<Exec>("buildImageUbuntu") {
    commandLine("docker", "build", "-t", dockerImageTagUbuntu, "-f", "tests/Dockerfile-ubuntu", ".")
}

task("buildImageAll") {
    dependsOn("buildImageJvm")
    dependsOn("buildImageNginx")
    dependsOn("buildImagePostgresTest")
    dependsOn("buildImageUbuntu")
}

task<Exec>("allUp") {
    commandLine("docker", "compose", "up", "--force-recreate", "-d")
}

task<Exec>("allDown") {
    commandLine("docker", "compose", "down")
}