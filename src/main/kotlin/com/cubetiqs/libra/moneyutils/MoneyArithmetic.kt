package com.cubetiqs.libra.moneyutils

fun StdMoney.addMoney(value: Double, currency: String): StdMoney {
    return this + Money.create(value, currency)
}