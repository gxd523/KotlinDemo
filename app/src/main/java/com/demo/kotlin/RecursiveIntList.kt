package com.demo.kotlin


fun main() {
    val list = intListOf(1, 2, 3, 4, 5)
    println(list.joinToString(','))
    list.sum().let(::println)
    val (first, second, third) = list
    println("$first, $second, $third")
}

/**
 * 递归集合
 */
sealed class IntList {
    object Nil : IntList()

    data class Cons(val head: Int, val tail: IntList) : IntList()

    override fun toString(): String {
        return when (this) {
            Nil -> "Nil"
            is Cons -> "$head, $tail"
        }
    }

    fun joinToString(separator: Char = ' '): String {
        return when (this) {
            Nil -> "Nil"
            is Cons -> "$head$separator${tail.joinToString(separator)}"
        }
    }
}

fun IntList.sum(): Int {
    return when (this) {
        IntList.Nil -> 0
        is IntList.Cons -> head + tail.sum()
    }
}

fun intListOf(vararg intArray: Int): IntList {
    return when (intArray.size) {
        0 -> IntList.Nil
        else -> {
            val sliceIntArray = intArray.slice(1 until intArray.size).toIntArray()
            IntList.Cons(
                intArray[0],
                intListOf(*sliceIntArray)
            )
        }
    }
}

operator fun IntList.component1(): Int? {
    return when (this) {
        IntList.Nil -> null
        is IntList.Cons -> head
    }
}

operator fun IntList.component2(): Int? {
    return when (this) {
        IntList.Nil -> null
        is IntList.Cons -> tail.component1()
    }
}

operator fun IntList.component3(): Int? {
    return when (this) {
        IntList.Nil -> null
        is IntList.Cons -> tail.component2()
    }
}

operator fun IntList.component4(): Int? {
    return when (this) {
        IntList.Nil -> null
        is IntList.Cons -> tail.component3()
    }
}