package com.cubetiqs.money

import java.text.DecimalFormat
import java.lang.StringBuilder
import java.math.RoundingMode

/**
 * Java Decimal utils
 *
 * @author sombochea
 * @since 1.0
 */
object DecimalUtils {
    private const val DECIMAL_PATTERN = "#.##"
    private const val ROUNDING_DECIMAL_PATTERN = "##0"
    fun toStringDecimal(value: Number?, pattern: String?): String {
        var _pattern = pattern
        if (_pattern == null || _pattern.isEmpty()) {
            _pattern = DECIMAL_PATTERN
        }
        return DecimalFormat(_pattern).format(value)
    }

    fun toStringDecimal(value: Number?, pattern: String?, roundingMode: RoundingMode?): String {
        var _pattern = pattern
        if (_pattern == null || _pattern.isEmpty()) {
            _pattern = DECIMAL_PATTERN
        }
        if (roundingMode == null) {
            return toStringDecimal(value, _pattern)
        }

        val formatter = DecimalFormat(_pattern)
        formatter.roundingMode = roundingMode
        return formatter.format(value)
    }

    fun toDecimalPrecision(value: Number?, precision: Int? = null, roundingMode: RoundingMode? = null): String? {
        var _precision = precision ?: -1
        if (value == null) {
            return null
        }

        val pattern = StringBuilder(ROUNDING_DECIMAL_PATTERN)
        if (_precision > 0) {
            pattern.append(".")
        }

        while (_precision > 0) {
            pattern.append("0")
            _precision--
        }

        val decimalFormat = DecimalFormat(pattern.toString())
        if (roundingMode != null) {
            decimalFormat.roundingMode = roundingMode
        }

        return decimalFormat.format(value)
    }
}