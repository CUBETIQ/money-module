package com.cubetiqs.libra.moneyutils

data class Money(
    var value: Double,
    var currency: String = "USD"
)

operator fun Money.unaryMinus() = (-value)
operator fun Money.unaryPlus() = (+value)
operator fun Money.inc() = Money(value++)
operator fun Money.dec() = Money(value--)
operator fun Money.plus(other: Money) = Money(value + other.value)
operator fun Money.times(other: Money) = Money(value * other.value)
operator fun Money.div(other: Money) = Money(value / other.value)
operator fun Money.timesAssign(other: Money) {
    this.value = this.value * other.value
}
operator fun Money.not() = this.value != this.value