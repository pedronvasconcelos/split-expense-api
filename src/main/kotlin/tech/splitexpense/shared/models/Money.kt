package tech.splitexpense.shared.models

import java.math.BigDecimal

@JvmInline
value class Money(val value: BigDecimal) {
    init {
        require(value >= BigDecimal.ZERO) { "Amount cannot be negative" }
    }
    operator fun plus(other: Money) = Money(this.value + other.value)

    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }
}