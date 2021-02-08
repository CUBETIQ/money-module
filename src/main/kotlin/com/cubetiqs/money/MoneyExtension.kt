package com.cubetiqs.money

infix fun StdMoney.exchangeTo(currency: StdMoney.Currency): StdMoney {
    return MoneyExchangeUtils.exchange(this, currency)
}

infix fun StdMoney.exchangeTo(currency: String): StdMoney = this exchangeTo object : StdMoney.Currency {
    override fun getCurrency(): String {
        return currency.toUpperCase().trim()
    }
}

infix fun StdMoney.plusWith(other: StdMoney): StdMoney = this + other
infix fun StdMoney.minusWith(other: StdMoney): StdMoney = this - other
infix fun StdMoney.divideWith(other: StdMoney): StdMoney = this / other
infix fun StdMoney.multiplyWith(other: StdMoney): StdMoney = this * other

infix fun StdMoney.plusOf(value: Number): StdMoney = object : StdMoney {
    override fun getMoneyCurrency(): StdMoney.Currency {
        return this@plusOf.getMoneyCurrency()
    }

    override fun getMoneyValue(): Double {
        return this@plusOf.getMoneyValue() + value.toDouble()
    }
}

infix fun StdMoney.minusOf(value: Number): StdMoney = object : StdMoney {
    override fun getMoneyCurrency(): StdMoney.Currency {
        return this@minusOf.getMoneyCurrency()
    }

    override fun getMoneyValue(): Double {
        return this@minusOf.getMoneyValue() - value.toDouble()
    }
}

infix fun StdMoney.divideOf(value: Number): StdMoney = object : StdMoney {
    override fun getMoneyCurrency(): StdMoney.Currency {
        return this@divideOf.getMoneyCurrency()
    }

    override fun getMoneyValue(): Double {
        return this@divideOf.getMoneyValue() / value.toDouble()
    }
}

infix fun StdMoney.multiplyOf(value: Number): StdMoney = object : StdMoney {
    override fun getMoneyCurrency(): StdMoney.Currency {
        return this@multiplyOf.getMoneyCurrency()
    }

    override fun getMoneyValue(): Double {
        return this@multiplyOf.getMoneyValue() * value.toDouble()
    }
}

infix fun Number.withCurrency(currency: StdMoney.Currency): StdMoney = object : StdMoney {
    override fun getMoneyCurrency(): StdMoney.Currency {
        return currency
    }

    override fun getMoneyValue(): Double {
        return this@withCurrency.toDouble()
    }
}

infix fun Number.withCurrency(currency: String): StdMoney = this withCurrency object : StdMoney.Currency {
    override fun getCurrency(): String {
        return currency.toUpperCase().trim()
    }
}

// toString function for StdMoney interface
fun StdMoney.asString(): String = "StdMoney(value=${getMoneyValue()}, currency=${getMoneyCurrency().getCurrency()})"
fun StdMoney.asMoneyString(): String = "${getMoneyValue()}:${getMoneyCurrency().getCurrency()}"
fun String?.fromStringToMoney(): StdMoney {
    val values = this?.split(":")
    if (values.isNullOrEmpty()) {
        return StdMoney.ZERO
    }

    val currency = StdMoney.initCurrency(values.firstOrNull())
    val value = values.lastOrNull()?.toDouble() ?: 0.0

    return object : StdMoney {
        override fun getMoneyCurrency(): StdMoney.Currency {
            return currency
        }

        override fun getMoneyValue(): Double {
            return value
        }
    }
}

fun StdMoney.isMatchedCurrency(currency: StdMoney.Currency) =
    this.getMoneyCurrency().getCurrency().equals(currency.getCurrency(), ignoreCase = true)

inline fun buildMoneyConfigProperties(
    builderAction: MoneyConfig.MoneyConfigProperties.MoneyConfigPropertiesBuilder.() -> Unit
): MoneyConfig.MoneyConfigProperties {
    return MoneyConfig
        .builder().apply(builderAction)
        .build()
}

inline fun applyMoneyConfig(
    builderAction: MoneyConfig.() -> Unit,
) {
    MoneyConfig.apply(builderAction)
}