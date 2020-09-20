package io

import java.io.File
import kotlin.reflect.KProperty

class MyTxtFile(name: String, private val append: Boolean = false) {
    private val file = File(name)

    init {
        println("文件已存在：${file.delete()}")
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("thisRef = $thisRef, property = $property")
        return file.readText()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        if (append) {
            file.appendText(value)
        } else {
            file.writeText(value)
        }
    }
}

fun main() {
    var txtFile by MyTxtFile("a.txt", true)
    txtFile = "abcd"
    println(txtFile)
    txtFile = "efgh"
    println(txtFile)
}