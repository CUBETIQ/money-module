package com.cubetiqs.money

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SpecialString(val value: String = "", val upperCase: Boolean = true, val trim: Boolean = true)