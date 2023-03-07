package com.example.nrk_firebase_test

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ClickCanvas(
    clicks: List<ClickPoint>,
    onClick: (Int, Int) -> Unit = {_, _ -> }
) {
    BoxWithConstraints {
        val (height, width) = with(LocalDensity.current) {
            maxHeight.toPx() to maxWidth.toPx()
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter(
                    onTouchEvent = { event ->
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            val xPercent = ((event.x / width) * 100).toInt()
                            val yPercent = ((event.y / height) * 100).toInt()
                            onClick(xPercent, yPercent)
                        }

                        true
                    }
                ),
            onDraw = {
                clicks.forEach { click ->
                    val offset = Offset(
                        x = (size.width / 100) * click.x,
                        y = (size.height / 100) * click.y
                    )
                    drawCircle(color = Color.Black, radius = 10f, center = offset)
                }
            }
        )
    }
}