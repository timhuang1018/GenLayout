package tim.huang.genlayout

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        App()
    }

    jsLog("hello from kotlin js")
}


//fun getFirebaseOptions(vars: String): FirebaseOptions = js("{vars}")

fun jsLog(log: String): Unit = js("{ console.log(log); }")


fun dbg(): Unit = js("{ debugger; }")

@OptIn(ExperimentalJsExport::class)
@JsExport
fun handleCredentialResponse(response: JsAny) {
    jsLog(response.toString())
}