package com.cubetiqs.libra.moneyutils

/**
 * Default standard money style
 * We have only value and currency of money
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
     */
    fun getCurrency(): String
}