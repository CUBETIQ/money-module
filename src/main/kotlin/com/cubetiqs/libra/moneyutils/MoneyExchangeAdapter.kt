package com.cubetiqs.libra.moneyutils

interface MoneyExchangeAdapter {
    fun getRate(currency: String): Double
}