package com.cubetiqs.money

open class MoneyView(
    private var value: Number? = null,
    private var currency: String? = null,
) : MoneyMixin {
    private var _currency: StdMoney.Currency? = null

    constructor(money: StdMoney) : this() {
        this._currency = money.getCurrency()
        this.value = money.getValue()
        this.currency = this._currency?.getCurrency()
    }

    fun getValue(): Double {
        return (value?.toDouble() ?: 0.0)
    }

    fun getCurrency(): String? {
        return currency
    }

    fun getSymbol(): String? {
        return this._currency?.findCurrency()?.symbol
    }

    fun getFormat(): String {
        return MoneyConfig
            .getFormatter(getCurrency())
            .setValue(this.asStdMoney())
            .format()
    }
}