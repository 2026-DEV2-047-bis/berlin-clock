package com.dev2026047.berlinclock.features.berlinclock.presentation.controller

import androidx.lifecycle.ViewModel
import com.dev2026047.berlinclock.features.berlinclock.domain.BerlinClockConverter
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockInputStatus
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BerlinClockController(
    private val converter: BerlinClockConverter = BerlinClockConverter(),
) : ViewModel() {
    private companion object {
        const val FORMAT_ERROR = "Use format HH:mm:ss"
        const val RANGE_ERROR = "Time values out of range"
    }

    private val _uiState = MutableStateFlow(
        BerlinClockUiState(
        time = ClockTime(hours = 0, minutes = 0, seconds = 0),
        clockState = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0)),
        rawInput = "00:00:00",
        status = BerlinClockInputStatus.Valid,
    ))
    val uiState: StateFlow<BerlinClockUiState> = _uiState.asStateFlow()

    fun setTime(time: ClockTime) {
        _uiState.update {
            BerlinClockUiState(
                time = time,
                clockState = converter.convert(time),
                rawInput = formatTime(time),
                status = BerlinClockInputStatus.Valid,
            )
        }
    }

    fun showSample(time: ClockTime) {
        setTime(time)
    }

    fun onInputChanged(raw: String) {
        _uiState.update {
            it.copy(
                rawInput = raw,
                status = BerlinClockInputStatus.Valid,
            )
        }
    }

    fun onConvertRequested() {
        when (val parseResult = parseTime(_uiState.value.rawInput)) {
            is ParseResult.Success -> setTime(parseResult.time)
            is ParseResult.FormatError -> {
                _uiState.update { it.copy(status = BerlinClockInputStatus.Error(FORMAT_ERROR)) }
            }

            is ParseResult.RangeError -> {
                _uiState.update { it.copy(status = BerlinClockInputStatus.Error(RANGE_ERROR)) }
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
