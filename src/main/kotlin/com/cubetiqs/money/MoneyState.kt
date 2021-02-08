package com.cubetiqs.money

import java.io.Serializable

/**
 * @author sombochea <Sambo Chea>
 * @email sombochea@cubetiqs.com
 * @date 08/02/21
 * @since 1.0
 */
data class MoneyState(
    var value: String? = null,
    var currency: String? = null,
    var history: MoneyHistory? = null
) : Serializable