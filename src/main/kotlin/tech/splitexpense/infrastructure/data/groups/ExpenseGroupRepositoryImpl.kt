package tech.splitexpense.infrastructure.data.groups

import ExpenseGroup
import GroupId
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Singleton

import tech.splitexpense.groups.domain.ExpenseGroupRepository
import java.util.UUID

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class ExpenseGroupRepositoryImpl : ExpenseGroupRepository, CrudRepository<ExpenseGroupEntity, UUID> {

    override fun save(expenseGroup: ExpenseGroup): ExpenseGroup {
        val groupEntity = GroupMapper.toEntity(expenseGroup)
        return GroupMapper.toDomain(save(groupEntity))
    }

    override fun getById(id: GroupId): ExpenseGroup? {
        return findById(id.value).map { GroupMapper.toDomain(it) }.orElse(null)
    }

    override fun update(expenseGroup: ExpenseGroup): ExpenseGroup {
        val groupEntity = GroupMapper.toEntity(expenseGroup)
        return GroupMapper.toDomain(update(groupEntity))
    }





}