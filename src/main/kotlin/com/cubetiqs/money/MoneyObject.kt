package com.cubetiqs.money

import java.io.Serializable

sealed class MoneyObject : Serializable, StdMoney {
    private var value: Double = 0.0
    private var currency: StdMoney.Currency = MoneyCurrency.USD
    private val moneyStates: MutableMap<MoneyCurrency, MoneyState> = mutableMapOf()

    constructor(value: Double, currency: MoneyCurrency) {
        this.value = value
        this.currency = currency
        isComputed = false
    }

    /**
     * Check if computed, set it to true. Because we no need to add the same value again after once
     * computed.
     */
    @Transient
    var isComputed = false
        private set

    @Transient
    var isInit = false
        private set

    /** Calculate the value. Must be call after complete add money. */
    fun compute(): MoneyObject = apply {

    }

    companion object {
        @JvmStatic
        private fun defaultCurrency(): StdMoney.Currency {
            return MoneyExchangeUtils.getBaseCurrency()
        }
    }

    override fun getMoneyValue(): Double {
        return value
    }

    override fun getMoneyCurrency(): StdMoney.Currency {
        return currency
    }
}