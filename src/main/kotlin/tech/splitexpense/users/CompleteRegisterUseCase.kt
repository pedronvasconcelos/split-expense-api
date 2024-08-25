package tech.splitexpense.application.usecases

import jakarta.inject.Singleton
import tech.splitexpense.shared.EmailAddress
import tech.splitexpense.users.User
import tech.splitexpense.users.UserRepository
import java.time.LocalDate
import java.util.UUID

@Singleton
class CompleteRegisterUseCase(private val userRepository: UserRepository) {

    fun execute(request: CompleteRegisterRequest): CompleteRegisterResponse {
        validateRequest(request)

        userRepository.findByEmail(request.email)?.let {
            throw UserAlreadyExistsException("User with email ${request.email} already exists")
        }

        val newUser = User(
                id = UUID.randomUUID(),
                firstName = request.firstName,
                lastName = request.lastName,
                email = EmailAddress(request.email),
                birthDate = request.birthDate,
        )

        val savedUser = userRepository.save(newUser)

        return CompleteRegisterResponse(savedUser.id.toString())
    }

    private fun validateRequest(request: CompleteRegisterRequest) {
        require(request.firstName.isNotBlank()) { "First name cannot be blank" }
        require(request.lastName.isNotBlank()) { "Last name cannot be blank" }
        require(request.email.isNotBlank()) { "Email cannot be blank" }
        require(request.birthDate != null) { "Birth date cannot be null" }
        require(request.birthDate.isBefore(LocalDate.now())) { "Birth date must be in the past" }
    }
}

class UserAlreadyExistsException(message: String) : RuntimeException(message)

// DTOs
data class CompleteRegisterRequest(
        val firstName: String,
        val lastName: String,
        val email: String,
        val birthDate: LocalDate
)

data class CompleteRegisterResponse(val userId: String)