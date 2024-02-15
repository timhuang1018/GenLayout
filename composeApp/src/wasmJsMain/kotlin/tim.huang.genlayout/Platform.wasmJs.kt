package tim.huang.genlayout

private val platform = object : Platform {
    override val name: String
        get() = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = platform