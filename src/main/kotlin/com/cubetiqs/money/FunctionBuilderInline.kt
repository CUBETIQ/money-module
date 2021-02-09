package com.cubetiqs.money

inline fun buildMoneyConfigProperties(
    builderAction: MoneyConfig.MoneyConfigProperties.MoneyConfigPropertiesBuilder.() -> Unit
): MoneyConfig.MoneyConfigProperties {
    return MoneyConfig
        .builder().apply(builderAction)
        .build()
}

inline fun applyMoneyConfig(
    builderAction: MoneyConfig.() -> Unit,
) {
    MoneyConfig.apply(builderAction)
}

inline fun buildMoneyFormatter(
    builderAction: MoneyFormatter.() -> Unit
): MoneyFormatter {
    return MoneyFormatter().apply(builderAction)
}