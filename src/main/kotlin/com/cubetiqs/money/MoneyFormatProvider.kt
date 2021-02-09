package com.cubetiqs.money

import java.math.RoundingMode

interface MoneyFormatProvider {
    fun getPattern(): String? {
        return null
    }

    fun getPrecision(): Int? {
        return null
    }

    fun getRoundingMode(): RoundingMode? {
        return null
    }
}