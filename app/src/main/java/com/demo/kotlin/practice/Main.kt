package com.demo.kotlin.practice

import com.demo.kotlin.annotation.PoKo

fun main() {
    val country = Country(0, "中国")
    println(country)
    val (id, name) = Country(1, "美国")

}

@PoKo
data class Country(val id: Int, val name: String)