package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import org.junit.Assert.assertEquals
import org.junit.Test

class BerlinClockConverterFiveHoursTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `five-hours lamp count is zero at midnight`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertEquals(0, result.fiveHoursLampOnCount)
    }

    @Test
    fun `five-hours lamp count stays zero before first bucket`() {
        val result = converter.convert(ClockTime(hours = 4, minutes = 0, seconds = 0))

        assertEquals(0, result.fiveHoursLampOnCount)
    }

    @Test
    fun `five-hours lamp count is one at five oclock`() {
        val result = converter.convert(ClockTime(hours = 5, minutes = 0, seconds = 0))

        assertEquals(1, result.fiveHoursLampOnCount)
    }

    @Test
    fun `five-hours lamp count is two for thirteen hours`() {
        val result = converter.convert(ClockTime(hours = 13, minutes = 0, seconds = 0))

        assertEquals(2, result.fiveHoursLampOnCount)
    }

    @Test
    fun `five-hours lamp count is four for twenty-three hours`() {
        val result = converter.convert(ClockTime(hours = 23, minutes = 0, seconds = 0))

        assertEquals(4, result.fiveHoursLampOnCount)
    }
}
