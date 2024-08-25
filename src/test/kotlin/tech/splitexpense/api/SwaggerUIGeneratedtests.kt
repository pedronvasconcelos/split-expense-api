package tech.splitexpense.api

import io.micronaut.core.io.ResourceLoader
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest(startApplication = false)
class SwaggerUiGeneratedTest {

    @Test
    fun buildGeneratesSwaggerUI(resourceLoader: ResourceLoader) {

        assertTrue(resourceLoader.getResource("META-INF/swagger/views/swagger-ui/index.html").isPresent)
    }
}
