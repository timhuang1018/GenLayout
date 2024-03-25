package tim.huang.genlayout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tim.huang.genlayout.model.NumberTicketVendorConsoleConfigs


@Composable
fun NumberTicketApp() {

    VendorConsole()
}

@Composable
fun VendorConsole() {
    var selectedTab by remember { mutableStateOf(0) }
    var config by remember {
        mutableStateOf(NumberTicketVendorConsoleConfigs())
    }

    Column(modifier = Modifier.background(Color.LightGray)) {
        TabRow(
            modifier = Modifier.height(32.dp),
            selectedTabIndex = selectedTab
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
            ) {
                Text("Number Display Screen")
            }
            // Add more tabs as needed
        }

        Row {
            Column {
                when (selectedTab) {
                    0 -> {
                        // Display buttons for Number Display Screen
                        Column {

                        }

                        // Add more buttons as needed
                    }
                    // Add more cases as needed
                }
            }

            Box(
                modifier = Modifier.fillMaxSize().background(Color.LightGray)
            ) {
                when (selectedTab) {
                    0 -> NumberDisplayScreen(396, config = config)
                    // Add more cases as needed
                }
            }
        }
    }
}
@Composable
fun NumberDisplayScreen(
    number: Int,
    defaultDigits: Int = 3,
    config: NumberTicketVendorConsoleConfigs
) {
    LED(number, defaultDigits, config.strokeWidth)
}

/**
 * each LED number should have 7 sticks, and the height is 2 times of the width - strokeWidth
 */
@Composable
fun LED(number: Int, defaultDigits: Int, strokeWidth: Dp = 30.dp) {
    //TODO consider defaultDigits
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
    ){

        Layout(content = {
            for (singleValue in number.toString()){
                val ledNumber = singleValue.toString().toInt().toLEDNumber()
                LEDNumberDisplay(modifier = Modifier
                    .fillMaxSize(), ledNumber, strokeWidth)
            }
        }){ measurables, constraints ->

            val padding = 0.04f * constraints.maxWidth
            val singleWidth = ((constraints.maxWidth - padding * (measurables.size + 1)) / measurables.size).let { width ->
                //check if need to scale so that the height is not exceed the constraints
                val scale: Float = if (width * 2 - strokeWidth.roundToPx() > (constraints.maxHeight - 2 * padding) ) (constraints.maxHeight - 2 * padding) / (width * 2 - strokeWidth.roundToPx())  else 1f
                width * scale
            }

            val singleHeight = singleWidth * 2 - strokeWidth.roundToPx()

            val itemConstraints = constraints.copy(
                minWidth = singleWidth.toInt() ,
                maxWidth = singleWidth.toInt(),
                minHeight = singleHeight.toInt() ,
                maxHeight = singleHeight.toInt(),
            )
            val placeables = measurables.map { measurable ->
                // Measure each children
                measurable.measure(itemConstraints)
            }

            val vacancyWidth = constraints.maxWidth - (singleWidth * placeables.size + padding * (placeables.size + 1))

            layout(constraints.maxWidth, constraints.maxHeight){

                var xPosition = padding + vacancyWidth / 2
                val yPosition = (constraints.maxHeight - singleHeight) / 2
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(x = xPosition.toInt(), y = yPosition.toInt())
                    xPosition += singleWidth + padding
                }
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