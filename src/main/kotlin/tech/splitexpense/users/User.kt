package tech.splitexpense.users

import tech.splitexpense.shared.models.EmailAddress
import java.time.Instant
import java.time.LocalDate
import java.util.*

data class User (val id: UserId  = UserId(UUID.randomUUID()),
            val firstName: String,
            val lastName: String,
            val email: EmailAddress,
            val status: UserStatus = UserStatus.ACTIVE,
            val birthDate: LocalDate? = null,
            val createdAt: Instant = Instant.now(),
            val updatedAt: Instant? = null,
            val deletedAt: Instant? = null,
            val roles: UserRoles = UserRoles.USER,
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
