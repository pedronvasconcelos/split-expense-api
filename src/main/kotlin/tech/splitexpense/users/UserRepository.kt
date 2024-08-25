package tech.splitexpense.users

import java.util.*


interface UserRepository  {
    fun save(user: User): User
    fun findById(id: UUID): User?
    fun findByEmail(email: String): User?
    fun update(user: User): User
}