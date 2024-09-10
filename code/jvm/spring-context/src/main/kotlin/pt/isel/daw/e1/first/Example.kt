package pt.isel.daw.e1.first

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext

private val log = LoggerFactory.getLogger("main")

/*
 * Introductory example to Spring Context and the concepts of Inversion of Control, dependency injections.
 */

/**
 * Interface with some functionality.
 */
interface InterfaceA {
    // not relevant for this example
}

/**
 * Another interface with some other functionality.
 */
interface InterfaceB {
    // not relevant for this example
}

/**
 * An implementation of [InterfaceA].
 */
class ComponentA : InterfaceA {
    init {
        log.info("ComponentA ctor.")
    }
}

/**
 * An implementation of [InterfaceB] that *depends* on an implementation of [InterfaceA].
 */
class ComponentB(
    // This means that ComponentB depends on an implementation of InterfaceA
    val dependency: InterfaceA,
) : InterfaceB {
    init {
        log.info("ComponentB ctor.")
    }
}

/**
 * A class that depends on both [InterfaceA] and [InterfaceB].
 */
class ComponentC(
    val dependency: InterfaceB,
    val anotherDependency: InterfaceA,
) {
    init {
        log.info("ComponentC ctor.")
    }
}

/**
 * An example application.
 */
fun main() {
    log.info("## Example: illustrates the use of a context with statically defined components/beans.")

    log.info("Creating the context.")
    val context = AnnotationConfigApplicationContext()
    log.info("Context created.")

    log.info("Registering components/beans *classes* to `context.register.")
    context.register(
        ComponentC::class.java,
        ComponentA::class.java,
        ComponentB::class.java,
    )

    log.info("Refresh the context to take into consideration the new bean definitions.")
    context.refresh()

    log.info("Getting beans.")
    val componentC = context.getBean<ComponentC>()

    log.info("ComponentC instance - {}.", componentC)
    val anotherComponentC = context.getBean<ComponentC>()
    val componentB = context.getBean<ComponentB>()

    log.info("componentC - {}, anotherComponentC - {}.", componentC, anotherComponentC)
    log.info("componentC.dependency - {}, componentB - {}.", componentC.dependency, componentB)
    log.info("B.A = {}, C.A = {}", componentB.dependency, componentC.anotherDependency)
}