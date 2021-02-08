package com.cubetiqs.money

interface MoneyExchangeProvider {
    fun getRate(currency: StdMoney.Currency): Double
}