package tech.splitexpense.infrastructure.data.users

import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import tech.splitexpense.users.User
import tech.splitexpense.users.UserRepository
import java.util.*

@Singleton
open class UserRepositoryImpl : UserRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    override fun save(user: User): User {
        val userEntity = UserMapper.toEntity(user)
        entityManager.persist(userEntity)
        return UserMapper.toDomain(userEntity)
    }

    override fun findById(id: UUID): User? {
        val userEntity = entityManager.find(UserEntity::class.java, id)
        return userEntity?.let { UserMapper.toDomain(it) }
    }



    override fun findByEmail(email: String): User? {
        val query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email",
                UserEntity::class.java
        )
        query.setParameter("email", email)
        return query.resultList.firstOrNull()?.let { UserMapper.toDomain(it) }
    }

    @Transactional
    override fun update(user: User): User {
        val userEntity = UserMapper.toEntity(user)
        val updatedEntity = entityManager.merge(userEntity)
        return UserMapper.toDomain(updatedEntity)
    }

}