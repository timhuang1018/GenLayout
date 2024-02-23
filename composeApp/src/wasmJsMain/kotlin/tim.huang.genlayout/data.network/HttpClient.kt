package tim.huang.genlayout.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

actual fun createHttpClient(): WrappedHttpClient {

    val client = HttpClient()

    return object : WrappedHttpClient {
        override suspend fun greeting(): String {
            return client.get("https://65d7fb0727d9a3bc1d7bf88e.mockapi.io/api/v1/temp").bodyAsText()
        }

    }
}
