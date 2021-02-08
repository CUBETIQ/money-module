package com.cubetiqs.money

/**
 * Default money currency state exception
 */
class MoneyCurrencyStateException(message: String? = null) :
    IllegalStateException(message ?: "money currency is illegal state!")