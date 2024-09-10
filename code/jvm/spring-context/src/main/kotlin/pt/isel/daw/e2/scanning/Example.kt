package pt.isel.daw.e2.scanning

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component

private val log = LoggerFactory.getLogger("main")

interface InterfaceA

@Component
class ComponentA : InterfaceA

@Component
class ComponentB(
    val dependency: InterfaceA,
)

fun main() {
    log.info("## Example: illustrates the use of a context with component scanning")

    log.info("Create the context.")
    val context = AnnotationConfigApplicationContext()

    log.info("Scan a package for all @Component annotated classes.")
    context.scan("pt.isel.daw.e2")

    log.info("Refresh the context.")
    context.refresh()

    log.info("Get a component.")
    val componentB = context.getBean<ComponentB>()
    log.info("ComponentB - {}.", componentB)
    /*
     * Conclusions:
     * - In this example the bean definitions are not added explicitly to the context.
     * - Instead, the `isel.pt.daw.e1.scanning` package is scanned for classes that have the `@Component` annotation.
     * - The end result is similar to explicitly registering the `ComponentA` and `ComponentB` classes.
     *
     * Experiments:
     * - Remove the call to `context.scan` and run the example.
     * - Remote the `@Component` annotation on one of the classes and run the example.
     */
}