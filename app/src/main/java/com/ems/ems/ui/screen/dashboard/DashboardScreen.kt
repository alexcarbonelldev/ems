package com.ems.ems.ui.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ems.ems.R
import com.ems.ems.ui.common.NavDestination
import com.ems.ems.ui.common.extension.formatToPercentage
import com.ems.ems.ui.common.extension.roundTo2Decimals
import com.ems.ems.ui.component.ErrorComponent
import com.ems.ems.ui.component.LoadingComponent
import com.ems.ems.ui.component.TopBar
import com.ems.ems.ui.theme.EmsTheme

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEffects.collect { effects ->
            if (effects.isEmpty()) return@collect

            val effect = effects.first()
            when (effect) {
                DashboardViewEffect.NavigateToDetail -> navController.navigate(NavDestination.DetailScreen.route)
            }
            viewModel.onEffectConsumed(effect)
        }
    }

    DashboardScreen(
        state = state,
        onStatisticsClick = { viewModel.onViewIntent(DashboardViewIntent.OnStatisticsClick) },
        onRetryClick = { viewModel.onViewIntent(DashboardViewIntent.OnRetryClick) }
    )
}

@Composable
private fun DashboardScreen(
    state: DashboardViewState,
    onStatisticsClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(stringResource(id = R.string.dashboard), showBackButton = false)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state) {
                DashboardViewState.Loading -> LoadingComponent()
                DashboardViewState.Error -> ErrorComponent() { onRetryClick() }
                is DashboardViewState.Data ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        QuasarInfo(state.chargedEnergyWithQuasar, state.dischargedEnergyFromQuasar)
                        LiveInfo(state.liveInfoViewState)
                        StatisticsInfo(state.statisticsInfoViewState, { onStatisticsClick() })
                    }
            }
        }
    }
}

@Composable
private fun QuasarInfo(chargedEnergy: Double, dischargedEnergy: Double, modifier: Modifier = Modifier) {
    Widget(title = stringResource(R.string.quasar_info), modifier = modifier) {
        Row() {
            QuasarInfoItem(
                text = stringResource(R.string.charged),
                energy = chargedEnergy,
                modifier = Modifier.weight(1f)
            )
            QuasarInfoItem(
                text = stringResource(R.string.discharged),
                energy = dischargedEnergy,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuasarInfoItem(text: String, energy: Double, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = text
        )
        Text(text = stringResource(R.string.value_with_kwh, energy.roundTo2Decimals()))
    }
}

@Composable
private fun LiveInfo(liveInfoViewState: LiveInfoViewState, modifier: Modifier = Modifier) {
    Widget(title = stringResource(R.string.live_data), modifier = modifier) {
        Column {
            WidgetItem(
                text = stringResource(R.string.solar_power_with_value, liveInfoViewState.solarPower.roundTo2Decimals())
            )
            WidgetItem(
                text = stringResource(R.string.grid_power_with_value, liveInfoViewState.gridPower.roundTo2Decimals())
            )
            WidgetItem(
                text = stringResource(
                    R.string.quasar_power_with_value,
                    liveInfoViewState.quasarsPower.roundTo2Decimals()
                )
            )
            WidgetItem(
                text = stringResource(
                    R.string.building_demand_with_value,
                    liveInfoViewState.buildingDemand.roundTo2Decimals()
                )
            )
        }
    }
}

@Composable
private fun StatisticsInfo(
    statisticsInfoViewState: StatisticsInfoViewState,
    onStatisticsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Widget(
        title = stringResource(R.string.statistics_data),
        modifier = modifier,
        onClick = onStatisticsClick
    ) {
        Column {
            WidgetItem(
                stringResource(
                    R.string.solar_power_with_value,
                    "${statisticsInfoViewState.solarPowerPercentage.formatToPercentage()}%"
                )
            )
            WidgetItem(
                stringResource(
                    R.string.grid_power_with_value,
                    "${statisticsInfoViewState.gridPowerPercentage.formatToPercentage()}%"
                )
            )
            WidgetItem(
                stringResource(
                    R.string.quasar_power_with_value,
                    "${statisticsInfoViewState.quasarsPowerPercentage.formatToPercentage()}%"
                )
            )
        }
    }
}

@Composable
private fun Widget(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
                fontWeight = FontWeight.Bold,
                text = title,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            content()
        }
    }
}

@Composable
private fun WidgetItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 2.dp),
        text = text
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    EmsTheme {
        DashboardScreen(
            DashboardViewState.Data(
                dischargedEnergyFromQuasar = 37.12,
                chargedEnergyWithQuasar = 54.12,
                liveInfoViewState = LiveInfoViewState(),
                statisticsInfoViewState = StatisticsInfoViewState()
            ),
            {}
        ) {}
    }
}
