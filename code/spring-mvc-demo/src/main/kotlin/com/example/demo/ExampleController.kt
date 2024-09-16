package com.example.demo

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A _controller_, which is a set of _handlers_
 */
@RestController
class ExampleController(
    private val greetingsService: GreetingsService,
) {

    /**
     * An example handler
     */
    @GetMapping("/example/0")
    fun exampleHandler() = greetingsService.getGreetings()

    @PostMapping("/example/1")
    fun anotherExampleHandler() = mapOf(
        "greetings" to "hello",
        "anotherProp" to 42,
    )
}

@RestController
class AnotherController {

    @GetMapping("/example/2/{id}")
    fun theHandler(
        @PathVariable id: Int,
    ) = "handler in another controller, id=$id"

}

interface GreetingsService {
    fun getGreetings(): String
}

@Component
class DefaultGreetingsService : GreetingsService {
    override fun getGreetings(): String {
        return "Olá mundo"
    }
}