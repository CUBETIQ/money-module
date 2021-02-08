package com.cubetiqs.money

// unary operators
operator fun StdMoney.unaryMinus() = (-getMoneyValue())
operator fun StdMoney.unaryPlus() = (+getMoneyValue())

// operators
operator fun StdMoney.inc() = Money.from(this).inc()
operator fun StdMoney.dec() = Money.from(this).dec()
operator fun StdMoney.plus(other: StdMoney): StdMoney {
    val result = Money.from(this)
    result.plusAssign(other)
    return result
}
operator fun StdMoney.minus(other: StdMoney): StdMoney {
    val result = Money.from(this)
    result.minusAssign(other)
    return result
}
operator fun StdMoney.times(other: StdMoney) = Money.from(this).multiplyAssign(other)
operator fun StdMoney.div(other: StdMoney) = Money.from(this).divideAssign(other)

// assign operators
operator fun Money.timesAssign(other: StdMoney) = this.multiplyAssign(other).nor()
operator fun Money.plusAssign(other: StdMoney) = this.plusAssign(other).nor()
operator fun Money.minusAssign(other: StdMoney) = this.minusAssign(other).nor()
operator fun Money.divAssign(other: StdMoney) = this.divideAssign(other).nor()