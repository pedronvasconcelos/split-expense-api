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
value class UserId(val value: UUID) : Comparable<UserId> {
    constructor(uuidString: String) : this(UUID.fromString(uuidString))

    init {
        require(value != UUID(0, 0)) { "User ID cannot be empty" }
    }

    override fun toString(): String = value.toString()

     fun isEquals(other: Any?): Boolean = when (other) {
        is UserId -> value == other.value
        is UUID -> value == other
        else -> false
    }


    override fun compareTo(other: UserId): Int = value.compareTo(other.value)

    companion object {
        fun randomUUID(): UserId = UserId(UUID.randomUUID())
    }
}

operator fun UUID.compareTo(other: UserId): Int = this.compareTo(other.value)
operator fun UserId.compareTo(other: UUID): Int = this.value.compareTo(other)

val UserId.uuid: UUID get() = this.value

fun UUID.toUserId(): UserId = UserId(this)
