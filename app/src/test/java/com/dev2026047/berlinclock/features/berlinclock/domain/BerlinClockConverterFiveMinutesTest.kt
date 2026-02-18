package com.dev2026047.berlinclock.features.berlinclock.domain

import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.domain.model.FiveMinutesLampState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BerlinClockConverterFiveMinutesTest {
    private val converter = BerlinClockConverter()

    @Test
    fun `five-minutes row has 11 off lamps at midnight`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 0, seconds = 0))

        assertEquals(11, result.fiveMinutesRow.size)
        assertTrue(result.fiveMinutesRow.all { it == FiveMinutesLampState.Off })
    }

    @Test
    fun `five-minutes row has one yellow lamp at five minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 5, seconds = 0))

        assertEquals(FiveMinutesLampState.Yellow, result.fiveMinutesRow[0])
        assertTrue(result.fiveMinutesRow.drop(1).all { it == FiveMinutesLampState.Off })
    }

    @Test
    fun `five-minutes row has two yellow lamps at ten minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 10, seconds = 0))

        assertEquals(FiveMinutesLampState.Yellow, result.fiveMinutesRow[0])
        assertEquals(FiveMinutesLampState.Yellow, result.fiveMinutesRow[1])
        assertTrue(result.fiveMinutesRow.drop(2).all { it == FiveMinutesLampState.Off })
    }

    @Test
    fun `third lamp is red at fifteen minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 15, seconds = 0))

        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[2])
    }

    @Test
    fun `third and sixth lamps are red at thirty minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 30, seconds = 0))

        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[2])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[5])
    }

    @Test
    fun `third sixth and ninth lamps are red at forty-five minutes`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 45, seconds = 0))

        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[2])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[5])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[8])
    }

    @Test
    fun `all eleven lamps are lit at fifty-nine minutes with quarter markers red`() {
        val result = converter.convert(ClockTime(hours = 0, minutes = 59, seconds = 0))

        assertEquals(11, result.fiveMinutesRow.size)
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[2])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[5])
        assertEquals(FiveMinutesLampState.Red, result.fiveMinutesRow[8])
        result.fiveMinutesRow.forEachIndexed { index, lamp ->
            if (index in listOf(2, 5, 8)) {
                assertEquals(FiveMinutesLampState.Red, lamp)
            } else {
                assertEquals(FiveMinutesLampState.Yellow, lamp)
            }
        }
    }
}
