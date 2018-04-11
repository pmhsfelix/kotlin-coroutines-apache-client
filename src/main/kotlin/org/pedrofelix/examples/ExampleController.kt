package org.pedrofelix.examples

import kotlinx.coroutines.experimental.future.future
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.concurrent.FutureCallback
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.util.concurrent.CancellationException
import kotlin.coroutines.experimental.suspendCoroutine


@RestController
@RequestMapping("/example")
class ExampleController(val client : CloseableHttpAsyncClient) {

    @GetMapping("")
    fun get() = future {
        val get = HttpGet("https://api.github.com/users/pmhsfelix")
        suspendCoroutine<String> {cont ->
            client.execute(get, object: FutureCallback<HttpResponse> {
                override fun completed(result: HttpResponse?) {
                    cont.resume(result!!.entity.content.bufferedReader().use { it.readText() })
                }

                override fun failed(ex: Exception?) {
                    cont.resumeWithException(ex!!)
                }

                override fun cancelled() {
                    cont.resumeWithException(CancellationException())
                }

            })
        }
    }
}