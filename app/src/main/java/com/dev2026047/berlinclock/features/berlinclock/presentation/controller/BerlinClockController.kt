package com.dev2026047.berlinclock.features.berlinclock.presentation.controller

import com.dev2026047.berlinclock.features.berlinclock.domain.BerlinClockConverter
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockUiState
import com.dev2026047.berlinclock.shared.riverpod.Notifier

val berlinClockControllerProvider = BerlinClockController()

class BerlinClockController(
    private val converter: BerlinClockConverter = BerlinClockConverter(),
) : Notifier<BerlinClockUiState>(
    initialState = BerlinClockUiState(
        time = ClockTime(hours = 0, minutes = 0, seconds = 0),
        clockState = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0)),
    ),
) {
    fun setTime(time: ClockTime) {
        state = BerlinClockUiState(
            time = time,
            clockState = converter.convert(time),
        )
    }

    fun showSample(time: ClockTime) {
        setTime(time)
    }
}
