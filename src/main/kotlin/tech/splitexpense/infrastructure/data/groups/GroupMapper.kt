package tech.splitexpense.infrastructure.data.groups

import ExpenseGroup
import GroupId
import jakarta.inject.Singleton
import tech.splitexpense.infrastructure.data.expenses.ExpenseMapper


import tech.splitexpense.infrastructure.data.users.UserMapper

@Singleton
object GroupMapper {
    fun toEntity(expenseGroup: ExpenseGroup): ExpenseGroupEntity {
        return ExpenseGroupEntity(
                id = expenseGroup.id.toUUID(),
                name = expenseGroup.name,
                members = expenseGroup.members.map { UserMapper.toEntity(it) }.toSet(),
                expenses = expenseGroup.expenses.map { ExpenseMapper.toEntity(it) }.toSet(),
                createdAt = expenseGroup.createdAt,
                updatedAt = expenseGroup.updatedAt,
                owner = UserMapper.toEntity(expenseGroup.owner),
                deletedAt = expenseGroup.deletedAt,
                finishedAt = expenseGroup.finishedAt,
                category = expenseGroup.category
        )
    }

    fun toDomain(entity: ExpenseGroupEntity): ExpenseGroup {
        return ExpenseGroup(
                id = GroupId(entity.id),
                name = entity.name,
                members = entity.members.map { UserMapper.toDomain(it) }.toSet(),
                expenses = entity.expenses.map { ExpenseMapper.toDomain(it) }.toSet(),
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                owner = UserMapper.toDomain(entity.owner),
                deletedAt = entity.deletedAt,
                finishedAt = entity.finishedAt,
                category = entity.category
        )
    }
}