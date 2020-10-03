package com.demo.kotlin.coroutine

import com.bennyhuo.kotlin.coroutinebasics.utils.log
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED

/**
 * Created by guoxiaodong on 2020/9/26 12:47
 */
fun main() {
    val completion = object : Continuation<Unit> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            result.getOrNull().let { "resumeWith...$it" }.let(::log)
        }
    }

    val createCoroutine = ::main1.createCoroutine(completion)
    createCoroutine.resume(Unit)// 启动协程
    "main end".let(::log)
}

suspend fun main1() {
    suspendCoroutine<String> {
        thread {
            Thread.sleep(3000)
            it.resume("xxxx")
        }
    }.let { "1...$it" }.let(::log)
    suspendCoroutine<Int> {
        it.resume(111)
    }.let { "2...$it" }.let(::log)
    suspendCoroutine<Boolean> {
        thread {
            Thread.sleep(1000)
            it.resume(true)
        }
        COROUTINE_SUSPENDED
    }.let { "3...$it" }.let(::log)
}