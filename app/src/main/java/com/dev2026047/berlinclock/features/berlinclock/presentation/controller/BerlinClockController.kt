package com.dev2026047.berlinclock.features.berlinclock.presentation.controller

import com.dev2026047.berlinclock.features.berlinclock.domain.BerlinClockConverter
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockInputStatus
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockUiState
import com.dev2026047.berlinclock.shared.riverpod.Notifier

val berlinClockControllerProvider = BerlinClockController()

class BerlinClockController(
    private val converter: BerlinClockConverter = BerlinClockConverter(),
) : Notifier<BerlinClockUiState>(
    initialState = BerlinClockUiState(
        time = ClockTime(hours = 0, minutes = 0, seconds = 0),
        clockState = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0)),
        rawInput = "00:00:00",
        status = BerlinClockInputStatus.Valid,
    ),
) {
    private companion object {
        const val FORMAT_ERROR = "Use format HH:mm:ss"
        const val RANGE_ERROR = "Time values out of range"
    }

    fun setTime(time: ClockTime) {
        state = BerlinClockUiState(
            time = time,
            clockState = converter.convert(time),
            rawInput = formatTime(time),
            status = BerlinClockInputStatus.Valid,
        )
    }

    fun showSample(time: ClockTime) {
        setTime(time)
    }

    fun onInputChanged(raw: String) {
        state = state.copy(
            rawInput = raw,
            status = BerlinClockInputStatus.Valid,
        )
    }

    fun onConvertRequested() {
        when (val parseResult = parseTime(state.rawInput)) {
            is ParseResult.Success -> setTime(parseResult.time)
            is ParseResult.FormatError -> {
                state = state.copy(status = BerlinClockInputStatus.Error(FORMAT_ERROR))
            }

            is ParseResult.RangeError -> {
                state = state.copy(status = BerlinClockInputStatus.Error(RANGE_ERROR))
            }
        }
    }

    private fun parseTime(raw: String): ParseResult {
        val parts = raw.split(":")
        if (parts.size != 3 || parts.any { it.length != 2 || it.any { char -> !char.isDigit() } }) {
            return ParseResult.FormatError
        }

        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        val seconds = parts[2].toInt()

        return try {
            ParseResult.Success(ClockTime(hours = hours, minutes = minutes, seconds = seconds))
        } catch (_: IllegalArgumentException) {
            ParseResult.RangeError
        }
    }

    private fun formatTime(time: ClockTime): String {
        return "%02d:%02d:%02d".format(time.hours, time.minutes, time.seconds)
    }

    private sealed interface ParseResult {
        data class Success(val time: ClockTime) : ParseResult
        data object FormatError : ParseResult
        data object RangeError : ParseResult
    }
}
