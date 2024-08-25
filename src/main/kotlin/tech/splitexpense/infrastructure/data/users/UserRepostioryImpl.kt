package tech.splitexpense.infrastructure.data.users

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import jakarta.inject.Singleton
import tech.splitexpense.users.User
import tech.splitexpense.users.UserRepository
import java.util.*

@Singleton
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class UserRepositoryImpl : UserRepository, CrudRepository<UserEntity, UUID> {

    override fun save(user: User): User {
        val userEntity = UserMapper.toEntity(user)
        return UserMapper.toDomain(save(userEntity))
    }

    override fun getById(id: UUID): User? {
        return findById(id).map { UserMapper.toDomain(it) }.orElse(null)
    }
    abstract fun findByEmail(email: String): UserEntity?

    override fun getByEmail(email: String): User? {
        return findByEmail(email)?.let { UserMapper.toDomain(it) }
    }




    override fun update(user: User): User {
        val userEntity = UserMapper.toEntity(user)
        return UserMapper.toDomain(update(userEntity))
    }


}