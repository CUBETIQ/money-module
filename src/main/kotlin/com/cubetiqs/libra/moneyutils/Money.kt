package com.cubetiqs.libra.moneyutils

open class Money(
    private var value: Double,
    private var currency: String = "USD"
) : StdMoney {
    override fun getValue(): Double {
        return this.value
    }

    fun setValue(value: Double) {
        this.value = value
    }

    override fun getCurrency(): String {
        return this.currency.toUpperCase()
    }

    fun setCurrency(currency: String) {
        this.currency = currency
    }

    override fun toString(): String {
        return "Money(value=${getValue()}, currency='${getCurrency()}')"
    }
}
