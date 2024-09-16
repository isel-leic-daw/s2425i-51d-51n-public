package pt.isel.daw.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

data class StudentOutputModel(
    val studentName: String,
    val studentNumber: Int,
)

data class CustomOutputModel(
    val something: String,
)

@RestController
@RequestMapping("examples-mc")
class MessageConversionController {
    /**
     * Using simple strings.
     */
    @GetMapping("0")
    fun handler0() = "Hello Web"

    /**
     * Using output models.
     */
    @GetMapping("1")
    fun handler1() = StudentOutputModel("Alice", 12345)

    /**
     * Using `ResponseEntity` that allows controlling other parts of the response message.
     */
    @GetMapping("2")
    fun handler2() =
        ResponseEntity
            .status(201)
            .contentType(MediaType.parseMediaType("application/vnd.isel.student+json"))
            .header("foo", "bar")
            .body(StudentOutputModel("Alice", 12345))

    /**
     * Using custom message converters.
     */
    @GetMapping("3")
    fun handler3() = URI("https://www.example.com")

    /**
     * Using custom message converters - JSON.
     */
    @GetMapping("4")
    fun handler4() = CustomOutputModel("the example")
}