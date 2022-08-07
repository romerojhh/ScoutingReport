package com.example.scoutingreport

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random.Default.nextFloat

private const val MAX_SPEED = 25
private const val MIN_SPEED = 7
private const val MAX_FONT_SIZE = 64f
private const val MIN_FONT_SIZE = 24f
private val characters = listOf("ジ", "ェ", "ッ", "ト", "パ", "ッ", "ク", "構", "成")
private val colors = listOf(0xffcefbe4, 0xff81ec72, 0xff5cd646, 0xff54d13c, 0xff4ccc32, 0xff43c728)

@Composable
fun MatrixText(
    modifier: Modifier = Modifier,
    stripCount: Int = 25,
    lettersPerStrip: Int = 20
) {
    // Where each strip is in Y
    // Drive composition with this state
    val stripY = remember { mutableStateListOf<Int>() }
    // Where each strip is in X
    val stripX = remember { IntArray(stripCount).toMutableList() }
    // The speed of each strip
    val dY = remember { IntArray(stripCount).toMutableList() }
    // The font size of each strip
    val stripFontSize = remember { IntArray(stripCount).toMutableList() }

    val paint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        typeface = Typeface.SANS_SERIF
    }

    Canvas(
        modifier
            .fillMaxSize()
            .background(Color.Black)) {
        val width = size.width
        val height = size.height

        for (i in 0 until stripCount) {
            var y = stripY.getOrNull(i)

            paint.textSize = stripFontSize[i].toFloat()
            if (y == null || (y > height + (lettersPerStrip * stripFontSize[i]))) {
                if (y == null) {
                    stripY.add(0)
                }
                // Initialise a strip in a random location
                stripX[i] = (nextFloat() * width).toInt()
                stripY[i] = -99 // Start off screen
                dY[i] = ((nextFloat() * MAX_SPEED.dp.value) + MIN_SPEED.dp.value).toInt()
                stripFontSize[i] = ((nextFloat() * MAX_FONT_SIZE) + MIN_FONT_SIZE).toInt()
            } else {
                val x = stripX[i]

                (0 until lettersPerStrip).forEach { _ ->
                    val randChar = characters.random()
                    paint.color = colors.random().toInt()

                    drawIntoCanvas {
                        it.nativeCanvas.drawText(randChar, x.toFloat(), y.toFloat(), paint)
                    }

                    y -= stripFontSize[i]
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        while(true) {
            delay(60)
            for (i in 0 until stripCount) {
                // Increase the start position of each strip which will trigger a recomposition
                stripY[i] = stripY[i] + dY[i]
            }
        }
    }
}

private fun<T> SnapshotStateList<T>.getOrNull(index: Int): T? {
    if (index < 0 || index >= size) return null
    return get(index)
}