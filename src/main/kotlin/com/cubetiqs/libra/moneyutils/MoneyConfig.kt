package com.cubetiqs.libra.moneyutils

/**
 * Default money config in static object.
 *
 * @author sombochea
 * @since 1.0
 */
object MoneyConfig {
    private const val SPLIT_ORD_1 = ','
    private const val SPLIT_ORD_2 = '='

    /**
     * Key is the currency
     * Value is the rate
     */
    private val config = mutableMapOf<String, Double>()

    fun parse(config: String): Map<String, Double> {
        val result = mutableMapOf<String, Double>()
        val rates = config.split(SPLIT_ORD_1)
        /**
         * USD=1
         * KHR=4000
         * EUR=0.99
         *
         * USD -> currency
         * 1 -> value
         */
        rates.map { i ->
            val temp = i.split(SPLIT_ORD_2)
            if (temp.size == 2) {
                val currency = temp[0]
                val value = temp[1].toDouble()
                result.put(currency.toUpperCase(), value)
            } else {
                throw IllegalArgumentException("invalid value!")
            }
        }

        // if you want to use all set currencies without remove from memory, we should remove this line
        this.config.clear()
        this.config.putAll(result)
        return result
    }

    // all currencies with its rate
    fun getConfig() = this.config

    fun getRate(currency: String): Double {
        return getConfig()[currency.toUpperCase()] ?: throw IllegalArgumentException("currency not found")
    }
}