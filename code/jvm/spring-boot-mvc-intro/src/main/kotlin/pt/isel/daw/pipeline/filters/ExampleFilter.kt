package pt.isel.daw.pipeline.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pt.isel.daw.pipeline.interceptors.ExampleInterceptor

@Component
class ExampleFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val start = System.nanoTime()

        chain.doFilter(request, response)

        val end = System.nanoTime()
        val method = request.method
        val uri = request.requestURI
        val status = response.status
        val handler = ExampleInterceptor.getFromAttributes(request)
        logger.info(
            "Request ending: method={}, uri={}, status={}, duration={}, handler={}",
            method,
            uri,
            status,
            (end - start) / 1000.0,
            handler?.shortLogMessage ?: "<na>",
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ExampleFilter::class.java)
    }
}