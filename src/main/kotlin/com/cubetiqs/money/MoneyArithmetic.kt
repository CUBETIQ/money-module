package com.cubetiqs.money

fun StdMoney.addMoney(value: Double, currency: StdMoney.Currency): StdMoney {
    this + Money.create(value, currency)
    return this
}