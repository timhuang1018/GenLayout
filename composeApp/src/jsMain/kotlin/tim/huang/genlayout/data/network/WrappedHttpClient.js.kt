package tim.huang.genlayout.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.JsClient

actual fun createHttpClient(): WrappedHttpClient {
    return object : WrappedHttpClient {
        private val ktorClient = HttpClient(JsClient())
//        override suspend fun getAsBytes(urlString: String): ByteArray {
//            return ktorClient.get(urlString).readBytes()
//        }

        override suspend fun greeting(): String {
            TODO("Not yet implemented")
        }
    }
}