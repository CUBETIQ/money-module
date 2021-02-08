package com.cubetiqs.money

open class Money(
    private var value: Double = 0.0,
    private val currency: StdMoney.Currency = MoneyCurrency.USD,
) : StdMoney, StdMoney.Operator<Money>, StdMoney.ExchangeOperator {
    //////////////////// - PROPERTIES - ////////////////////

    override fun getMoneyValue(): Double {
        return this.value
    }

    // not imply with exchange rate yet
    override fun StdMoney.getExchangedTo(currency: StdMoney.Currency): Double {
        return getMoneyValue()
    }

    override fun getMoneyCurrency(): StdMoney.Currency {
        return this.currency
    }

    //////////////////// - GENERIC - ////////////////////

    override fun toString(): String {
        return "Money(value=${getMoneyValue()}, currency='${getMoneyCurrency()}')"
    }

    override fun inc(): Money = apply {
        this.value += 1
    }

    override fun dec(): Money = apply {
        this.value -= 1
    }

    override fun plus(other: StdMoney): Money {
        val temp = this.value + other.getExchangedTo(this.currency)
        return Money(value = temp, currency = this.currency)
    }

    override fun divide(other: StdMoney): Money {
        val temp = this.value / other.getExchangedTo(this.currency)
        return Money(value = temp, currency = this.currency)
    }

    override fun multiply(other: StdMoney): Money {
        val temp = this.value * other.getExchangedTo(this.getMoneyCurrency())
        return Money(value = temp, currency = this.currency)
    }

    override fun plusAssign(other: StdMoney) {
        this.value = this.value + other.getExchangedTo(this.currency)
    }

    override fun divideAssign(other: StdMoney) {
        this.value = this.value / other.getExchangedTo(this.currency)
    }

    override fun multiplyAssign(other: StdMoney) {
        this.value = this.value * other.getExchangedTo(this.getMoneyCurrency())
    }

    companion object {
        val ZERO: StdMoney
            get() = Money()

        val ONE: StdMoney
            get() = Money(value = 1.0)

        val TEN: StdMoney
            get() = Money(value = 10.0)

        /**
         * Create a new money object with custom value
         *
         * @param value Double
         * @param currency MoneyCurrency
         */
        fun create(value: Double, currency: StdMoney.Currency): Money {
            return Money(value = value, currency = currency)
        }

        fun from(value: Double, currency: String): Money {
            return create(value, object : StdMoney.Currency {
                override fun getCurrency(): String {
                    return currency.toUpperCase().trim()
                }
            })
        }

        fun from(money: StdMoney): Money {
            return create(value = money.getMoneyValue(), currency = money.getMoneyCurrency())
        }
    }
}
