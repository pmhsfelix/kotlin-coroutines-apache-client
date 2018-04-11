package org.pedrofelix.examples

import org.apache.http.impl.nio.client.HttpAsyncClients
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CoroutinesApacheClientApplication {

    @Bean
    fun httpClient() = HttpAsyncClients.createDefault().also { it.start() }
}

fun main(args: Array<String>) {
    runApplication<CoroutinesApacheClientApplication>(*args)
}


