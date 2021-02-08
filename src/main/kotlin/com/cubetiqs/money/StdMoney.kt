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
    fun getMoneyValue(): Double

    /**
     * Get money's currency from current state.
     *
     * @return String
     */
    fun getMoneyCurrency(): Currency

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
        fun divide(other: StdMoney): T

        fun inc(): T
        fun dec(): T
        fun multiply(other: StdMoney): T

        // assign operators
        fun plusAssign(other: StdMoney)
        fun divideAssign(other: StdMoney)
        fun multiplyAssign(other: StdMoney)
    }
}