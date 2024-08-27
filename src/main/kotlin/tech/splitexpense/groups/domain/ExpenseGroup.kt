import tech.splitexpense.shared.models.Money
import tech.splitexpense.groups.domain.Expense
import tech.splitexpense.users.User
import java.time.Instant
import java.util.UUID

data class ExpenseGroup constructor(
        val id: GroupId,
        val name: String,
        val members: Set<User>,
        val expenses: Set<Expense>,
        val createdAt: Instant,
        val updatedAt: Instant?,
        val owner: User,
        val deletedAt: Instant?,
        val finishedAt: Instant?,
        var category: GroupCategory
) {
    init {
        if(!members.contains(owner)){
            members.plus(owner)
        }
        require(name.isNotBlank()) { "Group name cannot be blank" }
        require(members.isNotEmpty()) { "Group must have at least one member" }
        require(owner in members) { "Owner must be a member of the group" }
        require(expenses.all { it.participants.all { participant -> participant in members } }) {
            "All expense participants must be group members"
        }
    }

    val isActive: Boolean
        get() = deletedAt == null && finishedAt == null

    val totalExpenses: Money
        get() = expenses.fold(Money.ZERO) { acc, expense -> acc + expense.amount }

    fun addMember(user: User): ExpenseGroup {
        require(isActive) { "Cannot add member to inactive group" }
        if(user in members) {
            return this
        }
        return copy(
                members = members + user,
                updatedAt = Instant.now()
        )
    }

    fun removeMember(user: User): ExpenseGroup {
        require(isActive) { "Cannot remove member from inactive group" }
        require(user != owner) { "Cannot remove the owner from the group" }
        require(user in members) { "User is not a member of this group" }
        return copy(
                members = members - user,
                updatedAt = Instant.now()
        )
    }

    fun addExpense(expense: Expense): ExpenseGroup {
        require(isActive) { "Cannot add expense to inactive group" }
        require(expense.participants.all { it in members }) { "All expense participants must be group members" }
        return copy(
                expenses = expenses + expense,
                updatedAt = Instant.now()
        )
    }

    fun markAsFinished(): ExpenseGroup {
        require(isActive) { "Cannot finish an inactive group" }
        return copy(
                finishedAt = Instant.now(),
                updatedAt = Instant.now()
        )
    }

    fun delete(): ExpenseGroup {
        require(isActive) { "Cannot delete an inactive group" }
        return copy(
                deletedAt = Instant.now(),
                updatedAt = Instant.now()
        )
    }
    fun updateCategory(category: GroupCategory): ExpenseGroup {
        return copy(
                category = category,
                updatedAt = Instant.now()
        )
    }

    companion object {
        fun create(id: GroupId, name: String, owner: User, category : GroupCategory): ExpenseGroup {
            return ExpenseGroup(
                    id = id,
                    name = name,
                    members = setOf(owner),
                    expenses = emptySet(),
                    createdAt = Instant.now(),
                    updatedAt = null,
                    owner = owner,
                    deletedAt = null,
                    finishedAt = null,
                    category = category
            )
        }
    }
}

@JvmInline
value class GroupId(val value: UUID){
    override fun toString(): String = value.toString()


    fun toUUID(): UUID {
        return value
    }
}

enum class GroupCategory {
    FRIENDS,
    FAMILY,
    WORK,
    TRAVEL,
    OTHER,
    ROOMMATES,
    SPORTS,
    STUDIES,
    BUSINESS,
    CHARITY
}



