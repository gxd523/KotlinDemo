package com.demo.kotlin.dsl

open class Tag(private val name: String, private val value: String? = null) : Node {
    internal val childTagList = ArrayList<Node>()
    internal val properties = HashMap<String, String>()

    override fun render(): String {
        return StringBuilder()
            .append("<")
            .append(name)
            .apply {// 添加属性
                properties.forEach {
                    append(" ${it.key} = \"${it.value}\"")
                }
            }
            .apply {
                if (childTagList.isEmpty() && value == null) {
                    append("/>")
                } else {
                    append(">")
                        .apply {
                            if (value != null) {
                                append(value)
                            } else {
                                childTagList.map(Node::render).forEach(this::append)
                            }
                        }
                    append("</$name>")
                }
            }
            .toString()
    }

    operator fun String.invoke(value: String) {
        properties[this] = value
    }

    operator fun String.invoke(value: String? = null, block: Tag.() -> Unit) {
        childTagList.add(Tag(this, value).apply(block))
    }
}