package pt.isel.daw

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class Application(
    private val argumentResolvers: List<HandlerMethodArgumentResolver>,
    private val messageConverters: List<HttpMessageConverter<*>>,
    private val interceptors: List<HandlerInterceptor>,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.forEach {
            resolvers.add(it)
        }
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        messageConverters.forEach {
            converters.add(it)
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        interceptors.forEach {
            registry.addInterceptor(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}