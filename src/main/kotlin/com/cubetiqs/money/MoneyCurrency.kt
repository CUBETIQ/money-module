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
    private var configs: Map<String, Any?>? = null,
) : Serializable, StdMoney.Currency {
    override fun getCurrency(): String {
        return name.uppercase().trim()
    }

    fun getSymbol(): String? {
        return symbol ?: getConfigs()["symbol"]?.toString()
    }

    fun addConfig(key: String, value: Any?) = apply {
        if (this.configs.isNullOrEmpty()) {
            this.configs = mutableMapOf(key to value)
        } else {
            (this.configs as MutableMap)[key] = value
        }
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
            get() = StdMoney.USD

        val KHR
            get() = StdMoney.KHR
    }
}