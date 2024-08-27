package tech.splitexpense.infrastructure.data.groupInvitation

import io.micronaut.data.annotation.*
import io.micronaut.data.model.DataType
import io.micronaut.serde.annotation.Serdeable
import tech.splitexpense.groups.domain.GroupInvitationStatus
import tech.splitexpense.infrastructure.data.groups.ExpenseGroupEntity
import tech.splitexpense.infrastructure.data.users.UserEntity
import tech.splitexpense.users.User
import java.time.Instant
import java.util.UUID
import javax.swing.GroupLayout.Group

@Serdeable
@MappedEntity("group_invitations")
data class GroupInvitationEntity(
        @field:Id
        @field:AutoPopulated
        @field:MappedProperty("id")
        var id: UUID = UUID.randomUUID(),

        @Relation(value = Relation.Kind.MANY_TO_ONE)
        @field:MappedProperty("group_id")
        var group: ExpenseGroupEntity,

        @Relation(value = Relation.Kind.MANY_TO_ONE)
        @field:MappedProperty("invited_user_id")
        var invitedUser: UserEntity,

        @Relation(value = Relation.Kind.MANY_TO_ONE)
        @field:MappedProperty("invited_by_user_id")
        var invitedByUser: UserEntity,

        @field:MappedProperty("status")
        @TypeDef(type = DataType.STRING)
        var status: GroupInvitationStatus,

        @field:MappedProperty("created_at")
        var createdAt: Instant,

        @field:MappedProperty("responded_at")
        var respondedAt: Instant? = null,

        @field:MappedProperty("message")
        var message: String? = null,

        @field:MappedProperty("updated_at")
        var updatedAt: Instant? = null
)