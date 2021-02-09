package com.cubetiqs.money

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

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
    private val config: ConcurrentMap<String, Double> = ConcurrentHashMap()

    // use to format the money for each value, if have
    private val configFormatter: ConcurrentMap<String, MoneyFormatProvider> = ConcurrentHashMap()

    // use to identified for config dataset with prefix mode
    private var configPrefix: String = ""
    // use to fallback, if the currency not found
    // if the fallback greater than ZERO, then called it
    // else throws
    private var fallbackRate: Double = 0.0

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

    fun setConfigPrefix(prefix: String): MoneyConfig {
        configPrefix = prefix
        return MoneyConfig
    }

    fun setFallbackRate(fallbackRate: Double) = apply {
        this.fallbackRate = fallbackRate
    }

    // get custom config key within currency generally
    // example: myOwned_usd
    private fun getConfigKey(key: String): String {
        if (configPrefix.isEmpty() || configPrefix.isBlank()) {
            return key
        }
        return "${configPrefix}_$key"
    }

    /**
     * Parse the config string to currency's map within rates
     * Key is money's currency (String)
     * Value is money's value (Double)
     *
     * Example for config rules: usd:1,khr:4000
     */
    fun parse(config: String, clearAllStates: Boolean = true) {
        // remove all states, if needed
        if (clearAllStates) {
            if (configPrefix.isEmpty() || config.isBlank()) {
                MoneyConfig.config.clear()
            } else {
                val keys = MoneyConfig.config.filter { it.key.startsWith(prefix = configPrefix) }.keys
                keys.forEach { key ->
                    MoneyConfig.config.remove(key)
                }
            }
        }

        val rates = config.split(getProperties().deliSplit)
        rates.map { i ->
            val temp = i
                // remove the quote from string
                .replace("\"", "")
                .split(getProperties().deliEqual)
            if (temp.size == 2) {
                val currency = temp[0]
                    .toUpperCase()
                    .trim()
                val key = getConfigKey(currency)
                val value = temp[1].toDouble()

                // set the value into dataset
                if (MoneyConfig.config.containsKey(key)) {
                    MoneyConfig.config.replace(key, value)
                } else {
                    MoneyConfig.config.put(key, value)
                }
            } else {
                throw MoneyCurrencyStateException("money config format $temp is not valid!")
            }
        }
    }

    // append the rate into dataset
    // for config key are completed change inside
    fun appendRate(currency: String, rate: Double) = apply {
        val currencyKey = currency.toUpperCase().trim()
        val key = getConfigKey(currencyKey)
        if (config.containsKey(key)) {
            config.replace(key, rate)
        } else {
            config[key] = rate
        }
    }

    // append the rate via provider
    // no need to change currency prefix
    fun appendRate(provider: MoneyExchangeProvider) = apply {
        val currency = provider.getCurrency()
        val rate = provider.getRate()
        this.appendRate(currency, rate)
    }

    /**
     * Json Format must be, example below
     *
     * {
     *  "USD": 1,
     *  "EUR": 0.99,
     *  "...": ...
     * }
     */
    fun fromJsonRates(configJson: String, clearAllStates: Boolean = false) {
        val transformValues = configJson
            .removePrefix("{")
            .removeSuffix("}")

        parse(transformValues, clearAllStates)
    }

    // all currencies with its rate
    fun getConfig() = config

    @Throws(MoneyCurrencyStateException::class)
    fun getRate(currency: StdMoney.Currency): Double {
        return getConfig()[getConfigKey(currency.getCurrency().toUpperCase().trim())]
            ?: if (fallbackRate > 0) fallbackRate else throw MoneyCurrencyStateException("money currency ${currency.getCurrency()} is not valid!")
    }

    // apply default formatter for all not exists
    fun applyDefaultFormatter(
        provider: MoneyFormatProvider? = null
    ) = apply {
        configFormatter[MoneyFormatter.DEFAULT_FORMATTER] = buildMoneyFormatter {
            setProvider(provider)
        }
    }

    // add money formatter by currency of each money value
    fun addFormatter(currency: String, formatter: MoneyFormatProvider) = apply {
        val key = getConfigKey(currency.toUpperCase().trim())
        if (configFormatter.containsKey(key)) {
            configFormatter.replace(key, formatter)
        } else {
            configFormatter[key] = formatter
        }
    }

    // get formatter by currency or default
    fun getFormatter(currency: String? = null): MoneyFormatter {
        // apply default formatter
        val formatter = (if (!currency.isNullOrEmpty()) {
            val key = getConfigKey(currency.toUpperCase().trim())
            configFormatter[key]
        } else {
            null
        }) ?: configFormatter[MoneyFormatter.DEFAULT_FORMATTER]

        return when (formatter) {
            is MoneyFormatter -> formatter
            else -> buildMoneyFormatter { setProvider(provider = formatter) }
        }
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