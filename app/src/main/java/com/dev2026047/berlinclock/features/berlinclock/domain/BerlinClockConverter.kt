package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.BerlinClockState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime

class BerlinClockConverter {
    fun convert(time: ClockTime): BerlinClockState {
        return BerlinClockState(isSecondsLampOn = time.seconds % 2 == 0)
    }
}
