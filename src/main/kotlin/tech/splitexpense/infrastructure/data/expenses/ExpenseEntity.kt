package tech.splitexpense.infrastructure.data.expenses

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.data.model.naming.NamingStrategies
import io.micronaut.serde.annotation.Serdeable
import tech.splitexpense.infrastructure.data.groups.ExpenseGroupEntity
import tech.splitexpense.infrastructure.data.users.UserEntity
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Serdeable
@MappedEntity("expenses", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase::class)
data class ExpenseEntity(
        @field:Id
        @field:MappedProperty("id")
        var id: UUID = UUID.randomUUID(),

        @field:MappedProperty("name")
        var name: String,

        @field:MappedProperty("amount")
        var amount: BigDecimal,

        @Relation(value = Relation.Kind.MANY_TO_ONE)
        @field:MappedProperty("group_id")
        var group: ExpenseGroupEntity,

        @Relation(value = Relation.Kind.MANY_TO_MANY, cascade = [Relation.Cascade.PERSIST])
        var participants: Set<UserEntity> = emptySet(),

        @field:MappedProperty("created_at")
        var createdAt: Instant = Instant.now(),

        @field:MappedProperty("updated_at")
        var updatedAt: Instant? = null,

        @field:MappedProperty("deleted_at")
        var deletedAt: Instant? = null
)

@MappedEntity("expense_participants")
data class ExpenseParticipant(
        @Id
        @MappedProperty("expense_id")
        val expenseId: UUID,

        @Id
        @MappedProperty("user_id")
        val userId: UUID
)