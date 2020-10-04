package com.demo.kotlin.coroutine

import com.bennyhuo.kotlin.coroutinebasics.utils.log
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 配合ContinuationImpl
 */
suspend fun main() {
    log(1)
    returnSuspended().let(::log)
    log(2)
    delay(2000)
    log(3)
    returnImmediately().let(::log)
    log(4)
}

suspend fun returnSuspended() = suspendCoroutine<String> {
    thread {
        Thread.sleep(2000)
        it.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String> {
    "Return immediately."
}

suspend fun delay(time: Long) = suspendCoroutine<Unit> {
    thread {
        Thread.sleep(time)
        it.resume(Unit)
    }
}