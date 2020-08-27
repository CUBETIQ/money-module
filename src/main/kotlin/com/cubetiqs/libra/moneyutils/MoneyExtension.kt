package com.cubetiqs.libra.moneyutils

fun StdMoney.exchangeTo(currency: String): StdMoney {
    return MoneyExchangeUtils.exchange(this, currency)
}

fun StdMoney.isMatchedCurrency(currency: String) = this.getMoneyCurrency().equals(currency, ignoreCase = true)