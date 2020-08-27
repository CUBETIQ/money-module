package com.cubetiqs.libra.moneyutils

open class Money(
    var value: Double,
    @SpecialString(trim = true, upperCase = true) private var currency: String = "USD"
) : StdMoney {

    //////////////////// - PROPERTIES - ////////////////////

    override fun getMoneyValue(): Double {
        return this.value
    }

    override fun getMoneyCurrency(): String {
        return this.currency
    }

    //////////////////// - GENERIC - ////////////////////

    override fun toString(): String {
        return "Money(value=${getMoneyValue()}, currency='${getMoneyCurrency()}')"
    }

    companion object {
        val ZERO: StdMoney
            get() = Money(value = 0.0)
        val ONE: StdMoney
            get() = Money(value = 1.0)
        val TEN: StdMoney
            get() = Money(value = 10.0)

        /**
         * Create a new money object with custom value
         *
         * @param value Double
         * @param currency String
         */
        fun create(value: Double, currency: String = MoneyCurrency.USD.name): StdMoney {
            return Money(value = value, currency = currency)
        }

        /**
         * Create a new money object with custom value
         *
         * @param value Double
         * @param currency MoneyCurrency
         */
        fun create(value: Double, currency: MoneyCurrency = MoneyCurrency.USD): StdMoney = create(value, currency.name)
    }
}
