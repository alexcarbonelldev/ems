package com.ems.ems.ui.screen.detail

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ems.domain.common.extension.roundTo
import com.ems.ems.R
import com.ems.ems.ui.common.base.ViewEffectObserver
import com.ems.ems.ui.common.extension.drawText
import com.ems.ems.ui.component.ErrorComponent
import com.ems.ems.ui.component.LoadingComponent
import com.ems.ems.ui.component.TopBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Collections.max
import java.util.Collections.min
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

private const val CHART_CELLS_STROKE_WIDTH = 1f

private val chartCellsColor = Color.Black
private val solarPanelChartColor = Color.Red
private val gridChartColor = Color.Blue
private val quasarChartColor = Color.Green

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    viewModel.ViewEffectObserver { effect ->
        when (effect) {
            DetailViewEffect.NavigateBack -> navController.navigateUp()
        }
    }

    val state by viewModel.viewState.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        onBackClick = { viewModel.onViewIntent(DetailViewIntent.OnBackClick) },
        onRetryClick = { viewModel.onViewIntent(DetailViewIntent.OnRetryClick) }
    )
}

@Composable
private fun DetailScreen(
    state: DetailViewState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(stringResource(id = R.string.detail), showBackButton = true, onBackButtonClick = onBackClick)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                DetailViewState.Loading -> LoadingComponent()
                DetailViewState.Error -> ErrorComponent { onRetryClick() }
                is DetailViewState.Data -> Column {
                    Chart(state.chartValues)
                    ChartLegend()
                }
            }
        }
    }
}

@Composable
private fun Chart(
    chartValues: List<ChartValue>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = 48.dp, start = 48.dp, end = 24.dp, top = 24.dp)
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            val firstItem = chartValues.first()

            val calendar = Calendar.getInstance()
            calendar.time = firstItem.date
            val firstHour = calendar[Calendar.HOUR_OF_DAY]
            val offsetMinutesFromStart = calendar[Calendar.MINUTE]

            val lastItem = chartValues.last()
            calendar.time = lastItem.date
            val lastItemMinutes = calendar[Calendar.MINUTE]

            val offsetMinutesUntilEnd = if (lastItemMinutes > 0) {
                60 - lastItemMinutes
            } else {
                0
            }

            val xDistance = size.width / (chartValues.size + offsetMinutesFromStart + offsetMinutesUntilEnd + 1)

            val roundedMinValue = getRoundedMinChartValue(chartValues)
            val roundedMaxValue = getRoundedMaxChartValue(chartValues)
            val roundedRangeValue = (roundedMaxValue + roundedMinValue.absoluteValue)

            val gridPoints = mutableListOf<PointF>()
            val quasarPoints = mutableListOf<PointF>()
            val solarPanelPoints = mutableListOf<PointF>()

            val dateFormat = SimpleDateFormat("DD/MM", Locale.getDefault())
            var currentX = offsetMinutesFromStart.toFloat()

            chartValues.forEach { chartValue ->
                gridPoints.add(getPoint(roundedRangeValue, chartValue.gridValue, roundedMinValue, currentX, xDistance))
                quasarPoints.add(
                    getPoint(
                        roundedRangeValue,
                        chartValue.quasarValue,
                        roundedMinValue,
                        currentX,
                        xDistance
                    )
                )
                solarPanelPoints.add(
                    getPoint(
                        roundedRangeValue,
                        chartValue.solarPanelValue,
                        roundedMinValue,
                        currentX,
                        xDistance
                    )
                )

                calendar.time = chartValue.date
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minutes = calendar[Calendar.MINUTE]
                val monthAndDay = dateFormat.format(chartValue.date)

                if (minutes == 0) {
                    drawVerticalChartLine(currentX, hour, monthAndDay)
                }
                currentX += xDistance
            }

            if (offsetMinutesFromStart != 0) {
                val monthAndDay = dateFormat.format(firstItem.date)
                drawVerticalChartLine(0f, firstHour, monthAndDay, true)
            }
            if (offsetMinutesUntilEnd != 0) {
                val date = Date()
                date.time = lastItem.date.time + TimeUnit.HOURS.toMillis(1)
                calendar.time = date
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val monthAndDay = dateFormat.format(date)
                drawVerticalChartLine(size.width, hour, monthAndDay)
            }

            drawHorizontalChartLines(roundedRangeValue, roundedMaxValue)

            drawPointsLine(gridPoints, gridChartColor)
            drawPointsLine(solarPanelPoints, solarPanelChartColor)
            drawPointsLine(quasarPoints, quasarChartColor)
        }
    }
}

