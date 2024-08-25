package tech.splitexpense.api

import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import tech.splitexpense.shared.AppConfig

@MicronautTest
class AppConfigTest {

    @Inject
    lateinit var appConfig: AppConfig

    @Test
    fun testAppConfigProperties() {
        assertTrue(appConfig.hello != null)
        assertEquals("Hello World", appConfig.hello)
    }


}