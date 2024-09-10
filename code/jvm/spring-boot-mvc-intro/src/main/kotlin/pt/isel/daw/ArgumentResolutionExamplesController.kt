package pt.isel.daw

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
}