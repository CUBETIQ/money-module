package com.cubetiqs.money

object MoneyExchangeUtils {
    fun exchange(exchangeFrom: StdMoney, exchangeToCurrency: StdMoney.Currency): StdMoney {
        val rateFrom = MoneyConfig.getRate(exchangeFrom.getMoneyCurrency())
        val rateTo = MoneyConfig.getRate(exchangeToCurrency)
        return Money(value = computeRate(rateFrom, rateTo, amountFrom = exchangeFrom.getMoneyValue()), currency = exchangeToCurrency)
    }

    private fun computeRate(rateFrom: Double, rateTo: Double, baseRate: Double = 1.0, amountFrom: Double = 1.0): Double {
        return amountFrom * ((baseRate / rateFrom) / (baseRate / rateTo))
    }

    fun getBaseCurrency(): StdMoney.Currency {
        return StdMoney.USD
    }
}