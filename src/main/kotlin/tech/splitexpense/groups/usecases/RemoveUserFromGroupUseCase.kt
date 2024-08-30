package tech.splitexpense.groups.usecases

import GroupId
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import tech.splitexpense.groups.domain.ExpenseGroupRepository
import tech.splitexpense.users.UserId
import tech.splitexpense.users.UserRepository
import java.util.*

@Singleton
class RemoveUserFromGroupUseCase(private val expenseGroupRepository : ExpenseGroupRepository,
                                 private val userRepository: UserRepository) {

    fun execute(request: RemoveUserFromGroupRequest): RemoveUserFromGroupResponse {
        val group = expenseGroupRepository.getById(request.toGroupId())
        requireNotNull(group) { "Group not found" }
        if(group.owner.id != request.toOwnerId()){
            throw IllegalArgumentException("Only the group owner can remove users")
        }
        if (group.owner.id == UserId(request.userId)){
            throw IllegalArgumentException("The group owner cannot be removed")
        }
        val user = userRepository.getById(request.userId)
        requireNotNull(user) { "User not found" }
        if(!group.members.contains(user)){
            throw IllegalArgumentException("User is not a member of the group")
        }
        group.removeMember(user)
        expenseGroupRepository.update(group)
        return RemoveUserFromGroupResponse("User removed from group")
    }
}


@Introspected
@Serdeable
data class RemoveUserFromGroupRequest(
        val ownerId: UUID,
        val userId: UUID,
        val groupId: UUID){

     fun toOwnerId() =  UserId(ownerId)
     fun toGroupId() = GroupId(groupId)

}

@Introspected
@Serdeable
data class RemoveUserFromGroupResponse(
        val message: String
)

