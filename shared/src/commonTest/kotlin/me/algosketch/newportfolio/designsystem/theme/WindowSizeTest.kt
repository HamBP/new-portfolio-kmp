package me.algosketch.newportfolio.designsystem.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WindowSizeTest {

    @Test
    fun compactWhenWidthIsAtMost700dp() {
        assertTrue(WindowSize(width = 700.dp, height = 800.dp).isCompact)
        assertTrue(WindowSize(width = 375.dp, height = 800.dp).isCompact)
        assertFalse(WindowSize(width = 701.dp, height = 800.dp).isCompact)
    }

    @Test
    fun fluidSpClampsViewportWidthRatio() {
        // clamp(48px, 8vw, 88px)
        assertEquals(48.sp, WindowSize(width = 400.dp, height = 800.dp).fluidSp(48f, 8f, 88f))
        assertEquals(80.sp, WindowSize(width = 1000.dp, height = 800.dp).fluidSp(48f, 8f, 88f))
        assertEquals(88.sp, WindowSize(width = 1440.dp, height = 800.dp).fluidSp(48f, 8f, 88f))
    }
}
