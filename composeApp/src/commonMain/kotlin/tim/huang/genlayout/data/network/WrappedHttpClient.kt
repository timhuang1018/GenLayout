package tim.huang.genlayout.data.network

interface WrappedHttpClient {

    suspend fun greeting(): String

}

expect fun createHttpClient(): WrappedHttpClient