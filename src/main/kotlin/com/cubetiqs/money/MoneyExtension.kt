package com.cubetiqs.money

fun StdMoney.exchangeTo(currency: StdMoney.Currency): StdMoney {
    return MoneyExchangeUtils.exchange(this, currency)
}

fun StdMoney.isMatchedCurrency(currency: StdMoney.Currency) = this.getMoneyCurrency().getCurrency().equals(currency.getCurrency(), ignoreCase = true)