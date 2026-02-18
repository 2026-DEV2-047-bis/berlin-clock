package com.dev2026047.berlinclock.features.berlinclock.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev2026047.berlinclock.features.berlinclock.domain.model.BerlinClockState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.presentation.controller.berlinClockControllerProvider
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockInputStatus

private val LampOff = Color(0xFFD9D9D9)
private val LampBorder = Color(0xFF616161)
private val LampRed = Color(0xFFE53935)
private val LampYellow = Color(0xFFFDD835)
private val ScreenBackground = Color(0xFFF2F2F2)
private val TextPrimary = Color(0xFF1A1A1A)

@Composable
fun BerlinClockScreen(modifier: Modifier = Modifier) {
    val controller = berlinClockControllerProvider
    val uiState = controller.state

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ScreenBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BerlinClockDisplay(
            clockState = uiState.clockState,
            digitalTime = formatTime(uiState.time),
        )

        ControlsPanel(
            rawInput = uiState.rawInput,
            status = uiState.status,
            onInputChanged = controller::onInputChanged,
            onConvert = controller::onConvertRequested,
            onSampleClick = controller::showSample,
            currentTimeLabel = formatTime(uiState.time),
        )
    }
}

@Composable
private fun BerlinClockDisplay(clockState: BerlinClockState, digitalTime: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SecondsLamp(isOn = clockState.isSecondsLampOn)
        HoursRow(litCount = clockState.fiveHoursLampOnCount, onColor = LampRed)
        HoursRow(litCount = clockState.oneHoursLampOnCount, onColor = LampRed)
        FiveMinutesRow(rowStates = clockState.fiveMinutesRow)
        OneMinutesRow(rowStates = clockState.oneMinutesRow)

        Text(
            text = digitalTime,
            color = TextPrimary,
            fontFamily = FontFamily.Monospace,
            fontSize = 40.sp,
        )
    }
}

@Composable
private fun SecondsLamp(isOn: Boolean) {
    Box(
        modifier = Modifier
            .size(96.dp)
            .background(color = if (isOn) LampRed else LampOff, shape = CircleShape)
            .border(width = 4.dp, color = LampBorder, shape = CircleShape),
    )
}

@Composable
private fun HoursRow(litCount: Int, onColor: Color, total: Int = 4) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(total) { index ->
            LampBlock(
                color = if (index < litCount) onColor else LampOff,
                width = 72.dp,
                height = 34.dp,
                corner = 14.dp,
            )
        }
    }
}

@Composable
private fun FiveMinutesRow(rowStates: List<FiveMinutesLampState>) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        rowStates.forEach { state ->
            LampBlock(
                color = when (state) {
                    FiveMinutesLampState.Off -> LampOff
                    FiveMinutesLampState.Yellow -> LampYellow
                    FiveMinutesLampState.Red -> LampRed
                },
                width = 24.dp,
                height = 34.dp,
                corner = 8.dp,
            )
        }
    }
}

@Composable
private fun OneMinutesRow(rowStates: List<OneMinutesLampState>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        rowStates.forEach { state ->
            LampBlock(
                color = if (state == OneMinutesLampState.Yellow) LampYellow else LampOff,
                width = 72.dp,
                height = 34.dp,
                corner = 14.dp,
            )
        }
    }
}

@Composable
private fun LampBlock(color: Color, width: Dp, height: Dp, corner: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color = color, shape = RoundedCornerShape(corner))
            .border(width = 2.dp, color = LampBorder, shape = RoundedCornerShape(corner)),
    )
}

@Composable
private fun ControlsPanel(
    rawInput: String,
    status: BerlinClockInputStatus,
    onInputChanged: (String) -> Unit,
    onConvert: () -> Unit,
    onSampleClick: (ClockTime) -> Unit,
    currentTimeLabel: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, LampBorder),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(text = "Input", color = TextPrimary)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = rawInput,
                onValueChange = onInputChanged,
                label = { Text("HH:mm:ss") },
                singleLine = true,
            )
            Button(onClick = onConvert) {
                Text("Convert")
            }
            if (status is BerlinClockInputStatus.Error) {
                Text(
                    text = status.message,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Text(
                text = "Current valid time: $currentTimeLabel",
                color = TextPrimary,
                fontFamily = FontFamily.Monospace,
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(onClick = { onSampleClick(ClockTime(0, 0, 0)) }) {
                    Text("00:00:00")
                }
                Button(onClick = { onSampleClick(ClockTime(13, 17, 1)) }) {
                    Text("13:17:01")
                }
                Button(onClick = { onSampleClick(ClockTime(23, 59, 59)) }) {
                    Text("23:59:59")
                }
            }
        }
    }
}

private fun formatTime(time: ClockTime): String {
    return "%02d:%02d:%02d".format(time.hours, time.minutes, time.seconds)
}
