import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement
import tim.huang.genlayout.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    var hasLogin by mutableStateOf(false)

    onWasmReady {

        CanvasBasedWindow("ComposeTarget") {
            LaunchedEffect(hasLogin){
                document.getElementById("ComposeTarget")?.let {
                    val canvas = it as HTMLCanvasElement
                    canvas.style.display = if (hasLogin) "block" else "none"
                }
            }

            if (hasLogin){
                App()
            }
        }
    }
}
