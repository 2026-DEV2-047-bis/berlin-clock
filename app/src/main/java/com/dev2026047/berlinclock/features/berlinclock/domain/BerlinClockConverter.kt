package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.BerlinClockState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState

class BerlinClockConverter {
    fun convert(time: ClockTime): BerlinClockState {
        val litCount = time.minutes / 5
        val fiveMinutesRow = (1..11).map { position ->
            when {
                position > litCount -> FiveMinutesLampState.Off
                position % 3 == 0 -> FiveMinutesLampState.Red
                else -> FiveMinutesLampState.Yellow
            }
        }
        val oneMinutesLitCount = time.minutes % 5
        val oneMinutesRow = (1..4).map { position ->
            if (position <= oneMinutesLitCount) {
                OneMinutesLampState.Yellow
            } else {
                OneMinutesLampState.Off
            }
        }

        return BerlinClockState(
            isSecondsLampOn = time.seconds % 2 == 0,
            fiveHoursLampOnCount = time.hours / 5,
            oneHoursLampOnCount = time.hours % 5,
            fiveMinutesRow = fiveMinutesRow,
            oneMinutesRow = oneMinutesRow,
        )
    }
}
