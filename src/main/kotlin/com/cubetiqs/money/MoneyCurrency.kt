package com.cubetiqs.money

import java.io.Serializable

/**
 * Money Currency Object with flexible currency based on data and configs
 *
 * @author sombochea
 * @since 1.0
 */
open class MoneyCurrency(
    private val name: String,
    private val symbol: String? = null,
    private val configs: Map<String, Any?>? = null,
) : Serializable, StdMoney.Currency {
    override fun getCurrency(): String {
        return name.toUpperCase().trim()
    }

    fun getSymbol(): String? {
        return symbol ?: getConfigs()["symbol"]?.toString()
    }

    fun getConfigs(): Map<String, Any?> {
        return configs ?: emptyMap()
    }

    override fun toString(): String {
        return "MoneyCurrency(name='$name', symbol=$symbol, configs=$configs)"
    }

    companion object {
        fun create(name: String): MoneyCurrency {
            return MoneyCurrency(name = name)
        }

        val USD
            get() = create("USD")

        val KHR
            get() = create("KHR")
    }
}