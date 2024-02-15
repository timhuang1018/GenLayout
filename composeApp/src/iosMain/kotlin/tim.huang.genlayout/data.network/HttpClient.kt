package tim.huang.genlayout.data.network
import io.ktor.client.*

actual fun createHttpClient(): WrappedHttpClient {
    return object : WrappedHttpClient {
        override suspend fun greeting(): String {
            TODO("Not yet implemented")
        }
    }
}
