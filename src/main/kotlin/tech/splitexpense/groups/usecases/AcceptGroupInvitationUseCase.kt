package tech.splitexpense.groups.usecases

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.ExpenseGroupRepository
import tech.splitexpense.groups.domain.GroupInvitationId
import tech.splitexpense.groups.domain.GroupInvitationRepository
import tech.splitexpense.users.UserRepository
import java.util.*

@Singleton
class AcceptGroupInvitationUseCase(private val groupRepository: ExpenseGroupRepository,
                                   private val userRepository: UserRepository,
                                   private val invitationRepository: GroupInvitationRepository
) {
    fun execute(request: AcceptGroupInvitationRequest): AcceptGroupInvitationResponse {
        val user = userRepository.getById(request.userId)
                ?: throw IllegalArgumentException("User with ID ${request.userId} not found")

        val invitation = invitationRepository.getById(GroupInvitationId(request.invitationId))
                ?: throw IllegalArgumentException("Invitation with ID ${request.invitationId} not found")

        if(invitation.invitedUser.id != user.id) {
            throw IllegalArgumentException("User with ID ${request.userId} is not the recipient of the invitation")
        }
        val group = groupRepository.getById(invitation.group.id)
                ?: throw IllegalArgumentException("Group with ID ${invitation.group.id} not found")


        val updatedGroup = group.addMember(user)

        groupRepository.update(updatedGroup)

        val acceptedInvitation = invitation.accept()
        invitationRepository.update(acceptedInvitation)

        return AcceptGroupInvitationResponse(
                groupId = group.id.value,
                groupName = group.name
        )
    }
}

@Introspected
@Serdeable
data class AcceptGroupInvitationRequest(
        val userId: UUID,
        val invitationId: UUID
)

@Introspected
@Serdeable
data class AcceptGroupInvitationResponse(
        val groupId: UUID,
        val groupName: String
)