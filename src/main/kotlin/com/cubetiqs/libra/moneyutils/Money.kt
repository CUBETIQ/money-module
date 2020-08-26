package com.cubetiqs.libra.moneyutils

open class Money(
    var value: Double,
    private var currency: String = "USD"
) : StdMoney {

    //////////////////// - PROPERTIES - ////////////////////

    override fun getMoneyValue(): Double {
        return this.value
    }

    override fun getMoneyCurrency(): String {
        return this.currency.toUpperCase()
    }

    //////////////////// - GENERIC - ////////////////////

    override fun toString(): String {
        return "Money(value=${getMoneyValue()}, currency='${getMoneyCurrency()}')"
    }

    companion object {
        val ZERO: StdMoney
            get() = Money(value = 0.0)
    }
}
