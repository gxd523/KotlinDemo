package com.demo.kotlin.practice

class Outer {
    val a = 0

    inner class Inner {
        fun hello() {
            println(this@Outer.a)
        }
    }
}