package com.demo.kotlin.coroutine

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

fun async(block: suspend AsyncCoroutine.() -> Unit) {
    val completion = AsyncCoroutine()
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine : Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}

fun main() {
    async {
        val user = await { githubApi.getUserCallback("gxd523") }
        user.let { "result = $it" }.let(::log)
    }
}