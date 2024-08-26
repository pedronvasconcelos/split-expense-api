package tech.splitexpense.groups.domain

import GroupId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tech.splitexpense.shared.models.EmailAddress
import tech.splitexpense.shared.models.Money
import tech.splitexpense.users.User
import tech.splitexpense.users.UserId
import tech.splitexpense.users.UserStatus
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.*

class ExpenseTest {
    @Test
    fun `cannot add expense with non-member participants`() {
        val owner = createUser("Owner")
        val nonMember = createUser("NonMember")
        val expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)

        assertThrows<IllegalArgumentException> {
            val expense = Expense(
                    ExpenseId(UUID.randomUUID()),
                    "Test Expense",
                    Money(BigDecimal("50.00")),
                    expenseGroup,
                    setOf(owner, nonMember),  // nonMember is not in the group
                    Instant.now(),
                    null,
                    null
            )
        }
    }

    private fun createUser(name: String): User {
        return User(
                UserId(UUID.randomUUID()),
                name,
                "LastName",
                EmailAddress("$name@email.com"),
                UserStatus.ACTIVE,
                LocalDate.of(1990, 1, 1)
        )
    }
}