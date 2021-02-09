package com.cubetiqs.money

import java.io.Serializable
import java.math.RoundingMode
import java.text.NumberFormat
import javax.swing.text.NumberFormatter

/**
 * Money Formatter (Final class)
 *
 * @see MoneyConfig for format properties for each of value within currency
 * @see DecimalUtils for Utils formatter with number
 */
class MoneyFormatter(
    private var pattern: String? = null,
    private var precision: Int? = null,
    private var roundingMode: RoundingMode? = null,
) : Serializable, StdMoneyFormation, MoneyFormatProvider {
    fun setPattern(pattern: String?) = apply { this.pattern = pattern }
    fun setPrecision(precision: Int?) = apply { this.precision = precision }
    fun setRoundingMode(roundingMode: RoundingMode?) = apply { this.roundingMode = roundingMode }
    fun setProvider(provider: MoneyFormatProvider?) = apply {
        if (provider != null) {
            this.pattern = provider.getPattern()
            this.precision = provider.getPrecision()
            this.roundingMode = provider.getRoundingMode()
        }
    }

    // when want to format the value for each of them, need to parse the money value here
    private var value: StdMoney? = null
    fun setValue(value: StdMoney?) = apply { this.value = value }

    constructor(value: StdMoney?) : this() {
        this.value = value
    }

    override fun getPattern() = pattern?.trim()
    override fun getPrecision() = precision ?: -1
    override fun getRoundingMode() = roundingMode

    override fun format(): String {
        value?.getValue() ?: return ""

        if (MoneyConfig.isAutoLocaleFormatterEnabled()) {
            val systemCurrency = if (MoneyConfig.isAutoCurrencyFormatterEnabled()) {
                MoneyConfig.getCurrency()
            } else {
                value?.getCurrency()?.findCurrency()
            }

            if (systemCurrency != null) {
                val numberFormatter = NumberFormat.getNumberInstance(MoneyConfig.getLocale())
                numberFormatter.currency = systemCurrency
                return numberFormatter.format(value?.getValue())
            }
        }

        if (getPattern() == null && getPrecision() < 0 && getRoundingMode() == null) {
            return value?.getValue().toString()
        }

        if (getPrecision() > -1) {
            return DecimalUtils.toDecimalPrecision(value?.getValue() ?: 0, getPrecision(), getRoundingMode()) ?: ""
        }

        return DecimalUtils.toStringDecimal(value?.getValue() ?: 0, getPattern(), getRoundingMode())
    }

    override fun toMoneyString(overrideSymbol: Char?): String {
        return value?.asMoneyString(overrideSymbol) ?: ""
    }

    companion object {
        const val DEFAULT_FORMATTER = "defaultFormatter"
        const val DEFAULT_LOCALE = "defaultLocale"
        const val DEFAULT_CURRENCY = "defaultCurrency"
    }
}