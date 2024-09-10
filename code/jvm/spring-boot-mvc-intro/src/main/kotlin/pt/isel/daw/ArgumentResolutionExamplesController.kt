package pt.isel.daw

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Length
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// See https://beanvalidation.org for the Length and Min annotations.
data class StudentInputModel(
    @get:Length(min = 1, max = 255)
    val name: String,
    @get:Min(1)
    val number: Int,
    @get:Min(1970)
    val enrollmentYear: Int,
)

@RestController
@RequestMapping("examples-ar")
class ArgumentResolutionExamplesController {
    /**
     *  Binding path variables to arguments.
     * template = /examples-ar/0/{id}
     * GET /examples-ar/0/123 -> id=123
     */
    @GetMapping("0/{id}")
    fun handler0(
        @PathVariable id: String,
    ) = "handler0 with $id"

    /**
     * Binding query string values to arguments.
     */
    @GetMapping("1")
    fun handler1(
        @RequestParam id: Int,
    ) = "handler1 with $id"

    /**
     * Support for optional query string value.
     */
    @GetMapping("2")
    fun handler2(
        @RequestParam id: Int?,
    ) = "handler2 with ${id ?: "absent"}"

    /**
     * Binding all query string pairs to an arguments.
     */
    @GetMapping("3")
    fun handler3(
        @RequestParam prms: MultiValueMap<String, String>,
    ) = prms
        .map { "${it.key}: ${it.value.joinToString(", ", "[", "]")}" }
        .joinToString()

    /**
     * Accessing the [HttpServletRequest] directly.
     */
    @GetMapping("4")
    fun handler45(
        httpServletRequest: HttpServletRequest,
    ) = "Hello2 ${httpServletRequest.remoteAddr}"

    /**
     * Using model classes with JSON.
     */
    @PostMapping("5")
    fun handler5(
        @Valid @RequestBody
        input: StudentInputModel?,
    ) = "Received student with name=${input?.name}, number=${input?.number}, " +
        "and enrollment year=${input?.enrollmentYear}"

    /**
     * Using both the [StudentInputModel] and an input model.
     */
    @PostMapping("6")
    fun handler6(
        request: HttpServletRequest,
        @Valid @RequestBody
        input: StudentInputModel,
    ) = "Accept: ${request.getHeader("Accept")}, Body: $input }"

    /**
     * Using more than one path variable.
     */
    @GetMapping("7/{aid}/b/{bid}")
    fun handler7(
        @PathVariable aid: Int,
        @PathVariable bid: String,
        req: HttpServletRequest,
    ) = "handler7 with aid=$aid and bid=$bid"
}