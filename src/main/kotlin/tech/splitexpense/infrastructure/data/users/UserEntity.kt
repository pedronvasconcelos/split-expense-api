package tech.splitexpense.infrastructure.data.users

import io.micronaut.data.annotation.*
import io.micronaut.serde.annotation.Serdeable
import tech.splitexpense.users.UserRoles
import tech.splitexpense.users.UserStatus
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Serdeable
@MappedEntity("users")
data class UserEntity(
        @field:Id
        @field:MappedProperty("id")
        var id: UUID = UUID.randomUUID(),

        @field:MappedProperty("first_name")
        var firstName: String,

        @field:MappedProperty("last_name")
        var lastName: String,

        @field:MappedProperty("email")
        var email: String,

        @MappedProperty("status")
        var status: UserStatus = UserStatus.ACTIVE,

        @field:MappedProperty("birth_date")
        var birthDate: LocalDate? = null,

        @field:MappedProperty("created_at")
        var createdAt: Instant = Instant.now(),

        @field:MappedProperty("updated_at")
        var updatedAt: Instant? = null,

        @field:MappedProperty("deleted_at")
        var deletedAt: Instant? = null,

        @MappedProperty("role")
        var role: UserRoles = UserRoles.USER
)