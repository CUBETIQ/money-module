package com.cubetiqs.money

interface StdMoneyFormation {
    fun format(): String
    fun toMoneyString(overrideSymbol: Char? = null): String
}