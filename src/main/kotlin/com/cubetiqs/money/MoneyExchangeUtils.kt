package com.cubetiqs.money

object MoneyExchangeUtils {
    fun exchange(exchangeFrom: StdMoney, exchangeToCurrency: StdMoney.Currency): StdMoney {
        val rateFrom = MoneyConfig.getRate(exchangeFrom.getCurrency())
        val rateTo = MoneyConfig.getRate(exchangeToCurrency)
        return Money(value = computeRate(rateFrom, rateTo, amountFrom = exchangeFrom.getValue()), currency = exchangeToCurrency)
    }

    private fun computeRate(rateFrom: Double, rateTo: Double, baseRate: Double = 1.0, amountFrom: Double = 1.0): Double {
        return amountFrom * ((baseRate / rateFrom) / (baseRate / rateTo))
    }

    fun getBaseCurrency(): StdMoney.Currency {
        return StdMoney.USD
    }

    /**
     * Compute the Base Exchange Price / Converter to any Rates.
     * Multiple differentiate exchange relation computation.
     * Example: A -> B -> C meant You can with all variables, but must find the based MEANT of its.
     *
     * Sample Explanation: Matrix Multiply
     * 1 USD -> 0.90 EUR
     * 1 USD -> 4000 KHR
     * If I want to exchange from EUR to KHR, I need.
     * 1 EUR -> 1 * (1 / 0.90) / (1 / 4000)
     *
     * @author sombochea
     * @since 1.0
     */
    fun computeFromBaseRate(amountFrom: Double = 1.0, baseRate: Double = 1.0, rateFrom: Double, rateTo: Double): Double {
        // amount * ((baseRate / rateFrom) / (baseRate / rateTo))
        return amountFrom.times((baseRate.div(rateFrom)).div(baseRate.div(rateTo)))
    }
}