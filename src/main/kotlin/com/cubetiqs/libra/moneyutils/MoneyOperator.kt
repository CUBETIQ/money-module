package com.cubetiqs.libra.moneyutils

operator fun Money.unaryMinus() = (-getMoneyValue())
operator fun Money.unaryPlus() = (+getMoneyValue())
operator fun Money.inc() = Money(value++)
operator fun Money.dec() = Money(value--)
operator fun Money.plus(other: Money) = Money(value + other.value)
operator fun Money.times(other: Money) = Money(value * other.value)
operator fun Money.div(other: Money) = Money(value / other.value)
operator fun Money.timesAssign(other: Money) {
    this.value = this.value * other.value
}
operator fun Money.not() = this.value != this.value