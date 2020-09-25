package com.demo.kotlin.generic

/**
 * Created by guoxiaodong on 2020/9/24 16:56
 */
fun main() {
    val genericOut: GenericOut<*> = object : GenericOut<Int>() {
        override fun get(): Int {
            return 1
        }

    }
    val get = genericOut.get()

    val genericIn: GenericIn<*> = object : GenericIn<String>() {
        override fun set(t: String) {
        }
    }
//    genericIn.set()

    val mutableListOf: MutableList<*> = mutableListOf(1, 2, 3)
    val any = mutableListOf[0]
//    mutableListOf.set()

    val raw: Raw<*> = Raw.getRaw()

}


abstract class GenericIn<in T> {
    abstract fun set(t: T)
}

abstract class GenericOut<out T> {
    abstract fun get(): T
}

open class A
open class B : A()
open class C : B()

open class Hello<T>
open class KKK : Hello<Hello<*>>()