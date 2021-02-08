package com.cubetiqs.money

import java.io.Serializable

/**
 * @author sombochea <Sambo Chea>
 * @email sombochea@cubetiqs.com>
 * @date 08/02/21
 * @since 1.0
 */
data class MoneyHistory(
    val valueOn: String? = null,
    val valueOf: String? = null,
    val currency: String? = null
) : Serializable