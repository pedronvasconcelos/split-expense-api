package tech.splitexpense.infrastructure.data.expenses

import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.Expense
import tech.splitexpense.groups.domain.ExpenseId
 import tech.splitexpense.infrastructure.data.groups.GroupMapper
import tech.splitexpense.infrastructure.data.users.UserMapper
import tech.splitexpense.shared.models.Money


@Singleton
object ExpenseMapper {
    fun toEntity(expense: Expense): ExpenseEntity {
        return ExpenseEntity(
                id = expense.id.toUUID(),
                name = expense.name,
                amount = expense.amount.value,
                group = GroupMapper.toEntity(expense.expenseGroup),
                participants = expense.participants.map { UserMapper.toEntity(it) }.toSet(),
                createdAt = expense.createdAt,
                updatedAt = expense.updatedAt,
                deletedAt = expense.deletedAt
        )
    }

    fun toDomain(entity: ExpenseEntity): Expense {
        return Expense(
                id = ExpenseId(entity.id),
                name = entity.name,
                amount = Money(entity.amount),
                expenseGroup = GroupMapper.toDomain(entity.group),
                participants = entity.participants.map { UserMapper.toDomain(it) }.toSet(),
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                deletedAt = entity.deletedAt
        )
    }
}