package com.cubetiqs.money

import java.util.*

/**
 * Money Object Generator (Quick building the money)
 *
 * @since 1.0
 * @author sombochea
 */
class MoneyObject(
    val value: Double,
    val currency: String,
    var operator: MoneyOperator? = null,
    var with: MoneyObject? = null,
) : StdMoney {
    fun appendWith(with: MoneyObject?) {
        if (this.with == null) {
            this.with = with
        } else {
            this.with!!.appendWith(with)
        }
    }

    override fun getMoneyCurrency(): StdMoney.Currency {
        return StdMoney.initCurrency(currency)
    }

    override fun getMoneyValue(): Double {
        return value
    }

    private fun generate(): StdMoney {
        if (this.with != null) {
            val withGenerated = this.with!!.generate()
            return when (operator) {
                MoneyOperator.PLUS -> this plusWith withGenerated
                MoneyOperator.MINUS -> this minusWith withGenerated
                MoneyOperator.DIVIDE -> this divideWith withGenerated
                MoneyOperator.MULTIPLY -> this multiplyWith withGenerated
                // if operator is null or empty with default Plus operator
                else -> this plusWith withGenerated
            }
        }

        return StdMoney.initMoney(value, currency = StdMoney.initCurrency(currency))
    }

    fun compute(): StdMoney {
        return generate()
    }

    override fun toString(): String {
        return "MoneyObject(value=$value, currency='$currency', operator=$operator, with=$with)"
    }

    enum class MoneyOperator {
        // (+)
        PLUS,

        // (-)
        MINUS,

        // (/)
        DIVIDE,

        // (*)
        MULTIPLY;

        companion object {
            fun operator(value: Char?): MoneyOperator {
                return try {
                    when (value!!) {
                        '+' -> PLUS
                        '-' -> MINUS
                        '/' -> DIVIDE
                        '*' -> MULTIPLY
                        else -> throw IllegalArgumentException("operator not found with value: $value!")
                    }
                } catch (ex: Exception) {
                    throw IllegalArgumentException("operator not found!")
                }
            }
        }
    }

    class MoneyGeneratorBuilder {
        private var currency: String? = null
        private val withs: MutableCollection<MoneyObject> = LinkedList()

        fun withCurrency(currency: String) = apply {
            this.currency = currency
        }

        fun with(`object`: MoneyObject) = apply {
            this.withs.add(`object`)
        }

        fun with(value: Double, currency: String, operator: MoneyOperator = MoneyOperator.PLUS) = apply {
            this.with(
                MoneyObject(
                    value, currency, operator
                )
            )
        }

        fun with(value: Double, currency: String, operator: Char) = apply {
            this.with(
                MoneyObject(
                    value, currency, MoneyOperator.operator(operator)
                )
            )
        }

        fun build(): MoneyObject {
            val first: MoneyObject
            if (this.currency.isNullOrEmpty() && withs.isNotEmpty()) {
                first = withs.first()
                withs.remove(first)
            } else {
                first = MoneyObject(
                    value = 0.0,
                    operator = MoneyOperator.PLUS,
                    currency = this.currency ?: MoneyCurrency.USD.getCurrency()
                )
            }

            withs.forEach { with ->
                first.appendWith(with)
            }

            return first
        }

        override fun toString(): String {
            return "MoneyGeneratorBuilder(currency=$currency, withs=$withs)"
        }
    }

    companion object {
        fun builder() = MoneyGeneratorBuilder()
    }
}