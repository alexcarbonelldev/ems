package com.ems.ems.ui.common.extension

import kotlin.math.roundToInt

fun Double.roundTo2Decimals(): Float {
    return ((this * 100.0).roundToInt() / 100.0).toFloat()
}

fun Double.formatToPercentage(): String {
    return "%.2f".format(this)
}
