package com.deliveryhero.dinein.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.layout
import com.deliveryhero.dinein.ui.state.ZoomableState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.abs
import com.deliveryhero.dinein.ui.TransformEvent.TransformStopped
import com.deliveryhero.dinein.ui.TransformEvent.TransformStarted
import com.deliveryhero.dinein.ui.TransformEvent.TransformDelta

/**
 * A zoomable layout that can handle zoom in and out with drag support.
 *
 * @param state the state object to be used to observe the [Zoomable] state.
 * @param modifier the modifier to apply to this layout.
 * @param doubleTapScale a function called on double tap gesture, will scale to returned value.
 * @param content a block which describes the content.
 * @param onTap for interaction if need onClicked action
 *
 * This is a inspiration from Tlaster's project: Zoomable
 * @see <a href="https://github.com/Tlaster/Zoomable">reference here</a>
 */
@Composable
fun Zoomable(
    state: ZoomableState,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onTap: () -> Unit = {},
    doubleTapScale: (() -> Float)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val transition = updateTransition(targetState = state, label = "")
    val settingScale by transition.animateFloat(label = "") { it.scale }
    val settingTranslationX by transition.animateFloat(label = "") { it.translateX }
    val settingTranslationY by transition.animateFloat(label = "") { it.translateY }
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier,
    ) {

        var childWidth by remember { mutableStateOf(0) }
        var childHeight by remember { mutableStateOf(0) }
        fun getBounds(updateScale: Float): Pair<Float, Float> { //return boundary for translationX,Y
            return (childWidth * updateScale - constraints.maxWidth).coerceAtLeast(0F) / 2F to
                    (childHeight * updateScale - constraints.maxHeight).coerceAtLeast(0F) / 2F
        }

        LaunchedEffect(
            childHeight,
            childWidth,
        ) {
            val (maxX, maxY) = getBounds(state.scale)
            state.updateBounds(maxX, maxY)
        }

        val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
            scope.launch {
                state.onZoomChange(zoomChange)
                val (maxX, maxY) = getBounds(state.scale)
                state.updateBounds(maxX, maxY)
            }
            scope.launch {
                state.drag(panChange)
            }
        }
        val tapModifier = if (doubleTapScale != null && enable) {
            Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onTap()
                    },
                    onDoubleTap = { offset ->

                        scope.launch {
                            val diffX = offset.x - (constraints.maxWidth / 2)
                            val diffY = offset.y - (constraints.maxHeight / 2)
                            val dis = Offset(-diffX, -diffY)

                            val afterScale = doubleTapScale()
                            val (maxX, maxY) = getBounds(afterScale)
                            state.updateBounds(maxX, maxY)
                            state.animateDoubleTap(scale = afterScale, distance = dis)
                        }
                    }
                )
            }
        } else {
            Modifier
        }
        val zoomableModifier: Modifier = Modifier.transformable(
            state = transformableState,
            canPan = {  //canPan only allowed when image is zooming, otherwise will let other handle drag
                state.zooming
            }
        )
        Box(
            modifier = Modifier
                .then(zoomableModifier)
                .then(tapModifier)
                .layout { measurable, constraints ->
                    val placeable =
                        measurable.measure(constraints = constraints)
                    childHeight = placeable.height
                    childWidth = placeable.width
                    layout(
                        width = constraints.maxWidth,
                        height = constraints.maxHeight
                    ) {
                        placeable.placeRelativeWithLayer(
                            (constraints.maxWidth - placeable.width) / 2,
                            (constraints.maxHeight - placeable.height) / 2
                        ) {
                            scaleX = settingScale
                            scaleY = settingScale
                            translationX = settingTranslationX
                            translationY = settingTranslationY
                        }
                    }
                }
        ) {
            content.invoke(this)
        }
    }
}

/**
 * TODO DI-4304 remove this temporarily copy from compose foundation library 1.5
 */
