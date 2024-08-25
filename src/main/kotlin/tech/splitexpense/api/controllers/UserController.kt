package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import tech.splitexpense.application.usecases.CompleteRegisterRequest
import tech.splitexpense.application.usecases.CompleteRegisterUseCase
import tech.splitexpense.application.usecases.UserAlreadyExistsException

@Controller("/api/users")
class UserController(private val completeRegisterUseCase: CompleteRegisterUseCase) {


    @Post("/register")
    @Operation(summary = "Register a new user", description = "Register a new user in the system")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    fun registerUser(@Body request: CompleteRegisterRequest): HttpResponse<Any> {
        return try {
            val response = completeRegisterUseCase.execute(request)
            HttpResponse.created(response)
        } catch (e: UserAlreadyExistsException) {
            HttpResponse.status<Any>(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        } catch (e: IllegalArgumentException) {
            HttpResponse.badRequest(mapOf("error" to e.message))
        } catch (e: Exception) {
            HttpResponse.serverError(mapOf("error" to "An unexpected error occurred"))
        }
    }
}