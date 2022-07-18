package com.ems.ems.ui.common.extension

import android.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

fun DrawScope.drawText(text: String, x: Float, y: Float, paint: Paint) {
    drawIntoCanvas {
        it.nativeCanvas.drawText(text, x, y, paint)
    }
}
