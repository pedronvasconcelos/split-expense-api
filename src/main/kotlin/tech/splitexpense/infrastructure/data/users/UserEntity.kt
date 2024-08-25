package tech.splitexpense.infrastructure.data.users

import jakarta.persistence.*
import tech.splitexpense.users.UserRoles
import tech.splitexpense.users.UserStatus
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @Column(name = "id")
        var id: UUID = UUID.randomUUID(),

        @Column(name = "first_name", nullable = false)
        var firstName: String,

        @Column(name = "last_name", nullable = false)
        var lastName: String,

        @Column(name = "email", nullable = false, unique = true)
        var email: String,

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false)
        var status: UserStatus = UserStatus.ACTIVE,

        @Column(name = "birth_date")
        var birthDate: LocalDate?,

        @Column(name = "created_at", nullable = false)
        var createdAt: Instant = Instant.now(),

        @Column(name = "updated_at")
        var updatedAt: Instant? = null,

        @Column(name = "deleted_at")
        var deletedAt: Instant? = null,

        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false)
        var role: UserRoles = UserRoles.USER
)