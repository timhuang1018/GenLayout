package tim.huang.genlayout.data.network

actual fun createHttpClient(): WrappedHttpClient {
    return object : WrappedHttpClient {
        override suspend fun greeting(): String {
            TODO("Not yet implemented")
        }

    }
}
