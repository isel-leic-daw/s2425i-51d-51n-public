plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"

    // Not strictly needed, just added to include a task to show the task graph
    id("org.barfuin.gradle.taskinfo") version "2.2.0"
}

group = "pt.isel.daw"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register("demoTask") {
    group = "Demo"
    description = "Just a demo task to illustrate task definition and execution"
    println("demoTask definition")
    doLast {
        println("demoTask execution")
    }
}

tasks.register("anotherDemoTask") {
    group = "Demo"
    description = "Just another demo task to illustrate task definition and execution"
    dependsOn("demoTask")
    println("anotherDemoTask definition")
    doLast {
        println("anotherDemoTask execution")
    }
}