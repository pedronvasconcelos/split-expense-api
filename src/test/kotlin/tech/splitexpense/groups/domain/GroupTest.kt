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

class GroupTest {

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
        val group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        assertEquals("Test Group", group.name)
        assertEquals(setOf(owner), group.members)
        assertEquals(owner, group.owner)
        assertTrue(group.expenses.isEmpty())
        assertTrue(group.isActive)
    }

    @Test
    fun `create group with blank name throws exception`() {
        val owner = createUser("Owner")
        assertThrows<IllegalArgumentException> {
            Group.create(GroupId(UUID.randomUUID()), "", owner)
        }
    }

    @Test
    fun `add member to group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        group = group.addMember(member1)
        assertTrue(member1 in group.members)
        assertEquals(2, group.members.size)
    }

    @Test
    fun `remove member from group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        val member2 = createUser("Member2")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
                .addMember(member1)
                .addMember(member2)
        group = group.removeMember(member1)
        assertFalse(member1 in group.members)
        assertEquals(2, group.members.size)
    }

    @Test
    fun `cannot remove owner from group`() {
        val owner = createUser("Owner")
        val group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        assertThrows<IllegalArgumentException> {
            group.removeMember(owner)
        }
    }

    @Test
    fun `add expense to group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
                .addMember(member1)
        val expense = Expense(
                ExpenseId(UUID.randomUUID()),
                "Test Expense",
                Money(BigDecimal("50.00")),
                group,
                setOf(owner, member1),
                Instant.now(),
                null,
                null
        )
        group = group.addExpense(expense)
        assertEquals(1, group.expenses.size)
        assertEquals(Money(BigDecimal("50.00")), group.totalExpenses)
    }



    @Test
    fun `mark group as finished`() {
        val owner = createUser("Owner")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        group = group.markAsFinished()
        assertFalse(group.isActive)
        assertTrue(group.finishedAt != null)
    }

    @Test
    fun `delete group`() {
        val owner = createUser("Owner")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        group = group.delete()
        assertFalse(group.isActive)
        assertTrue(group.deletedAt != null)
    }

    @Test
    fun `cannot perform actions on inactive group`() {
        val owner = createUser("Owner")
        val member1 = createUser("Member1")
        var group = Group.create(GroupId(UUID.randomUUID()), "Test Group", owner)
        group = group.delete()

        assertThrows<IllegalArgumentException> { group.addMember(member1) }
        assertThrows<IllegalArgumentException> { group.removeMember(member1) }
        assertThrows<IllegalArgumentException> {
            group.addExpense(Expense(
                    ExpenseId(UUID.randomUUID()),
                    "Test Expense",
                    Money(BigDecimal("50.00")),
                    group,
                    setOf(owner),
                    Instant.now(),
                    null,
                    null
            ))
        }
        assertThrows<IllegalArgumentException> { group.markAsFinished() }
        assertThrows<IllegalArgumentException> { group.delete() }
    }
}