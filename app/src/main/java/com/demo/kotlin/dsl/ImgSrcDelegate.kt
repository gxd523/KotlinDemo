package com.demo.kotlin.dsl

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ImgSrcDelegate(private val map: MutableMap<String, String>) : ReadWriteProperty<Img, String> {
    override fun getValue(thisRef: Img, property: KProperty<*>): String {
        return map[property.name] ?: ""
    }

    override fun setValue(thisRef: Img, property: KProperty<*>, value: String) {
        println("thisRef = $thisRef, property = $property, value = $value")
        map[property.name] = value
    }
}