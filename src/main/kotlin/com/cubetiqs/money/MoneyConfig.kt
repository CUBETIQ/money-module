package com.cubetiqs.money

/**
 * Default money config in static object.
 * Sample parse format: USD=1,KHR=4000,EUR=0.99
 *
 * @author sombochea
 * @since 1.0
 */
object MoneyConfig {
    /**
     * All money currencies and its rate are stored in memory state for exchange or compute the value.
     *
     * Key is the currency
     * Value is the rate
     */
    private val config: MutableMap<String, Double> = mutableMapOf()

    // validate the config, if have it's valid
    fun isValid(): Boolean {
        return config.isNotEmpty()
    }

    /**
     * Money properties for money config format
     */
    private var properties: MoneyConfigProperties? = null

    private val propertiesBuilder = MoneyConfigProperties.MoneyConfigPropertiesBuilder()

    private fun getProperties(): MoneyConfigProperties {
        return properties ?: propertiesBuilder.build()
    }

    fun setProperties(properties: MoneyConfigProperties): MoneyConfig {
        MoneyConfig.properties = properties
        return MoneyConfig
    }

    /**
     * Parse the config string to currency's map within rates
     * Key is money's currency (String)
     * Value is money's value (Double)
     */
    fun parse(config: String, clearAllStates: Boolean = true) {
        // remove all states, if needed
        if (clearAllStates) {
            MoneyConfig.config.clear()
        }

        val rates = config.split(getProperties().deliSplit)
        rates.map { i ->
            val temp = i.split(getProperties().deliEqual)
            if (temp.size == 2) {
                val currency = temp[0].toUpperCase().trim()
                val value = temp[1].toDouble()
                if (MoneyConfig.config.containsKey(currency)) {
                    MoneyConfig.config.replace(currency, value)
                } else {
                    MoneyConfig.config.put(currency, value)
                }
            } else {
                throw MoneyCurrencyStateException("money config format is not valid!")
            }
        }
    }

    fun appendRate(currency: String, rate: Double) = apply {
        val currencyKey = currency.toUpperCase().trim()
        if (config.containsKey(currencyKey)) {
            config.replace(currencyKey, rate)
        } else {
            config[currencyKey] = rate
        }
    }

    // all currencies with its rate
    fun getConfig() = config

    @Throws(MoneyCurrencyStateException::class)
    fun getRate(currency: StdMoney.Currency): Double {
        return getConfig()[currency.getCurrency().toUpperCase()]
            ?: throw MoneyCurrencyStateException("money currency $currency is not valid!")
    }

    class MoneyConfigProperties(
        val deliEqual: Char,
        val deliSplit: Char,
    ) {
        class MoneyConfigPropertiesBuilder(
            private var deliEqual: Char? = null,
            private var deliSplit: Char? = null,
        ) {
            fun setDeliEqual(deliEqual: Char) = apply { this.deliEqual = deliEqual }

            private fun getDeliEqual(): Char {
                return deliEqual ?: '='
            }

            fun setDeliSplit(deliSplit: Char) = apply { this.deliSplit = deliSplit }

            private fun getDeliSplit(): Char {
                return deliSplit ?: ','
            }

            fun build(): MoneyConfigProperties {
                return MoneyConfigProperties(deliEqual = getDeliEqual(), deliSplit = getDeliSplit())
            }
        }
    }

    fun builder() = MoneyConfigProperties.MoneyConfigPropertiesBuilder()
}