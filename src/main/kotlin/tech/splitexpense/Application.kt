package tech.splitexpense

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
		info = Info(
				title = "split-expense",
				version = "1.0",
				description = "Split Expense API",
				contact = Contact(url = "https://pedrovasconcelos.tech"),
				license = License( name = "Apache 2.0"	)
		)
)
class Application

fun main(args: Array<String>) {
	Micronaut.run(Application::class.java, *args)
}
