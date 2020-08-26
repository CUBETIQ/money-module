package com.cubetiqs.libra.moneyutils

interface StdMoneyFormation {
    fun format(): String
    fun toMoneyString(overrideSymbol: Char? = null): String
}