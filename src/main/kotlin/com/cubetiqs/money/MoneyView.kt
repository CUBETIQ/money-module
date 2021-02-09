package com.cubetiqs.money

open class MoneyView(
    private var value: Number? = null,
    private var currency: String? = null,
) : MoneyMixin {
    constructor(money: StdMoney) : this() {
        this.value = money.getValue()
        this.currency = money.getCurrency().getCurrency()
    }

    fun getValue(): Double {
        return value?.toDouble() ?: 0.0
    }

    fun getCurrency(): String? {
        return currency
    }

    fun getFormat(): String {
        return MoneyConfig
            .getFormatter(getCurrency())
            .setValue(this.asStdMoney())
            .format()
    }
}