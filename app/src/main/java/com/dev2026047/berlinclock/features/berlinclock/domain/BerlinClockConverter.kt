package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.BerlinClockState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState

class BerlinClockConverter {
    private companion object {
        const val FIVE_MINUTES_ROW_SIZE = 11
        const val ONE_MINUTES_ROW_SIZE = 4
        const val THREE_LAMP_INTERVAL = 3
        const val FIVE_MINUTE_BUCKET = 5
    }

    fun convert(time: ClockTime): BerlinClockState {
        return BerlinClockState(
            isSecondsLampOn = time.seconds % 2 == 0,
            fiveHoursLampOnCount = time.hours / FIVE_MINUTE_BUCKET,
            oneHoursLampOnCount = time.hours % FIVE_MINUTE_BUCKET,
            fiveMinutesRow = buildFiveMinutesRow(time.minutes),
            oneMinutesRow = buildOneMinutesRow(time.minutes),
        )
    }

    private fun buildFiveMinutesRow(minutes: Int): List<FiveMinutesLampState> {
        val litCount = minutes / FIVE_MINUTE_BUCKET
        return (1..FIVE_MINUTES_ROW_SIZE).map { position -> mapFiveMinutesLamp(position, litCount) }
    }

    private fun buildOneMinutesRow(minutes: Int): List<OneMinutesLampState> {
        val oneMinutesLitCount = minutes % FIVE_MINUTE_BUCKET
        return (1..ONE_MINUTES_ROW_SIZE).map { position ->
            if (position <= oneMinutesLitCount) {
                OneMinutesLampState.Yellow
            } else {
                OneMinutesLampState.Off
            }
        }
    }

    private fun mapFiveMinutesLamp(position: Int, litCount: Int): FiveMinutesLampState {
        return when {
            position > litCount -> FiveMinutesLampState.Off
            position % THREE_LAMP_INTERVAL == 0 -> FiveMinutesLampState.Red
            else -> FiveMinutesLampState.Yellow
        }
    }
}
