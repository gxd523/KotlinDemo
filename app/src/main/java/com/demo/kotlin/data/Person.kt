package com.demo.kotlin.data

import com.demo.kotlin.reflect.MyAnno

data class Person(@MyAnno val name: String = "gxd", val age: Int = 22) {
    fun print() = println("print...$this")
}