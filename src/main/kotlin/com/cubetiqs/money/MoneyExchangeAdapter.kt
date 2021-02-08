package com.cubetiqs.money

interface MoneyExchangeAdapter {
    fun getRate(currency: String): Double
}