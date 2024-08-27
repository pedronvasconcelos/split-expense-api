package tech.splitexpense.infrastructure.data.groupInvitation

import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.GroupInvitation
import tech.splitexpense.groups.domain.GroupInvitationId
import tech.splitexpense.infrastructure.data.groups.GroupMapper
import tech.splitexpense.infrastructure.data.users.UserMapper

@Singleton
object GroupInvitationMapper {
    fun toEntity(groupInvitation: GroupInvitation): GroupInvitationEntity {
        return GroupInvitationEntity(
                id = groupInvitation.id.value,
                group = GroupMapper.toEntity(groupInvitation.group),
                invitedUser = UserMapper.toEntity(groupInvitation.invitedUser),
                invitedByUser = UserMapper.toEntity(groupInvitation.invitedByUser),
                status = groupInvitation.status,
                createdAt = groupInvitation.createdAt,
                respondedAt = groupInvitation.respondedAt,
                message = groupInvitation.message
        )
    }

    fun toDomain(entity: GroupInvitationEntity): GroupInvitation {
        return GroupInvitation(
                id = GroupInvitationId(entity.id),
                group = GroupMapper.toDomain(entity.group),
                invitedUser = UserMapper.toDomain(entity.invitedUser),
                invitedByUser = UserMapper.toDomain(entity.invitedByUser),
                status = entity.status,
                createdAt = entity.createdAt,
                respondedAt = entity.respondedAt,
                message = entity.message
        )
    }
}