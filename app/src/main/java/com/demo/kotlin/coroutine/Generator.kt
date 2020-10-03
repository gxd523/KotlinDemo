package com.demo.kotlin.coroutine

import kotlin.coroutines.*

/**
 * Created by guoxiaodong on 2020/10/2 18:46
 */
fun main() {
    val createGenerator = generator { start: Int ->
        (0..5).forEach {
            yield(start + it)
        }
    }

    val generator = createGenerator(10)
    val iterator = generator.iterator()
    while (iterator.hasNext()) {
        iterator.next().let(::println)
    }
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit): (T) -> Generator<T> {
    return { GeneratorImpl(it, block) }
}

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(private val param: T, private val block: suspend GeneratorScope<T>.(T) -> Unit) : Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(param, block)
    }
}

abstract class GeneratorScope<T> internal constructor() {
    protected abstract val param: T

    abstract suspend fun yield(value: T)
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>) : State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) : State()
    object Done : State()
}

class GeneratorIterator<T>(override val param: T, private val block: suspend GeneratorScope<T>.(T) -> Unit) : Iterator<T>,
    Continuation<Any?>, GeneratorScope<T>() {
    override val context: CoroutineContext = EmptyCoroutineContext
    private var state: State

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = { block(param) }
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start)
    }

    private fun resume() {
        when (val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
        }
    }

    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    override fun next(): T {
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IndexOutOfBoundsException("No Value Left!!!")
        }
    }

    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()

    }

    override suspend fun yield(value: T) = suspendCoroutine<Unit> {
        state = when (state) {
            is State.NotReady -> State.Ready(it, value)
            is State.Ready<*> -> throw IllegalStateException("状态异常!!!")
            State.Done -> throw IllegalStateException("状态异常!!!")
        }
    }
}