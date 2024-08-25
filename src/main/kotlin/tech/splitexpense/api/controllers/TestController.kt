package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Controller("/test")
class TestController {


    @Get("/hello")
    @Operation(summary = "Hello world", description = "A simple hello world endpoint")
    @ApiResponse(responseCode = "200", description = "A simple hello world response")
    fun hello(@QueryValue(defaultValue = "World") name: String): HttpResponse<String> {
        return HttpResponse.ok("Hello, $name!")
    }


    @Get("/test")
    @Operation(
            summary = "Test endpoint",
            description = "This is a test endpoint"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful response"
    )
    fun sum(): HttpResponse<String> {
        return HttpResponse.ok("test")
    }
}
