package com.ems.domain.common.extension

import kotlin.math.roundToLong

fun Double.percentageOf(total: Double): Double {
    return (this * 100 / total)
}

fun Double.roundTo(roundTo: Int): Long {
    return (this / roundTo).roundToLong() * roundTo
}
