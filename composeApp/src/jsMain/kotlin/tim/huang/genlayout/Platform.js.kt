package tim.huang.genlayout

class JsPlatform : Platform {
    override val name: String = "JS"
}

actual fun getPlatform(): Platform {
    return JsPlatform()
}