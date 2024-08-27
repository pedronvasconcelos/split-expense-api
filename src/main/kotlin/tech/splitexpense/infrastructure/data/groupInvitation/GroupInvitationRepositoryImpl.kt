package tech.splitexpense.infrastructure.data.groupInvitation

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.GroupInvitation
import tech.splitexpense.groups.domain.GroupInvitationId
import tech.splitexpense.groups.domain.GroupInvitationRepository
import java.util.*

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class GroupInvitationRepositoryImpl : GroupInvitationRepository, CrudRepository<GroupInvitationEntity, UUID> {

        override fun save(groupInvitation: GroupInvitation): GroupInvitation {
            val groupInvitationEntity = GroupInvitationMapper.toEntity(groupInvitation)
            return GroupInvitationMapper.toDomain(save(groupInvitationEntity))
        }

        override fun getById(id: GroupInvitationId): GroupInvitation? {
            return findById(id.value).map { GroupInvitationMapper.toDomain(it) }.orElse(null)
        }

        override fun update(groupInvitation: GroupInvitation): GroupInvitation {
            val groupInvitationEntity = GroupInvitationMapper.toEntity(groupInvitation)
            return GroupInvitationMapper.toDomain(update(groupInvitationEntity))
        }
}