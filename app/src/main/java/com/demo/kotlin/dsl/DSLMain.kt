package com.demo.kotlin.dsl

import java.io.File

fun main() {
    html {
        "name"("Hao123")
        head {
            "meta"{
                "http-equiv"("Content-Type")
                "content"("text/html; charset=UTF-8")
            }
        }
        body {
            "a"("百度") {
                "href"("https://www.baidu.com/")
            }
            img {
                "width"("30px")
                "height"("30px")
                src = "https://img.mukewang.com/54a404600001811801000100-100-100.jpg"
            }
        }
    }.render()
        .apply(::print)
        .apply { File("a.html").writeText(this) }

}