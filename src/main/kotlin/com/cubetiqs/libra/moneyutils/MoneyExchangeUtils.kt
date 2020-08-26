package com.cubetiqs.libra.moneyutils

object MoneyExchangeUtils {
    fun computeRate(rateFrom: Double, rateTo: Double, baseRate: Double = 1.0, amountFrom: Double = 1.0): Double {
        return amountFrom * ((baseRate / rateFrom) / (baseRate / rateTo))
    }
}