package tim.huang.genlayout

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform