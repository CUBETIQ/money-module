package com.cubetiqs.libra.moneyutils

operator fun StdMoney.unaryMinus() = (-getMoneyValue())
operator fun StdMoney.unaryPlus() = (+getMoneyValue())
operator fun Money.inc() = Money(value++)
operator fun Money.dec() = Money(value--)
operator fun StdMoney.plus(other: StdMoney) = Money(getMoneyValue() + other.getMoneyValue())
operator fun StdMoney.times(other: StdMoney) = Money(getMoneyValue() * other.getMoneyValue())
operator fun StdMoney.div(other: StdMoney) = Money(getMoneyValue() / other.getMoneyValue())
operator fun Money.timesAssign(other: StdMoney) {
    this.value = this.getMoneyValue() * other.getMoneyValue()
}