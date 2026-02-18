package com.dev2026047.berlinclock.features.berlinclock.presentation.controller

import com.dev2026047.berlinclock.features.berlinclock.domain.BerlinClockConverter
import com.dev2026047.berlinclock.features.berlinclock.domain.model.ClockTime
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
    }

    @Test
    fun `set time updates state for 13 colon 17 colon 01`() {
        val controller = BerlinClockController()

        controller.setTime(ClockTime(hours = 13, minutes = 17, seconds = 1))

        assertEquals(ClockTime(hours = 13, minutes = 17, seconds = 1), controller.state.time)
        assertFalse(controller.state.clockState.isSecondsLampOn)
        assertEquals(2, controller.state.clockState.fiveHoursLampOnCount)
        assertEquals(3, controller.state.clockState.oneHoursLampOnCount)
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
        assertEquals(BerlinClockConverter().convert(ClockTime(23, 59, 59)), controller.state.clockState)
    }
}
