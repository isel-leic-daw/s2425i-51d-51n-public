package com.example.demo_leic51n

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class Student(
    val name: String,
    val number: Int,
)

@RestController
class ExampleController(
    private val greetingsService: GreetingsService
) {
    @GetMapping("/examples/0")
    fun exampleHandler() = greetingsService.getGreeting()

    @GetMapping("/examples/1")
    fun anotherExampleHandler() = mapOf(
        "aField" to "some string",
        "anotherField" to 42,
    )

    @GetMapping("/examples/2")
    fun yetAnotherOne() = Student("Alice", 12345)

    @GetMapping("/examples/3/{id}")
    fun exampleUsingAPathTemplate(
        foo: HttpServletRequest,
        @PathVariable id: String
    ) = "id=$id"

}

interface GreetingsService {
    fun getGreeting(): String
}

@Component
class DefaultGreetingsService : GreetingsService {
    override fun getGreeting(): String {
        return "Olá mundo"
    }
}