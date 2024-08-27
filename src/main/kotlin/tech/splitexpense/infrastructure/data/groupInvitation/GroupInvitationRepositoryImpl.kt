package tech.splitexpense.infrastructure.data.groupInvitation

import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.GroupInvitation
import tech.splitexpense.groups.domain.GroupInvitationId
import tech.splitexpense.groups.domain.GroupInvitationRepository
import tech.splitexpense.infrastructure.data.groups.ExpenseGroupEntity
import java.util.*

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class GroupInvitationRepositoryImpl : GroupInvitationRepository, CrudRepository<GroupInvitationEntity, UUID> {

    @Join(value = "group", type = Join.Type.FETCH)
    @Join(value = "group.owner", type = Join.Type.FETCH)
    @Join(value = "group.members", type = Join.Type.FETCH)
    @Join(value = "invitedUser", type = Join.Type.FETCH)
    @Join(value = "invitedByUser", type = Join.Type.FETCH)
    abstract override fun findById(id: UUID): Optional<GroupInvitationEntity>
    override fun save(groupInvitation: GroupInvitation): GroupInvitation {
        val groupInvitationEntity = GroupInvitationMapper.toEntity(groupInvitation)
        return GroupInvitationMapper.toDomain(save(groupInvitationEntity))
    }

    override fun getById(id: GroupInvitationId): GroupInvitation? {
        val groupEntity = findById(id.value)
        return if (groupEntity.isPresent) {
            GroupInvitationMapper.toDomain(groupEntity.get())
        } else {
            null
        }
    }

    override fun update(groupInvitation: GroupInvitation): GroupInvitation {
        val groupInvitationEntity = GroupInvitationMapper.toEntity(groupInvitation)
        return GroupInvitationMapper.toDomain(update(groupInvitationEntity))
    }
}