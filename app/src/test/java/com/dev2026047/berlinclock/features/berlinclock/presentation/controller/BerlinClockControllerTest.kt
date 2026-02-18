package com.dev2026047.berlinclock.features.berlinclock.presentation.controller

import com.dev2026047.berlinclock.features.berlinclock.domain.BerlinClockConverter
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
import com.dev2026047.berlinclock.features.berlinclock.presentation.state.BerlinClockInputStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BerlinClockControllerTest {
    @Test
    fun `initial state is midnight`() {
        val controller = BerlinClockController()

        assertEquals(ClockTime(hours = 0, minutes = 0, seconds = 0), controller.state.time)
        assertTrue(controller.state.clockState.isSecondsLampOn)
        assertEquals(0, controller.state.clockState.fiveHoursLampOnCount)
        assertEquals(0, controller.state.clockState.oneHoursLampOnCount)
        assertEquals("00:00:00", controller.state.rawInput)
        assertEquals(BerlinClockInputStatus.Valid, controller.state.status)
    }

    @Test
    fun `input changed updates raw input and sets status to valid`() {
        val controller = BerlinClockController()

        controller.onInputChanged("13:17:01")

        assertEquals("13:17:01", controller.state.rawInput)
        assertEquals(BerlinClockInputStatus.Valid, controller.state.status)
        assertEquals(ClockTime(hours = 0, minutes = 0, seconds = 0), controller.state.time)
    }

    @Test
    fun `convert requested with valid input updates time and clock state`() {
        val controller = BerlinClockController()

        controller.onInputChanged("13:17:01")
        controller.onConvertRequested()

        assertEquals(ClockTime(hours = 13, minutes = 17, seconds = 1), controller.state.time)
        assertFalse(controller.state.clockState.isSecondsLampOn)
        assertEquals(2, controller.state.clockState.fiveHoursLampOnCount)
        assertEquals(3, controller.state.clockState.oneHoursLampOnCount)
        assertEquals("13:17:01", controller.state.rawInput)
        assertEquals(BerlinClockInputStatus.Valid, controller.state.status)
        assertEquals(BerlinClockConverter().convert(ClockTime(13, 17, 1)), controller.state.clockState)
    }

    @Test
    fun `convert requested with malformed input sets format error and keeps last valid state`() {
        val controller = BerlinClockController()
        controller.setTime(ClockTime(hours = 13, minutes = 17, seconds = 1))

        controller.onInputChanged("13-17-01")
        controller.onConvertRequested()

        assertEquals(ClockTime(hours = 13, minutes = 17, seconds = 1), controller.state.time)
        assertEquals("13-17-01", controller.state.rawInput)
        assertEquals(
            BerlinClockInputStatus.Error("Use format HH:mm:ss"),
            controller.state.status,
        )
        assertEquals(BerlinClockConverter().convert(ClockTime(13, 17, 1)), controller.state.clockState)
    }

    @Test
    fun `convert requested with out of range input sets range error and keeps last valid state`() {
        val controller = BerlinClockController()
        controller.setTime(ClockTime(hours = 13, minutes = 17, seconds = 1))

        controller.onInputChanged("24:00:00")
        controller.onConvertRequested()

        assertEquals(ClockTime(hours = 13, minutes = 17, seconds = 1), controller.state.time)
        assertEquals("24:00:00", controller.state.rawInput)
        assertEquals(
            BerlinClockInputStatus.Error("Time values out of range"),
            controller.state.status,
        )
        assertEquals(BerlinClockConverter().convert(ClockTime(13, 17, 1)), controller.state.clockState)
    }

    @Test
    fun `show sample updates state for 23 colon 59 colon 59`() {
        val controller = BerlinClockController()

        controller.showSample(ClockTime(hours = 23, minutes = 59, seconds = 59))

        assertEquals(ClockTime(hours = 23, minutes = 59, seconds = 59), controller.state.time)
        assertFalse(controller.state.clockState.isSecondsLampOn)
        assertEquals(4, controller.state.clockState.fiveHoursLampOnCount)
        assertEquals(3, controller.state.clockState.oneHoursLampOnCount)
        assertEquals("23:59:59", controller.state.rawInput)
        assertEquals(BerlinClockInputStatus.Valid, controller.state.status)
        assertEquals(BerlinClockConverter().convert(ClockTime(23, 59, 59)), controller.state.clockState)
    }
}
