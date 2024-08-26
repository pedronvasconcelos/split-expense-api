package tech.splitexpense.groups.domain

import Group
import tech.splitexpense.shared.models.Money
import tech.splitexpense.users.User
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Expense(var id : ExpenseId,
              var name: String,
              val amount: Money,
              val group: Group,
              val participants: Set<User>,
              var createdAt: Instant,
              var updatedAt: Instant?,
              var deletedAt: Instant?

        ) {
    init {
        validate()
    }

    constructor(name: String, amount: BigDecimal, group: Group, participants: Set<User>) : this(
        ExpenseId(UUID.randomUUID()),
        name,
        Money(amount),
        group,
        participants,
        Instant.now(),
        null,
        null
    )


    private fun validate() {
        require(name.isNotBlank()) { "Expense name cannot be blank" }
        require(participants.isNotEmpty()) { "Expense must have at least one participant" }
        require(participants.all { it in group.members }) { "All participants must be members of the group" }
    }
}

@JvmInline
value class ExpenseId(val value: UUID)