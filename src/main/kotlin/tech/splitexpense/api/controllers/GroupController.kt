package tech.splitexpense.api.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import tech.splitexpense.groups.usecases.*

@Controller("/group")
class GroupController(private val createNewGroupUseCase: CreateNewGroupUseCase,
                      private val inviteUserToGroupUseCase: InviteUserToGroupUseCase,
                      private val acceptGroupInvitationUseCase: AcceptGroupInvitationUseCase,
                      private val removeUserFromGroupUseCase: RemoveUserFromGroupUseCase){


    @Post("/")
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

    @Post("/invite")
    @Operation(summary = "Invite a user to a group", description = "Invite a user to a group")
    @ApiResponse(responseCode = "200", description = "User invited successfully")
    fun inviteUserToGroup(@Body request: InviteUserToGroupRequest): HttpResponse<Any> {
        return try {
            val response = inviteUserToGroupUseCase.execute(request)
            HttpResponse.created(response)
        }
        catch (e: IllegalArgumentException) {
            HttpResponse.badRequest(mapOf("error" to e.message))
        }
        catch (e: Exception) {
            HttpResponse.serverError(mapOf("error" to "An unexpected error occurred"))
        }
    }

    @Post("/accept-invitation")
    @Operation(summary = "Accept a group invitation", description = "Accept a group invitation")
    @ApiResponse(responseCode = "200", description = "Invitation accepted successfully")
    fun acceptGroupInvitation(@Body request: AcceptGroupInvitationRequest): HttpResponse<Any> {
        return try {
            val response = acceptGroupInvitationUseCase.execute(request)
            HttpResponse.ok(response)
        }
        catch (e: IllegalArgumentException) {
            HttpResponse.badRequest(mapOf("error" to e.message))
        }
        catch (e: Exception) {
            HttpResponse.serverError(mapOf("error" to "An unexpected error occurred"))
        }
    }

    @Put("/remove-user")
    @Operation(summary = "Remove a user from a group", description = "Remove a user from a group")
    @ApiResponse(responseCode = "200", description = "User removed successfully")
    fun removeUserFromGroup(@Body request: RemoveUserFromGroupRequest): HttpResponse<Any> {
        return try {
            val response = removeUserFromGroupUseCase.execute(request)
            HttpResponse.ok(response)
        } 
        catch (e: IllegalArgumentException) {
            HttpResponse.badRequest(mapOf("error" to e.message))
        }
        catch (e: Exception) {
            HttpResponse.serverError(mapOf("error" to "An unexpected error occurred"))
        }
    }
}