private fun DrawScope.drawHorizontalChartLines(
    roundedRangeValue: Long,
    roundedMaxValue: Long
) {
    val horizontalLines = roundedRangeValue / 10
    val yDistance = size.height / horizontalLines
    var currentY = 0f
    var currentValue = roundedMaxValue

    for (i in 0..horizontalLines) {
        drawLine(
            start = Offset(0f, currentY),
            end = Offset(size.width, currentY),
            color = chartCellsColor,
            strokeWidth = CHART_CELLS_STROKE_WIDTH
        )
        drawText("$currentValue kWh", -32f, currentY + 4, getChartTextPaint())
        currentY += yDistance
        currentValue -= 10
    }
}

private fun getRoundedMinChartValue(chartValues: List<ChartValue>): Long {
    return chartValues.map { chartValue ->
        min(listOf(chartValue.gridValue, chartValue.quasarValue, chartValue.solarPanelValue))
    }.minOf { it }
        .minus(5)
        .roundTo(10)
}

private fun getRoundedMaxChartValue(chartValues: List<ChartValue>): Long {
    return chartValues.map { chartValue ->
        max(listOf(chartValue.gridValue, chartValue.quasarValue, chartValue.solarPanelValue))
    }.maxOf { it }
        .plus(5)
        .roundTo(10)
}

private fun DrawScope.drawPointsLine(points: List<PointF>, color: Color) {
    for (i in 0 until points.size - 1) {
        drawLine(
            start = Offset(points[i].x, points[i].y),
            end = Offset(points[i + 1].x, points[i + 1].y),
            color = color,
            strokeWidth = 2f
        )
    }
}

private fun DrawScope.getPoint(
    roundedRangeValue: Long,
    value: Double,
    roundedMinValue: Long,
    currentX: Float,
    xDistance: Float
): PointF {
    val x = currentX + xDistance
    val y = (roundedRangeValue - (value + roundedMinValue.absoluteValue)) * (size.height / roundedRangeValue)
    return PointF(x, y.toFloat())
}

private fun DrawScope.drawVerticalChartLine(
    x: Float,
    hour: Int,
    monthAndDay: String,
    forceShowMonthAndDay: Boolean = false
) {
    drawLine(
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        color = chartCellsColor,
        strokeWidth = CHART_CELLS_STROKE_WIDTH
    )
    drawText("$hour:00", x, size.height + 48, getChartTextPaint())
    if (forceShowMonthAndDay || hour == 0) {
        drawText(monthAndDay, x, size.height + 24, getChartTextPaint())
    }
}

@Composable
private fun ChartLegend(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ChartLegendItem(stringResource(id = R.string.solar_panel_energy), solarPanelChartColor)
        ChartLegendItem(stringResource(id = R.string.grid_energy), gridChartColor)
        ChartLegendItem(stringResource(id = R.string.quasar_energy), quasarChartColor)
    }
}

@Composable
private fun ChartLegendItem(text: String, color: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RectangleShape)
                .background(color)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            fontSize = 16.sp
        )
    }
}

private fun getChartTextPaint(): Paint {
    return Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 10f
        color = 0xFF000000.toInt()
    }
}
