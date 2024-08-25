package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import tech.splitexpense.application.usecases.CompleteRegisterRequest
import tech.splitexpense.application.usecases.CompleteRegisterUseCase
import tech.splitexpense.application.usecases.UserAlreadyExistsException

@Controller("/api/users")
class UserController(private val completeRegisterUseCase: CompleteRegisterUseCase) {

    @Post("/register")
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