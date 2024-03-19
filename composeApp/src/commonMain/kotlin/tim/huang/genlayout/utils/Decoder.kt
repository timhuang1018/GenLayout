package tim.huang.genlayout.utils

import io.ktor.utils.io.core.String
import kotlin.io.encoding.Base64.Default.decode
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Decode a JWT token into a pair of header and payload. The data is in json format.
 */
@Throws(IllegalArgumentException::class)
fun decodeJwt(token: String): Pair<String, String> {
    val parts = token.split(".")
    if (parts.size != 3) throw IllegalArgumentException("Invalid JWT token.")

    val header = decodeByBase64(parts[0])
    val payload = decodeByBase64(parts[1])

    return Pair(header, payload)
}

@OptIn(ExperimentalEncodingApi::class)
fun decodeByBase64(input: String): String {
    val byteArray = decode(input)
    return String(byteArray)
}