fun Modifier.transformable(
    state: TransformableState,
    canPan: () -> Boolean,
    lockRotationOnZoomPan: Boolean = false,
    enabled: Boolean = true
) = composed(
    factory = {
        val updatePanZoomLock = rememberUpdatedState(lockRotationOnZoomPan)
        val updatedCanPan = rememberUpdatedState(canPan)
        val channel = remember { Channel<TransformEvent>(capacity = Channel.UNLIMITED) }
        if (enabled) {
            LaunchedEffect(state) {
                while (isActive) {
                    var event = channel.receive()
                    if (event !is TransformStarted) continue
                    try {
                        state.transform(MutatePriority.UserInput) {
                            while (event !is TransformStopped) {
                                (event as? TransformDelta)?.let {
                                    transformBy(it.zoomChange, it.panChange, it.rotationChange)
                                }
                                event = channel.receive()
                            }
                        }
                    } catch (_: CancellationException) {
                        // ignore the cancellation and start over again.
                    }
                }
            }
        }
        val block: suspend PointerInputScope.() -> Unit = remember {
            {
                coroutineScope {
                    awaitEachGesture {
                        try {
                            detectZoom(updatePanZoomLock, channel, updatedCanPan)
                        } catch (exception: CancellationException) {
                            if (!isActive) throw exception
                        } finally {
                            channel.trySend(TransformStopped)
                        }
                    }
                }
            }
        }
        if (enabled) Modifier.pointerInput(channel, block) else Modifier
    },
)

/**
 * TODO DI-4304 remove this temporarily copy from compose foundation library 1.5
 */
private suspend fun AwaitPointerEventScope.detectZoom(
    panZoomLock: State<Boolean>,
    channel: Channel<TransformEvent>,
    canPan: State<() -> Boolean>
) {
    var rotation = 0f
    var zoom = 1f
    var pan = Offset.Zero
    var pastTouchSlop = false
    val touchSlop = viewConfiguration.touchSlop
    var lockedToPanZoom = false
    awaitFirstDown(requireUnconsumed = false)
    do {
        val event = awaitPointerEvent()
        val canceled = event.changes.any { it.isConsumed }
        if (!canceled) {
            val zoomChange = event.calculateZoom()
            val rotationChange = event.calculateRotation()
            val panChange = event.calculatePan()

            if (!pastTouchSlop) {
                zoom *= zoomChange
                rotation += rotationChange
                pan += panChange

                val centroidSize = event.calculateCentroidSize(useCurrent = false)
                val zoomMotion = abs(1 - zoom) * centroidSize
                val rotationMotion = abs(rotation * PI.toFloat() * centroidSize / 180f)
                val panMotion = pan.getDistance()

                if (zoomMotion > touchSlop ||
                    rotationMotion > touchSlop ||
                    (panMotion > touchSlop && canPan.value.invoke())
                ) {
                    pastTouchSlop = true
                    lockedToPanZoom = panZoomLock.value && rotationMotion < touchSlop
                    channel.trySend(TransformStarted)
                }
            }

            if (pastTouchSlop) {
                val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                if (effectiveRotation != 0f ||
                    zoomChange != 1f ||
                    (panChange != Offset.Zero && canPan.value.invoke())
                ) {
                    channel.trySend(TransformDelta(zoomChange, panChange, effectiveRotation))
                }
                event.changes.forEach {
                    if (it.positionChanged()) {
                        it.consume()
                    }
                }
            }
        } else {
            channel.trySend(TransformStopped)
        }
        val finalEvent = awaitPointerEvent(pass = PointerEventPass.Final)
        // someone consumed while we were waiting for touch slop
        val finallyCanceled = finalEvent.changes.any { it.isConsumed } && !pastTouchSlop
    } while (!canceled && !finallyCanceled && event.changes.any { it.pressed })
}

/**
 * TODO DI-4304 remove this temporarily copy from compose foundation library 1.5
 */
internal sealed class TransformEvent {
    object TransformStarted : TransformEvent()
    object TransformStopped : TransformEvent()
    class TransformDelta(
        val zoomChange: Float,
        val panChange: Offset,
        val rotationChange: Float
    ) : TransformEvent()
}
