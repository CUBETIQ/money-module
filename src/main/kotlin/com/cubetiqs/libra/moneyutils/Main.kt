package com.cubetiqs.libra.moneyutils

class Main {
    fun run() = print("Just mind!")
}

fun main() {
    Main().run()
    val money = Money(10.0)
    val money2 = Money(20.0)
    money *= money
    println((money + money2) * money2)
}