package tech.splitexpense.api

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Factory
import io.micronaut.core.annotation.Nullable
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import jakarta.inject.Singleton

@Factory
class OpenApiConfiguration {

    @Singleton
    fun api(): OpenAPI {
        return OpenAPI()
                .info(Info()
                        .title("Split-Expense")
                        .version("1.0")
                        .description("Split-Expense API")
                )
    }
}

@ConfigurationProperties("swagger")
class SwaggerConfigurationProperties {
    var enabled: Boolean = true
    @Nullable
    var path: String? = null
}