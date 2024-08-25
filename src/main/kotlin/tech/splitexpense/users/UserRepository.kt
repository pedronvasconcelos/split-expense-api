package tech.splitexpense.users

import java.util.*


interface UserRepository  {
    fun save(user: User): User
    fun getById(id: UUID): User?
    fun getByEmail(email: String): User?
    fun update(user: User): User
}