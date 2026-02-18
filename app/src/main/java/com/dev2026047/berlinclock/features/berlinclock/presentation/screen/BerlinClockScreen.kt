package com.dev2026047.berlinclock.features.berlinclock.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.presentation.controller.berlinClockControllerProvider

@Composable
fun BerlinClockScreen(modifier: Modifier = Modifier) {
    val controller = berlinClockControllerProvider
    val uiState = controller.state

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Time: %02d:%02d:%02d".format(uiState.time.hours, uiState.time.minutes, uiState.time.seconds))
        Text(text = "Seconds: ${if (uiState.clockState.isSecondsLampOn) "ON" else "OFF"}")
        Text(text = "Five-hours count: ${uiState.clockState.fiveHoursLampOnCount}")
        Text(text = "One-hours count: ${uiState.clockState.oneHoursLampOnCount}")

        Text(text = "Five-minutes row")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            uiState.clockState.fiveMinutesRow.forEach { lampState ->
                LampIndicator(color = colorForFiveMinutesLamp(lampState))
            }
        }

        Text(text = "One-minutes row")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            uiState.clockState.oneMinutesRow.forEach { lampState ->
                LampIndicator(color = colorForOneMinutesLamp(lampState))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(onClick = { controller.showSample(ClockTime(0, 0, 0)) }) {
                Text("00:00:00")
            }
            Button(onClick = { controller.showSample(ClockTime(13, 17, 1)) }) {
                Text("13:17:01")
            }
            Button(onClick = { controller.showSample(ClockTime(23, 59, 59)) }) {
                Text("23:59:59")
            }
        }
    }
}

@Composable
private fun LampIndicator(color: Color) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .size(width = 20.dp, height = 20.dp)
            .background(color = color),
    )
}

private fun colorForFiveMinutesLamp(state: FiveMinutesLampState): Color {
    return when (state) {
        FiveMinutesLampState.Off -> Color.DarkGray
        FiveMinutesLampState.Yellow -> Color.Yellow
        FiveMinutesLampState.Red -> Color.Red
    }
}

private fun colorForOneMinutesLamp(state: OneMinutesLampState): Color {
    return when (state) {
        OneMinutesLampState.Off -> Color.DarkGray
        OneMinutesLampState.Yellow -> Color.Yellow
    }
}
