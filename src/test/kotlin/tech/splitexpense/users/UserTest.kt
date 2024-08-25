package tech.splitexpense.users

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tech.splitexpense.shared.EmailAddress
import java.time.LocalDate
import java.util.*
import org.junit.jupiter.api.Assertions.*

class UserTest {

    @Test
    fun `should create a valid user`() {
        val user = User(
                firstName = "John",
                lastName = "Doe",
                email = EmailAddress("john.doe@example.com"),
                birthDate = LocalDate.of(1990, 1, 1)
        )

        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("john.doe@example.com", user.email.toString())
        assertEquals(LocalDate.of(1990, 1, 1), user.birthDate)
        assertEquals(UserStatus.ACTIVE, user.status)
        assertEquals(UserRoles.USER, user.roles)
        assertNotNull(user.id)
        assertNotNull(user.createdAt)
        assertNull(user.updatedAt)
        assertNull(user.deletedAt)
    }

    @Test
    fun `should throw exception for blank first name`() {
        assertThrows<IllegalArgumentException> {
            User(
                    firstName = "",
                    lastName = "Doe",
                    email = EmailAddress("john.doe@example.com"),
                    birthDate = LocalDate.of(1990, 1, 1)
            )
        }
    }

    @Test
    fun `should throw exception for blank last name`() {
        assertThrows<IllegalArgumentException> {
            User(
                    firstName = "John",
                    lastName = "",
                    email = EmailAddress("john.doe@example.com"),
                    birthDate = LocalDate.of(1990, 1, 1)
            )
        }
    }

    @Test
    fun `should throw exception for null birth date`() {
        assertThrows<IllegalArgumentException> {
            User(
                    firstName = "John",
                    lastName = "Doe",
                    email = EmailAddress("john.doe@example.com"),
                    birthDate = null
            )
        }
    }

    @Test
    fun `should allow custom id`() {
        val customId = UUID.randomUUID()
        val user = User(
                id = customId,
                firstName = "John",
                lastName = "Doe",
                email = EmailAddress("john.doe@example.com"),
                birthDate = LocalDate.of(1990, 1, 1)
        )

        assertEquals(customId, user.id)
    }

    @Test
    fun `should allow custom status and roles`() {
        val user = User(
                firstName = "John",
                lastName = "Doe",
                email = EmailAddress("john.doe@example.com"),
                birthDate = LocalDate.of(1990, 1, 1),
                status = UserStatus.INACTIVE,
                roles = UserRoles.ADMIN
        )

        assertEquals(UserStatus.INACTIVE, user.status)
        assertEquals(UserRoles.ADMIN, user.roles)
    }
}