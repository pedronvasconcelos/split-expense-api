package tech.splitexpense.groups.usecases

import GroupId
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.*
import tech.splitexpense.users.UserId
import tech.splitexpense.users.UserRepository
import tech.splitexpense.users.toUserId
import java.time.Instant
import java.util.*

@Singleton
class InviteUserToGroupUseCase(private val groupInvitationRepository: GroupInvitationRepository,
                               private val expenseGroupRepository: ExpenseGroupRepository,
                               private val userRepository : UserRepository) {

        fun execute(request: InviteUserToGroupRequest): InviteUserToGroupResponse {
            val group = expenseGroupRepository.getById(request.toGroupId())
            requireNotNull(group) { "Group not found" }
            require(group.owner.id == request.toInvitedByUserId()) { "Only the group owner can invite users" }


            val invitedUser = userRepository.getById(request.invitedUserId);
            requireNotNull(invitedUser) { "Invited user not found" }



            val invitation = GroupInvitation(
                    id = GroupInvitationId(UUID.randomUUID()),
                    group = group,
                    invitedUser = invitedUser,
                    invitedByUser = group.owner,
                    status = GroupInvitationStatus.PENDING,
                    createdAt = Instant.now(),
                    respondedAt = null,
                    message = request.message
            )
            groupInvitationRepository.save(invitation)
            return InviteUserToGroupResponse(invitation.id.toString())

        }

}

@Introspected
@Serdeable
data class InviteUserToGroupRequest(
        val groupId: UUID,
        val invitedUserId: UUID,
        val invitedByUserId: UUID,
        val message: String?
) {
    fun toGroupId() = GroupId(groupId)
    fun toInvitedUserId() = UserId(invitedUserId)
    fun toInvitedByUserId() = UserId(invitedByUserId)
}

@Introspected
@Serdeable
data class InviteUserToGroupResponse(
    val invitationId: String
)