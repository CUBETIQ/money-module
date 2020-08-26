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
     * All money currencies and its rate are stored in memory state for exchange or compute the value.
     *
     * Key is the currency
     * Value is the rate
     */
    private val config: MutableMap<String, Double> = mutableMapOf()

    /**
     * Parse the config string to currency's map within rates
     * Key is money's currency (String)
     * Value is money's value (Double)
     */
    fun parse(config: String, clearAllStates: Boolean = true) {
        if (clearAllStates) {
            this.config.clear()
        }
        val rates = config.split(SPLIT_ORD_1)
        rates.map { i ->
            val temp = i.split(SPLIT_ORD_2)
            if (temp.size == 2) {
                val currency = temp[0].toUpperCase()
                val value = temp[1].toDouble()
                if (this.config.containsKey(currency)) {
                    this.config.replace(currency, value)
                } else {
                    this.config.put(currency, value)
                }
            } else {
                throw MoneyCurrencyStateException("money config format is not valid!")
            }
        }
    }

    // all currencies with its rate
    fun getConfig() = this.config

    @Throws(MoneyCurrencyStateException::class)
    fun getRate(currency: String): Double {
        return getConfig()[currency.toUpperCase()]
            ?: throw MoneyCurrencyStateException("money currency $currency is not valid!")
    }
}