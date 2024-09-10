package pt.isel.daw.e6.scopes

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

private val log = LoggerFactory.getLogger("main")

@Component
@Scope("prototype")
class ComponentA

@Component
class ComponentB(
    val a: ComponentA,
)

@Component
class ComponentC(
    val a: ComponentA,
    val b: ComponentB,
)

@Component
class ComponentD(
    val a: ComponentA,
    val b: ComponentB,
    val c: ComponentC,
)

fun main() {
    log.info("## Example: illustrates the use of scopes")

    log.info("Create the context.")
    val context = AnnotationConfigApplicationContext()

    log.info("Scan a package for all @Component annotated classes.")
    context.scan("pt.isel.daw.e6.scopes")

    log.info("Refresh the context.")
    context.refresh()

    log.info("Get a component.")
    val d = context.getBean<ComponentD>()

    log.info("ComponentA instances are different: {}", setOf(d.a, d.b.a, d.c.a).size == 3)
    log.info("ComponentB instances are the same: {}", setOf(d.b, d.c.b).size == 1)
}