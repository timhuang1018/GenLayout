import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun NumberTicketApp() {

    VendorConsole()
}

@Composable
fun VendorConsole() {

    NumberDisplayScreen(396)
}

@Composable
fun NumberDisplayScreen(number: Int, defaultDigits: Int = 3) {
    LED(number, defaultDigits)
}

@Composable
fun LED(number: Int, defaultDigits: Int, widthUnit: Dp = 30.dp) {
    //TODO consider defaultDigits
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
    ){


        Row  {
            for (singleValue in number.toString()){
                val ledNumber = singleValue.toString().toInt().toLEDNumber()
                LEDNumberDisplay(modifier = Modifier
                    .weight(1f)
                    .padding(32.dp)
                    .fillMaxSize(), ledNumber, widthUnit)

            }
        }
    }
}

@Composable
fun LEDNumberDisplay(modifier: Modifier = Modifier, ledNumber: LEDNumber, widthUnit: Dp) {

    Canvas(modifier = modifier
    ){

        val strokeWidth = widthUnit.toPx()

        ledNumber.stickColors.forEachIndexed { stickIndex, value ->
            when(stickIndex + 1){
                1 -> {
                    drawStick(strokeWidth, value)
                }
                2 -> {
                    withTransform({
                        rotate(90f, Offset(strokeWidth/2, strokeWidth/2))
                    }){
                        drawStick(strokeWidth, value)
                    }
                }
                3 -> {
                    withTransform({
                        rotate(90f, Offset(strokeWidth/2, strokeWidth/2))
                        translate( 0f, -(size.width - strokeWidth))
                    }){
                        drawStick(strokeWidth, value)
                    }
                }
                4 -> {
                    withTransform({
                        translate( 0f, size.width - strokeWidth)
                    }){
                        drawStick(strokeWidth, value)
                    }
                }

                5 -> {
                    withTransform({
                        rotate(90f, Offset(strokeWidth/2, strokeWidth/2))
                        translate(size.width - strokeWidth, 0f)
                    }){
                        drawStick(strokeWidth, value)
                    }
                }

                6 -> {
                    withTransform({
                        rotate(90f, Offset(strokeWidth/2, strokeWidth/2))
                        translate( size.width - strokeWidth, -(size.width - strokeWidth))
                    }){
                        drawStick(strokeWidth, value)
                    }
                }

                7 -> {
                    withTransform({
                        translate( 0f, (size.width - strokeWidth) * 2)
                    }){
                        drawStick(strokeWidth, value)
                    }
                }
            }
        }
        //TODO remove draw path of canvas border
        drawPath(
            Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
            color = Color.Green,
            style = Stroke(width = 3f)
        )
    }
}

/**
 * a stick contain two filled triangle at start and end of the stick
 */
fun DrawScope.drawStick(strokeWidth: Float, value: Int) {
    val filledPath = Path()
    filledPath.moveTo(strokeWidth / 2, strokeWidth / 2)
    filledPath.lineTo(strokeWidth, 0f)
    filledPath.lineTo(size.width - strokeWidth, 0f)
    filledPath.lineTo(size.width - strokeWidth / 2, strokeWidth / 2)
    filledPath.lineTo(size.width - strokeWidth, strokeWidth)
    filledPath.lineTo(strokeWidth, strokeWidth)

    filledPath.close()
    drawPath(
        filledPath,
        color = if (value == 1) Color.Red else Color.Black,
    )
}

/**
 * [stickColors] only contain 1 or 0, which 1 is filled and 0 is empty
 */
enum class LEDNumber(val stickColors: IntArray){
    Zero(intArrayOf(1,1, 1, 0, 1, 1, 1)),
    One(intArrayOf(0, 0, 1, 0, 0, 1, 0)),
    Two(intArrayOf(1, 0, 1, 1, 1, 0, 1)),
    Three(intArrayOf(1, 0, 1, 1, 0, 1, 1)),
    Four(intArrayOf(0, 1, 1, 1, 0, 1, 0)),
    Five(intArrayOf(1, 1, 0, 1, 0, 1, 1)),
    Six(intArrayOf(1, 1, 0, 1, 1, 1, 1)),
    Seven(intArrayOf(1, 0, 1, 0, 0, 1, 0)),
    Eight(intArrayOf(1, 1, 1, 1, 1, 1, 1)),
    Nine(intArrayOf(1, 1, 1, 1, 0, 1, 1)),
    None(intArrayOf(0, 0, 0, 0, 0, 0, 0))
}

/**
 * Convert single digit integer value to LEDNumber
 */
private fun Int.toLEDNumber(): LEDNumber {
    return when(this){
        0 -> LEDNumber.Zero
        1 -> LEDNumber.One
        2 -> LEDNumber.Two
        3 -> LEDNumber.Three
        4 -> LEDNumber.Four
        5 -> LEDNumber.Five
        6 -> LEDNumber.Six
        7 -> LEDNumber.Seven
        8 -> LEDNumber.Eight
        9 -> LEDNumber.Nine
        else -> LEDNumber.None
    }
}