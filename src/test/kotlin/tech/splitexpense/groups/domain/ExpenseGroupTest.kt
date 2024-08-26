package tech.splitexpense.groups.domain

import GroupId
import org.junit.jupiter.api.Assertions.*
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
import java.util.UUID

class ExpenseGroupTest {

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

    @Test
    fun `create group successfully`() {
        val owner = createUser("Owner")
        val expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        assertEquals("Test Group", expenseGroup.name)
        assertEquals(setOf(owner), expenseGroup.members)
        assertEquals(owner, expenseGroup.owner)
        assertTrue(expenseGroup.expenses.isEmpty())
        assertTrue(expenseGroup.isActive)
    }

    @Test
    fun `create group with blank name throws exception`() {
        val owner = createUser("Owner")
        assertThrows<IllegalArgumentException> {
            ExpenseGroup.create(GroupId(UUID.randomUUID()), "", owner, GroupCategory.OTHER)
        }
    }

    @Test
    fun `add member to group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        expenseGroup = expenseGroup.addMember(member1)
        assertTrue(member1 in expenseGroup.members)
        assertEquals(2, expenseGroup.members.size)
    }

    @Test
    fun `remove member from group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        val member2 = createUser("Member2")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
                .addMember(member1)
                .addMember(member2)
        expenseGroup = expenseGroup.removeMember(member1)
        assertFalse(member1 in expenseGroup.members)
        assertEquals(2, expenseGroup.members.size)
    }

    @Test
    fun `cannot remove owner from group`() {
        val owner = createUser("Owner")
        val expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        assertThrows<IllegalArgumentException> {
            expenseGroup.removeMember(owner)
        }
    }

    @Test
    fun `add expense to group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
                .addMember(member1)
        val expense = Expense(
                ExpenseId(UUID.randomUUID()),
                "Test Expense",
                Money(BigDecimal("50.00")),
                expenseGroup,
                setOf(owner, member1),
                Instant.now(),
                null,
                null
        )
        expenseGroup = expenseGroup.addExpense(expense)
        assertEquals(1, expenseGroup.expenses.size)
        assertEquals(Money(BigDecimal("50.00")), expenseGroup.totalExpenses)
    }



    @Test
    fun `mark group as finished`() {
        val owner = createUser("Owner")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        expenseGroup = expenseGroup.markAsFinished()
        assertFalse(expenseGroup.isActive)
        assertTrue(expenseGroup.finishedAt != null)
    }

    @Test
    fun `delete group`() {
        val owner = createUser("Owner")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        expenseGroup = expenseGroup.delete()
        assertFalse(expenseGroup.isActive)
        assertTrue(expenseGroup.deletedAt != null)
    }

    @Test
    fun `cannot perform actions on inactive group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var expenseGroup = ExpenseGroup.create(GroupId(UUID.randomUUID()), "Test Group", owner, GroupCategory.OTHER)
        expenseGroup = expenseGroup.delete()

        assertThrows<IllegalArgumentException> { expenseGroup.addMember(member1) }
        assertThrows<IllegalArgumentException> { expenseGroup.removeMember(member1) }
        assertThrows<IllegalArgumentException> {
            expenseGroup.addExpense(Expense(
                    ExpenseId(UUID.randomUUID()),
                    "Test Expense",
                    Money(BigDecimal("50.00")),
                    expenseGroup,
                    setOf(owner),
                    Instant.now(),
                    null,
                    null
            ))
        }
        assertThrows<IllegalArgumentException> { expenseGroup.markAsFinished() }
        assertThrows<IllegalArgumentException> { expenseGroup.delete() }
    }
}