package com.cubetiqs.money

fun StdMoney.addMoney(value: Double, currency: String): StdMoney {
    return this + Money.create(value, currency)
}