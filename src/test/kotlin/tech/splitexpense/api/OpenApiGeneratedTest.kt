package tech.splitexpense.api

import io.micronaut.core.io.ResourceLoader
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class OpenApiGeneratedTest {
    @Test
    fun buildGeneratesOpenApi(resourceLoader : ResourceLoader ) {
        assertTrue(resourceLoader.getResource("META-INF/swagger/micronaut-guides-1.0.yml").isPresent());

    }
}