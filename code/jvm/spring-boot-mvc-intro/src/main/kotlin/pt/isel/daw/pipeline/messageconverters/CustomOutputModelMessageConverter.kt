package pt.isel.daw.pipeline.messageconverters

import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import pt.isel.daw.controllers.CustomOutputModel

@Component
class CustomOutputModelMessageConverter : MappingJackson2HttpMessageConverter() {
    override fun canWrite(clazz: Class<*>, mediaType: MediaType?) =
        (mediaType == null || mediaType == MEDIA_TYPE) && CustomOutputModel::class.java.isAssignableFrom(clazz)

    override fun canWrite(mediaType: MediaType?) = mediaType == null || mediaType == MEDIA_TYPE

    override fun getSupportedMediaTypes() = listOf(MEDIA_TYPE)

    companion object {
        val MEDIA_TYPE = MediaType("application", "vnd.custom+json")
    }
}