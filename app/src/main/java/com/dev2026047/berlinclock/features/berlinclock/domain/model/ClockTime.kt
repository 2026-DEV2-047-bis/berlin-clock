package com.dev2026047.berlinclock.features.berlinclock.domain.model

data class ClockTime(
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
) {
    init {
        require(hours in 0..23) { "Hours must be between 0 and 23" }
        require(minutes in 0..59) { "Minutes must be between 0 and 59" }
        require(seconds in 0..59) { "Seconds must be between 0 and 59" }
    }
}
