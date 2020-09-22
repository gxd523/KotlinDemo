package com.demo.kotlin.dsl

fun html(block: Tag.() -> Unit): Node {
    return Tag("html").apply(block)
}

fun Tag.head(block: Head.() -> Unit) {
    this.childTagList.add(Head().apply(block))
}

fun Tag.body(block: Body.() -> Unit) {
    this.childTagList.add(Body().apply(block))
}

fun Tag.img(block: Img.() -> Unit) {
    this.childTagList.add(Img().apply(block))
}

class Head : Tag("head")
class Body : Tag("body")
class Img : Tag("img") {
    var src by ImgSrcDelegate(properties)
}