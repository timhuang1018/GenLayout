import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.gitlive.firebase.auth.GoogleAuthProvider
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.jetbrains.skiko.wasm.onWasmReady
import tim.huang.genlayout.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    GoogleAuthProvider
    onWasmReady {
        CanvasBasedWindow("ImageViewer") {
            App()
        }
    }
}
