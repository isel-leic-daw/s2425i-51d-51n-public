package pt.isel.daw.pipeline.interceptors

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ExampleInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            addToAttributes(handler, request)
        }
        return true
    }

    companion object {
        private const val KEY = "example-interceptor"

        fun addToAttributes(handler: HandlerMethod, request: HttpServletRequest) {
            request.setAttribute(KEY, handler)
        }

        fun getFromAttributes(request: HttpServletRequest): HandlerMethod? = request.getAttribute(KEY) as? HandlerMethod
    }
}