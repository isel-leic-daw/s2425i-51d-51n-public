package pt.isel.daw.e3.router

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component

private val log = LoggerFactory.getLogger("main")

/**
 * An annotation to mark all handler methods
 * (which don't need to have a specific signature)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyHandler(
    val method: String,
    val pathTemplate: String,
)

/**
 * An annotation to mark all controller classes
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class MyController

@MyController
class SomeController {
    @MyHandler("GET", "some/path")
    fun getIndex(): String {
        return "Hello"
    }
}

@MyController
class AnotherController {
    @MyHandler("GET", "another/path")
    fun getIndex(): String {
        return "Hello"
    }

    @MyHandler("POST", pathTemplate = "another/path")
    fun create() {
        // something...
    }
}

@Component
class NotAController

interface ControllerProvider {
    val controllers: List<Any>
}

@Component
class SpringContextBasedControllerProvider(
    // Caution: this should really be avoided - application components should not depend on the container/context
    // (however I don't know how to do it differently :( )
    applicationContext: ApplicationContext,
) : ControllerProvider {
    override val controllers: List<Any> =
        applicationContext.getBeansWithAnnotation(MyController::class.java).values.toList()
}

@Component
class Router(
    controllerProvider: ControllerProvider,
) {
    val controllers = controllerProvider.controllers
}

private fun main() {
    log.info("started")
    // Create the context
    val context = AnnotationConfigApplicationContext()
    // Scan a package for all @Component annotated classes
    context.scan("pt.isel.daw.e3.router")
    // Refresh the context to take into consideration the new bean definitions
    context.refresh()
    // Get the router
    val router = context.getBean(Router::class.java)

    log.info("Available controllers - {}", router.controllers)
    router.controllers.forEach {
        val handlerMethods =
            it.javaClass.methods.filter { method ->
                method.isAnnotationPresent(MyHandler::class.java)
            }
        handlerMethods.forEach { method ->
            val annotation = method.getAnnotation(MyHandler::class.java)
            log.info("'{}' on '{}' is handled by '{}'", annotation.method, annotation.pathTemplate, method)
        }
    }
}