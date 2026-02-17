package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import org.junit.Assert.assertEquals
import org.junit.Test

class BerlinClockConverterOneHoursTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `one-hours lamp count is zero at midnight`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertEquals(0, result.oneHoursLampOnCount)
    }

    @Test
    fun `one-hours lamp count is one for one oclock`() {
        val result = converter.convert(ClockTime(hours = 1, minutes = 0, seconds = 0))

        assertEquals(1, result.oneHoursLampOnCount)
    }

    @Test
    fun `one-hours lamp count is four for four oclock`() {
        val result = converter.convert(ClockTime(hours = 4, minutes = 0, seconds = 0))

        assertEquals(4, result.oneHoursLampOnCount)
    }

    @Test
    fun `one-hours lamp count resets at five oclock`() {
        val result = converter.convert(ClockTime(hours = 5, minutes = 0, seconds = 0))

        assertEquals(0, result.oneHoursLampOnCount)
    }

    @Test
    fun `one-hours lamp count is three for thirteen hours`() {
        val result = converter.convert(ClockTime(hours = 13, minutes = 0, seconds = 0))

        assertEquals(3, result.oneHoursLampOnCount)
    }

    @Test
    fun `one-hours lamp count is three for twenty-three hours`() {
        val result = converter.convert(ClockTime(hours = 23, minutes = 0, seconds = 0))

        assertEquals(3, result.oneHoursLampOnCount)
    }
}
