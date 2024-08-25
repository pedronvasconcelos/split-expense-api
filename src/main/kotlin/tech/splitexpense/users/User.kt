package tech.splitexpense.users

import tech.splitexpense.shared.EmailAddress
import java.time.Instant
import java.time.LocalDate
import java.util.*

class User (var id: UUID  = UUID.randomUUID(),
            var firstName: String,
            var lastName: String,
            var email:  EmailAddress,
            var status: UserStatus = UserStatus.ACTIVE ,
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






