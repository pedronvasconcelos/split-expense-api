package tech.splitexpense.users

import tech.splitexpense.shared.EmailAddress
import java.time.LocalDate

class CompleteRegisterUseCase {
    private lateinit var userRepository: UserRepository

    fun execute(request: CompleteRegisterRequest): CompleteRegisterResponse {
        val user = userRepository.findByEmail(request.email)
        if (user != null) {
            throw IllegalArgumentException("User already exists")
        }
        val newUser = User(
                firstName = request.firstName,
                lastName = request.lastName,
                email = EmailAddress(request.email),
                birthDate = request.birthDate
        )
         userRepository.save(newUser)
        return CompleteRegisterResponse(newUser.id.toString())
    }

}

data class CompleteRegisterRequest(
        val firstName: String,
        val lastName : String,
        val email: String,
        val birthDate: LocalDate,
)

data class CompleteRegisterResponse(
        val id: String,

)