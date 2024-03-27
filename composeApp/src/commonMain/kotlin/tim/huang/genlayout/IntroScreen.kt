package tim.huang.genlayout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import genlayout.composeapp.generated.resources.Res
import genlayout.composeapp.generated.resources.ic_google_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun IntroScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ask KeepPro GPT to build your number ticket service right now",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )

        //another text to show sub title


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "https://example.com",
            color = Color.Blue,
            modifier = Modifier.clickable {
                // Handle link click here
            },
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Started to use already?\nLog in to see console and account status",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoogleSignInButton(onClick = { /* Handle Google sign in here */ })
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.ic_google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google")
        }
    }
}