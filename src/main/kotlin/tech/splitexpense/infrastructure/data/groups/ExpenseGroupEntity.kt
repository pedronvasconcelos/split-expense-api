package tech.splitexpense.infrastructure.data.groups

import GroupCategory
import io.micronaut.data.annotation.*
import io.micronaut.data.annotation.event.PrePersist
import io.micronaut.data.annotation.event.PreUpdate
import io.micronaut.data.annotation.sql.JoinColumn
import io.micronaut.data.annotation.sql.JoinTable
import io.micronaut.data.model.naming.NamingStrategies
import io.micronaut.serde.annotation.Serdeable
import tech.splitexpense.infrastructure.data.expenses.ExpenseEntity
import tech.splitexpense.infrastructure.data.users.UserEntity
import java.time.Instant
import java.util.*



@Serdeable
@MappedEntity("groups", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase::class)
data class ExpenseGroupEntity(
        @field:Id
        @field:MappedProperty("id")
        var id: UUID = UUID.randomUUID(),

        @field:MappedProperty("name")
        var name: String,

        @MappedProperty("members")
        @Relation(
                value = Relation.Kind.MANY_TO_MANY,
                cascade = [Relation.Cascade.PERSIST, Relation.Cascade.UPDATE]
        )
        @JoinTable(
                name = "group_members",
                joinColumns = [JoinColumn(name = "group_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        var members: Set<UserEntity> = emptySet(),

        @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "group")
        var expenses: Set<ExpenseEntity> = emptySet(),

        @field:MappedProperty("created_at")
        var createdAt: Instant = Instant.now(),

        @field:MappedProperty("updated_at")
        var updatedAt: Instant? = null,

        @Relation(value = Relation.Kind.MANY_TO_ONE)
        @field:MappedProperty("owner_id")
        var owner: UserEntity,

        @field:MappedProperty("deleted_at")
        var deletedAt: Instant? = null,

        @field:MappedProperty("finished_at")
        var finishedAt: Instant? = null,

        @field:MappedProperty("category")
        var category: GroupCategory = GroupCategory.OTHER
) {
        @PrePersist
        @PreUpdate
        fun convertCategoryToString() {
                category = GroupCategory.valueOf(category.name)
        }
}

@MappedEntity("expense_group_entity_user_entity")
data class ExpenseGroupUserEntity(
        @Id
        @MappedProperty("expense_group_entity_id")
        val expenseGroupId: UUID,

        @Id
        @MappedProperty("user_entity_id")
        val userId: UUID
)