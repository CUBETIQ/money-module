package com.cubetiqs.money

import java.util.*

/**
 * Money Object Generator (Quick building the money)
 *
 * @since 1.0
 * @author sombochea
 */
open class MoneyObject(
    private var value: Double,
    private var currency: String,

    // operator, used with "with" below
    // example: if we have next we will use this operator to compute the value with
    private var operator: MoneyOperator? = null,
    // the value to compute with current object
    private var with: MoneyObject? = null,
) : StdMoney {
    override fun getCurrency(): StdMoney.Currency {
        return StdMoney.initCurrency(currency)
    }

    override fun getValue(): Double {
        return value
    }

    open fun getOperator(): MoneyOperator? = operator
    open fun getWith(): MoneyObject? = with

    fun appendWithNext(with: MoneyObject?) {
        if (with == null) {
            return
        }

        if (this.with == null) {
            this.with = with
        } else {
            this.with!!.appendWithNext(with)
        }
    }

    /**
     * Internal Generate the value by next compute (First and next)
     *
     * Example: we have 3 items => [10, 20, 50]
     * We want to sum of them => [10 + 20] => [30 + 50] => 80
     */
    private fun generate(): StdMoney {
        // get next record for compute with
        var next = this.with
        // get first operator
        var operatorFirst = this.operator
        while (next != null) {
            val temp = when (operatorFirst) {
                MoneyOperator.PLUS -> this plusWith next
                MoneyOperator.MINUS -> this minusWith next
                MoneyOperator.DIVIDE -> this divideWith next
                MoneyOperator.MULTIPLY -> this multiplyWith next
                // if operator is null or empty with default Plus operator
                else -> this plusWith next
            }
            // move the temp value that computed
            this.value = temp.getValue()
            // move the currency into parent, if changed
            this.currency = temp.getCurrency().getCurrency()
            // move the first operator to previous next operator
            // example: first ops = +, then next ops = -, then inside next ops = *, n
            // return next: first ops = -, then next ops = *, then n
            operatorFirst = next.operator
            // move next element
            next = next.with
        }

        return this
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
            fun operator(value: Any?, defaultOperator: MoneyOperator? = null): MoneyOperator {
                return try {
                    when (val temp = value!!) {
                        is String -> {
                            if (temp.length == 1) {
                                operator(temp[0])
                            } else {
                                valueOf(temp.uppercase().trim())
                            }
                        }
                        is Char -> {
                            when (temp) {
                                '+' -> PLUS
                                '-' -> MINUS
                                '/' -> DIVIDE
                                '*' -> MULTIPLY
                                else -> throw IllegalArgumentException("operator not found with value: $temp!")
                            }
                        }
                        else -> throw IllegalArgumentException("operator not found by value: $value!")
                    }
                } catch (ex: Exception) {
                    defaultOperator ?: throw IllegalArgumentException("operator not found!")
                }
            }
        }
    }

    class MoneyObjectBuilder {
        private val defaultCurrency = MoneyCurrency.USD.getCurrency()

        private var currency: String? = null
        private val withs: Deque<MoneyObject> = ArrayDeque()

        fun withCurrency(currency: String) = apply {
            this.currency = currency
        }

        fun with(value: MoneyObject) = apply {
            this.withs.add(value)
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

        /**
         * Example: String format => usd:1:+,khr:4000.0:-,usd:1 => 1 + (4000 eh) - 1 = 1USD
         */
        fun parseFromString(valuesString: String) = apply {
            val valueGroups = valuesString.split(",")
            valueGroups.forEach { value ->
                val tempValue = value.trim().split(":")
                if (tempValue.isNotEmpty() && !tempValue.firstOrNull().isNullOrEmpty()) {
                    this.with(
                        tempValue.getOrNull(1)?.toDoubleOrNull() ?: 0.0,
                        tempValue.firstOrNull() ?: defaultCurrency,
                        MoneyOperator.operator(tempValue.getOrNull(2), MoneyOperator.PLUS),
                    )
                }
            }
        }

        fun build(): MoneyObject {
            val first: MoneyObject = if (this.currency.isNullOrEmpty() && withs.isNotEmpty()) {
                withs.removeFirst()
            } else {
                MoneyObject(
                    value = 0.0,
                    currency = this.currency ?: defaultCurrency,
                    operator = MoneyOperator.PLUS,
                )
            }

            while (withs.isNotEmpty()) {
                first.appendWithNext(withs.pop())
            }

            return first
        }

        override fun toString(): String {
            return "MoneyGeneratorBuilder(currency=$currency, withs=$withs)"
        }
    }

    companion object {
        fun builder() = MoneyObjectBuilder()
    }
}