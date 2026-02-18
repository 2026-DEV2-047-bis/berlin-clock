package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.OneMinutesLampState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BerlinClockConverterOneMinutesTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `one-minutes row has four off lamps at zero minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertEquals(4, result.oneMinutesRow.size)
        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Off })
    }

    @Test
    fun `one-minutes row has first lamp yellow at one minute`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 1, seconds = 0))

        assertEquals(OneMinutesLampState.Yellow, result.oneMinutesRow[0])
        assertTrue(result.oneMinutesRow.drop(1).all { it == OneMinutesLampState.Off })
    }

    @Test
    fun `one-minutes row has first two lamps yellow at two minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 2, seconds = 0))

        assertEquals(OneMinutesLampState.Yellow, result.oneMinutesRow[0])
        assertEquals(OneMinutesLampState.Yellow, result.oneMinutesRow[1])
        assertTrue(result.oneMinutesRow.drop(2).all { it == OneMinutesLampState.Off })
    }

    @Test
    fun `one-minutes row has four yellow lamps at four minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 4, seconds = 0))

        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Yellow })
    }

    @Test
    fun `one-minutes row resets to off at five minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 5, seconds = 0))

        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Off })
    }

    @Test
    fun `one-minutes row has four yellow lamps at fifty-nine minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 59, seconds = 0))

        assertTrue(result.oneMinutesRow.all { it == OneMinutesLampState.Yellow })
    }
}
