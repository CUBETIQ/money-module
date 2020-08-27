package com.cubetiqs.libra.moneyutils

import java.lang.reflect.Field

class SpecialStringProcessor: ISerializer {
    override fun <T> serialize(data: T?): T? {
        if (data == null) return null
        val clazz = data.javaClass
        clazz.declaredFields.forEach {
            it.isAccessible = true
            if (it.isAnnotationPresent(SpecialString::class.java) && it.genericType == String::class.java) {
                val annotationValues = it.getAnnotation(SpecialString::class.java)
                var value = it.get(data).toString()
                if (annotationValues.trim) {
                    value = value.trim()
                }
                if (annotationValues.upperCase) {
                    value = value.toUpperCase()
                }
                it.set(data, value)
            }
        }

        return data
    }

    private fun getSerializeKey(field: Field): String {
        val annotationValue = field.getAnnotation(SpecialString::class.java).value
        return if (annotationValue.isEmpty()) {
            field.name
        } else annotationValue
    }
}

interface ISerializer {
    fun <T> serialize(data: T?): T?
}