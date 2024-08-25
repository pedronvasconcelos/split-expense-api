package tech.splitexpense.users

interface UserRepository {
    fun save(user: User): User
    fun findById(id: String): User?
    fun findByEmail(email: String): User?
    fun update(user: User): User
}