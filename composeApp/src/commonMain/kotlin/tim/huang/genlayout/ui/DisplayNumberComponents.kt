package tim.huang.genlayout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StrokeWidthControl(value: Dp, inc: () -> Unit, dec: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.border(1.dp, Color.Black)
    ) {

        Text("Stroke Width",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            fontSize = 24.sp
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(TriangleShapeUp)
                    .background(Color.DarkGray)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            radius = 32.dp,
                            color = Color.Black
                        ),
                        onClick = { inc() }
                    )
                    .align(Alignment.CenterVertically)
                    ,
            )
            Text(
                value.value.toInt().toString(),
                modifier = Modifier.padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 32.sp
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(TriangleShapeDown)
                    .background(Color.DarkGray)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            radius = 32.dp,
                            color = Color.Black
                        ),
                        onClick = { dec() }
                    )
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

private val TriangleShapeUp = GenericShape { size, _ ->
    moveTo(size.width / 2f, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
}

private val TriangleShapeDown = GenericShape { size, _ ->
    moveTo(size.width / 2f, size.height)
    lineTo(size.width, 0f)
    lineTo(0f, 0f)
}