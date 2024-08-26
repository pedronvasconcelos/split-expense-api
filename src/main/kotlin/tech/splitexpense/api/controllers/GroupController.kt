package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import tech.splitexpense.groups.usecases.CreateGroupRequest
import tech.splitexpense.groups.usecases.CreateNewGroupUseCase

@Controller("/group")
class GroupController(private val createNewGroupUseCase: CreateNewGroupUseCase) {


    @Post("/create")
    @Operation(summary = "Create a new group", description = "Create a new group")
    @ApiResponse(responseCode = "201", description = "Group created successfully")
    fun createGroup(@Body request: CreateGroupRequest): HttpResponse<Any> {
        return try {
            val response = createNewGroupUseCase.execute(request)
            HttpResponse.created(response)
        }
        catch (e: IllegalArgumentException) {
            HttpResponse.badRequest(mapOf("error" to e.message))
        }
        catch (e: Exception) {
            HttpResponse.serverError(mapOf("error" to "An unexpected error occurred"))
        }
    }
}