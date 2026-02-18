package com.dev2026047.berlinclock.features.berlinclock.presentation.state

import com.dev2026047.berlinclock.features.berlinclock.domain.model.BerlinClockState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime

data class BerlinClockUiState(
    val time: ClockTime,
    val clockState: BerlinClockState,
)
