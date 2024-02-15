package tim.huang.genlayout.data.network
import io.ktor.client.*
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

actual fun createHttpClient(): WrappedHttpClient {
    return object : WrappedHttpClient {
        val client = HttpClient()

        override suspend fun greeting(): String {
            val response = client.get("https://ktor.io/docs/")
            return response.bodyAsText()
        }
    }
}