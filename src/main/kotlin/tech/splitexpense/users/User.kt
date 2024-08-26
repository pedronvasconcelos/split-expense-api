package tech.splitexpense.users

import tech.splitexpense.shared.models.EmailAddress
import java.time.Instant
import java.time.LocalDate
import java.util.*

data class User (var id: UserId  = UserId(UUID.randomUUID()),
            var firstName: String,
            var lastName: String,
            var email: EmailAddress,
            var status: UserStatus = UserStatus.ACTIVE,
            var birthDate: LocalDate? = null,
            var createdAt: Instant = Instant.now(),
            var updatedAt: Instant? = null,
            var deletedAt: Instant? = null,
            var roles: UserRoles = UserRoles.USER,
        ){
    init {
        validate()
    }

     fun validate () {
        if (firstName.isBlank()) {
            throw IllegalArgumentException("First name cannot be blank")
        }
        if (lastName.isBlank()) {
            throw IllegalArgumentException("Last name cannot be blank")
        }
        if (birthDate == null) {
            throw IllegalArgumentException("Birth date cannot be null")
        }
     }
}

enum class UserStatus {
    ACTIVE, INACTIVE
}
enum class UserRoles {
     USER, PREMIUM, ADMIN
}


@JvmInline
value class UserId(val value: UUID) {
    init {
        require(value != UUID(0, 0)) { "User ID cannot be empty" }
    }

    override fun toString(): String = value.toString()


    fun toUUID(): UUID {
        return value
    }
}


