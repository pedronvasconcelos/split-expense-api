package tech.splitexpense.infrastructure.data.groups

import ExpenseGroup
import GroupId
import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Singleton

import tech.splitexpense.groups.domain.ExpenseGroupRepository
import java.util.*

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class ExpenseGroupRepositoryImpl : ExpenseGroupRepository, CrudRepository<ExpenseGroupEntity, UUID> {
    @Join(value = "owner", type = Join.Type.FETCH)
    @Join(value = "members", type = Join.Type.LEFT_FETCH)
    abstract override fun findById(id: UUID): Optional<ExpenseGroupEntity>
    override fun save(expenseGroup: ExpenseGroup): ExpenseGroup {
        val groupEntity = GroupMapper.toEntity(expenseGroup)
        return GroupMapper.toDomain(save(groupEntity))
    }

    override fun getById(id: GroupId): ExpenseGroup? {

        var group =  findById(id.value)
        if(group.isPresent){
            return GroupMapper.toDomain(group.get())
        }
        return null
    }


    override fun update(expenseGroup: ExpenseGroup): ExpenseGroup {
        val existingGroup = findById(expenseGroup.id.value).orElseThrow { Exception("Group not found") }
        val updatedGroupEntity = GroupMapper.toEntity(expenseGroup)

        updatedGroupEntity.members = updatedGroupEntity.members
                .filterNot { updatedMember ->
                    existingGroup.members.any { it.id == updatedMember.id }
                }
                .toMutableSet()

        return GroupMapper.toDomain(update(updatedGroupEntity))
    }





}