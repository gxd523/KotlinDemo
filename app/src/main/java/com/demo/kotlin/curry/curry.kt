package com.demo.kotlin.curry

fun main() {
    val curryFo = ::foo.convertCurry()
    val curryFoo = curryFo(1)(true)
    println(curryFoo(1.1f))
    println(curryFoo(1.2f))
    println(curryFoo(1.3f))
}

fun foo(a: Int, b: Boolean, c: Float): String {
    return "a = $a, b = $b, c = $c"
}

fun curryFoo(a: Int) = fun(b: Boolean) = fun(c: Float): String {
    return "a = $a, b = $b, c = $c"
}

fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.convertCurry() = fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)