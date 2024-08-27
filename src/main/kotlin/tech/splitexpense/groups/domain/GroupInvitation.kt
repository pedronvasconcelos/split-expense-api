package tech.splitexpense.groups.domain

import ExpenseGroup
import GroupId
import tech.splitexpense.users.User
import tech.splitexpense.users.UserId
import java.time.Instant
import java.util.*

data class GroupInvitation (
        val id: GroupInvitationId = GroupInvitationId(UUID.randomUUID()),
        val group: ExpenseGroup,
        val invitedUser: User,
        val invitedByUser: User,
        val status: GroupInvitationStatus = GroupInvitationStatus.PENDING,
        val createdAt: Instant,
        val respondedAt: Instant?,
        val message: String?

        ) {

    fun accept(): GroupInvitation {
        return this.copy(status = GroupInvitationStatus.ACCEPTED, respondedAt = Instant.now())
    }

}

@JvmInline
value class GroupInvitationId(val value: UUID) {
    init {
        require(value != UUID(0, 0)) { "Group Invitation ID cannot be empty" }
    }

    override fun toString(): String = value.toString()

    fun toUUID(): UUID {
        return value
    }
}

enum class GroupInvitationStatus {
    PENDING, ACCEPTED, REJECTED, CANCELLED
}



