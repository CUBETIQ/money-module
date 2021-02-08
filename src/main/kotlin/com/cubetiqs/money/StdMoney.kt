package com.cubetiqs.money

/**
 * Default standard money style
 * We have only value and currency of money
 *
 * @author sombochea
 * @since 1.0
 */
interface StdMoney {
    /**
     * Get money's value from current state.
     *
     * @return Double
     */
    fun getValue(): Double

    /**
     * Get money's currency from current state.
     *
     * @return String
     */
    fun getCurrency(): Currency

    /**
     * Allow for money currency called and implemented
     */
    interface Currency {
        fun getCurrency(): String
    }

    interface ExchangeOperator {
        fun StdMoney.getExchangedTo(currency: Currency): Double
    }

    interface Operator<T : StdMoney> {
        fun plus(other: StdMoney): T
        fun minus(other: StdMoney): T
        fun divide(other: StdMoney): T

        fun inc(): T
        fun dec(): T
        fun multiply(other: StdMoney): T

        // assign operators
        fun plusAssign(other: StdMoney): T
        fun minusAssign(other: StdMoney): T
        fun divideAssign(other: StdMoney): T
        fun multiplyAssign(other: StdMoney): T

        // none-of-return
        fun nor() {}
    }

    companion object {
        fun initCurrency(currency: String?): Currency {
            return object : Currency {
                override fun getCurrency(): String {
                    return currency?.toUpperCase()?.trim() ?: "USD"
                }
            }
        }

        val USD
            get() = initCurrency("USD")
        val KHR
            get() = initCurrency("KHR")

        fun initMoney(initValue: Double, currency: Currency = USD): StdMoney {
            return object : StdMoney {
                override fun getCurrency(): Currency {
                    return currency
                }

                override fun getValue(): Double {
                    return initValue
                }
            }
        }

        val ZERO
            get() = initMoney(0.0, USD)
    }
}