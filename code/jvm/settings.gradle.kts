plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    kotlin("jvm") version "2.0.10" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.3.3"  apply false
    id("io.spring.dependency-management") version "1.1.6"  apply false
}
include("spring-context")
include("gradle-intro")
include("servlet-intro")
include("spring-boot-mvc-intro")
include("tic-tac-toe")
include("tic-tac-toe:host")
include("tic-tac-toe:domain")
include("tic-tac-toe:http")
include("tic-tac-toe:services")
include("tic-tac-toe:repository")
include("tic-tac-toe:repository-jdbi")

