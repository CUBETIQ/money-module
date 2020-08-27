package com.cubetiqs.libra.moneyutils

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
    @SpecialString
    fun getMoneyCurrency(): String
}