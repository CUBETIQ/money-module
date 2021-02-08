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
    override fun getCurrency(): StdMoney.Currency {
        return this@plusOf.getCurrency()
    }

    override fun getValue(): Double {
        return this@plusOf.getValue() + value.toDouble()
    }
}

infix fun StdMoney.minusOf(value: Number): StdMoney = object : StdMoney {
    override fun getCurrency(): StdMoney.Currency {
        return this@minusOf.getCurrency()
    }

    override fun getValue(): Double {
        return this@minusOf.getValue() - value.toDouble()
    }
}

infix fun StdMoney.divideOf(value: Number): StdMoney = object : StdMoney {
    override fun getCurrency(): StdMoney.Currency {
        return this@divideOf.getCurrency()
    }

    override fun getValue(): Double {
        return this@divideOf.getValue() / value.toDouble()
    }
}

infix fun StdMoney.multiplyOf(value: Number): StdMoney = object : StdMoney {
    override fun getCurrency(): StdMoney.Currency {
        return this@multiplyOf.getCurrency()
    }

    override fun getValue(): Double {
        return this@multiplyOf.getValue() * value.toDouble()
    }
}

infix fun Number.withCurrency(currency: StdMoney.Currency): StdMoney = object : StdMoney {
    override fun getCurrency(): StdMoney.Currency {
        return currency
    }

    override fun getValue(): Double {
        return this@withCurrency.toDouble()
    }
}

infix fun Number.withCurrency(currency: String): StdMoney = this withCurrency object : StdMoney.Currency {
    override fun getCurrency(): String {
        return currency.toUpperCase().trim()
    }
}

// toString function for StdMoney interface
fun StdMoney.asString(): String = "StdMoney(value=${getValue()}, currency=${getCurrency().getCurrency()})"
fun StdMoney.asMoneyString(): String = "${getValue()}:${getCurrency().getCurrency()}"
fun String?.fromStringToMoney(): StdMoney {
    val values = this?.split(":")
    if (values.isNullOrEmpty()) {
        return StdMoney.ZERO
    }

    val currency = StdMoney.initCurrency(values.firstOrNull())
    val value = values.lastOrNull()?.toDouble() ?: 0.0

    return object : StdMoney {
        override fun getCurrency(): StdMoney.Currency {
            return currency
        }

        override fun getValue(): Double {
            return value
        }
    }
}

fun StdMoney.isMatchedCurrency(currency: StdMoney.Currency) =
    this.getCurrency().getCurrency().equals(currency.getCurrency(), ignoreCase = true)

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