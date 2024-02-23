package tim.huang.genlayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {


        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Image(
                    painterResource("settings.xml"),
                    null
                )
            }

            //need a mutable list of strings
            var messages by remember { mutableStateOf(listOf<String>()) }

            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.padding(vertical = 32.dp),
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") }
            )


            Button(onClick = {
                messages = messages + text
                text = ""
            }) {
                Text("Send Query")
            }

            LazyColumn {
                items(messages.size) { index ->
                    Text(messages[index])
                }
            }

//            Image(
//                painterResource("camera.xml"),
//                null
//            )

        }
    }
}

fun todaysDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}