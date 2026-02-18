package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BerlinClockConverterKataExamplesTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `converts 00 colon 00 colon 00 to expected berlin clock state`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertTrue(result.isSecondsLampOn)
        assertEquals(0, result.fiveHoursLampOnCount)
        assertEquals(0, result.oneHoursLampOnCount)
        assertEquals(11, result.fiveMinutesRow.size)
        assertTrue(result.fiveMinutesRow.all { it == FiveMinutesLampState.Off })
        assertEquals(4, result.oneMinutesRow.size)
        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Off })
    }

    @Test
    fun `converts 13 colon 17 colon 01 to expected berlin clock state`() {
        val result = converter.convert(ClockTime(hours = 13, minutes = 17, seconds = 1))

        assertFalse(result.isSecondsLampOn)
        assertEquals(2, result.fiveHoursLampOnCount)
        assertEquals(3, result.oneHoursLampOnCount)

        assertEquals(FiveMinutesLampState.Yellow, result.fiveMinutesRow[0])
        assertEquals(FiveMinutesLampState.Yellow, result.fiveMinutesRow[1])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[2])
        assertTrue(result.fiveMinutesRow.drop(3).all { it == FiveMinutesLampState.Off })

        assertEquals(OneMinutesLampState.Yellow, result.oneMinutesRow[0])
        assertEquals(OneMinutesLampState.Yellow, result.oneMinutesRow[1])
        assertEquals(OneMinutesLampState.Off, result.oneMinutesRow[2])
        assertEquals(OneMinutesLampState.Off, result.oneMinutesRow[3])
    }

    @Test
    fun `converts 23 colon 59 colon 59 to expected berlin clock state`() {
        val result = converter.convert(ClockTime(hours = 23, minutes = 59, seconds = 59))

        assertFalse(result.isSecondsLampOn)
        assertEquals(4, result.fiveHoursLampOnCount)
        assertEquals(3, result.oneHoursLampOnCount)

        assertEquals(11, result.fiveMinutesRow.size)
        result.fiveMinutesRow.forEachIndexed { index, lamp ->
            if (index in listOf(2, 5, 8)) {
                assertEquals(FiveMinutesLampState.Red, lamp)
            } else {
                assertEquals(FiveMinutesLampState.Yellow, lamp)
            }
        }

        assertEquals(4, result.oneMinutesRow.size)
        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Yellow })
    }
}
