package tim.huang.genlayout.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.engine.java.*


actual fun createHttpClient(): WrappedHttpClient {
    return object : WrappedHttpClient {

        val client = HttpClient(Java)

        override suspend fun greeting(): String {
            return client.get("https://ktor.io/docs/").bodyAsText()
        }
    }
}