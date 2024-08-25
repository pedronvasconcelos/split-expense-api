package tech.splitexpense.shared

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app")
class AppConfig {
   var hello : String? = null
}