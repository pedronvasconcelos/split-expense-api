package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/test")
class TestController {

    @Get("/hello")
    fun hello(@QueryValue(defaultValue = "World") name: String): HttpResponse<String> {
        return HttpResponse.ok("Hello, $name!")
    }

    @Get("/test")
    fun sum(): HttpResponse<String> {
        return HttpResponse.ok("test")
    }
}
