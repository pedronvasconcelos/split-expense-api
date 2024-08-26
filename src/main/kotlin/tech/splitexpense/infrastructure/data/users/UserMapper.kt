package tech.splitexpense.infrastructure.data.users

import jakarta.inject.Singleton
import tech.splitexpense.shared.models.EmailAddress
import tech.splitexpense.users.User
import tech.splitexpense.users.UserId


@Singleton
object UserMapper {
    fun toEntity(user: User): UserEntity {
        return UserEntity(
                id = user.id.toUUID(),
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email.toString(),
                status = user.status,
                birthDate = user.birthDate,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                deletedAt = user.deletedAt,
                role = user.roles,
        )
    }

    fun toDomain(entity: UserEntity): User {
        return User(
                id = UserId(entity.id),
                firstName = entity.firstName,
                lastName = entity.lastName,
                email = EmailAddress(entity.email),
                status = entity.status,
                birthDate = entity.birthDate,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                deletedAt = entity.deletedAt,
                roles = entity.role,
        )
    }
}