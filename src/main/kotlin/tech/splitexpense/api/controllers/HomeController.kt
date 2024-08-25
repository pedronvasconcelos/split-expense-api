package tech.splitexpense.api.controllers
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.views.View
import io.swagger.v3.oas.annotations.Hidden
import java.net.URI

@Controller("/")
class HomeController {

    companion object {
        private const val SWAGGER_UI = "/swagger-ui"
    }

    @Get
    @Hidden
    fun home(): HttpResponse<Any> {
        return HttpResponse.redirect(URI.create(SWAGGER_UI))
    }
}



@Controller("/swagger-ui")
class SwaggerController {

    @Get("/", produces = [MediaType.TEXT_HTML])
    @View("swagger-ui")
    fun index(): HttpResponse<Map<String, Any>> {
        val model = mapOf(
                "title" to "Your API Title",
                "contextPath" to "",
                "specURL" to "/swagger/swagger.yml"
        )
        return HttpResponse.ok(model)
    }
}