package com.dev2026047.berlinclock.features.berlinclock.presentation.state

sealed interface BerlinClockInputStatus {
    data object Valid : BerlinClockInputStatus

    data class Error(val message: String) : BerlinClockInputStatus
}
