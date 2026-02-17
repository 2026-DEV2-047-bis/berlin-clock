package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BerlinClockConverterSecondsTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `seconds lamp is on for zero seconds`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertTrue(result.isSecondsLampOn)
    }

    @Test
    fun `seconds lamp is off for one second`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 1))

        assertFalse(result.isSecondsLampOn)
    }

    @Test
    fun `seconds lamp is on for another even second`() {
        val result = converter.convert(ClockTime(hours = 12, minutes = 34, seconds = 58))

        assertTrue(result.isSecondsLampOn)
    }

    @Test
    fun `seconds lamp is off for another odd second`() {
        val result = converter.convert(ClockTime(hours = 23, minutes = 59, seconds = 59))

        assertFalse(result.isSecondsLampOn)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `clock time rejects invalid hours`() {
        ClockTime(hours = 24, minutes = 0, seconds = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `clock time rejects invalid minutes`() {
        ClockTime(hours = 0, minutes = 60, seconds = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `clock time rejects invalid seconds`() {
        ClockTime(hours = 0, minutes = 0, seconds = 60)
    }
}
