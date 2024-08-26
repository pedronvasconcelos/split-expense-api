package tech.splitexpense.groups.usecases

import GroupCategory
import GroupId
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.ExpenseGroupRepository
import tech.splitexpense.users.UserRepository
import java.util.UUID

@Singleton
class CreateNewGroupUseCase(
        private val groupRepository: ExpenseGroupRepository,
        private val userRepository: UserRepository
) {
    fun execute(request: CreateGroupRequest): CreateGroupResponse {
        require(request.name.isNotBlank()) { "Group name cannot be blank" }

        val owner = userRepository.getById(request.ownerId)
                ?: throw IllegalArgumentException("Owner with ID ${request.ownerId} not found")

        val newExpenseGroup = ExpenseGroup.create(
                id = GroupId(UUID.randomUUID()),
                name = request.name,
                owner = owner,
                category = request.category
        )

        val savedGroup = groupRepository.save(newExpenseGroup)

        return CreateGroupResponse(
                id = savedGroup.id.value,
                name = savedGroup.name,
                ownerId = savedGroup.owner.id.value
        )
    }
}
@Introspected
@Serdeable
data class CreateGroupRequest(
        val name: String,
        val ownerId: UUID,
        val category : GroupCategory
)
@Introspected
@Serdeable
data class CreateGroupResponse(
        val id: UUID,
        val name: String,
        val ownerId: UUID
)