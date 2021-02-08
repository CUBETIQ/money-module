package com.cubetiqs.money

interface MoneyExchangeProvider {
    fun getCurrency(): String
    fun getRate(): Double
}