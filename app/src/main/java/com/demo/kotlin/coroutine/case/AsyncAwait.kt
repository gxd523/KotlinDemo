package com.demo.kotlin.coroutine.case

import com.bennyhuo.kotlin.coroutinebasics.utils.log
import com.demo.kotlin.coroutine.dispatcher.DefaultDispatcher
import com.demo.kotlin.coroutine.dispatcher.DispatcherContext
import com.demo.kotlin.coroutine.githubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.*

suspend fun <T> AsyncCoroutine.await(block: () -> Call<T>) = suspendCoroutine<T> { completion ->
    block().enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                response.body()?.let(completion::resume) ?: completion.resumeWithException(NullPointerException())
            } else {
                completion.resumeWithException(HttpException(response))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            completion.resumeWithException(t)
        }
    })
}

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncCoroutine.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Unit> {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}

fun main() {
    val dispatcherContext = DispatcherContext(DefaultDispatcher)
    async(dispatcherContext) {
        val user = await { githubApi.getUserCallback("gxd523") }
        user.let { "result = $it" }.let(::log)
    }
}