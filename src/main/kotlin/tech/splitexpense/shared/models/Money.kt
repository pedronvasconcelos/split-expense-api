package tech.splitexpense.shared.models

import java.math.BigDecimal

@JvmInline
value class Money(val amount: BigDecimal) {
    init {
        require(amount >= BigDecimal.ZERO) { "Amount cannot be negative" }
    }
    operator fun plus(other: Money) = Money(this.amount + other.amount)

    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }
}