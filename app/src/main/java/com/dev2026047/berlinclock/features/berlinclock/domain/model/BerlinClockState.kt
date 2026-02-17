package com.dev2026047.berlinclock.features.berlinclock.domain.model

data class BerlinClockState(
    val isSecondsLampOn: Boolean,
    val fiveHoursLampOnCount: Int,
    val oneHoursLampOnCount: Int,
)
