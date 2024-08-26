package tech.splitexpense.groups.domain

import ExpenseGroup
import GroupId

interface ExpenseGroupRepository {
    fun save(expenseGroup: ExpenseGroup): ExpenseGroup
    fun getById(id: GroupId): ExpenseGroup?
    fun update(expenseGroup: ExpenseGroup): ExpenseGroup
}