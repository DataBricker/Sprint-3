package ru.sber.qa

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.test.assertContentEquals

class ScannerTest {

    @BeforeEach
    fun setUp() {
        mockkObject(Random.Default)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getScanDataThrowsScanTimeoutException(){
        every { Random.nextLong(5000L, 15000L) } returns (Scanner.SCAN_TIMEOUT_THRESHOLD * 2)

        assertThrows<ScanTimeoutException> { Scanner.getScanData() }
    }

    @Test
    fun getScanData() {
        val expected = byteArrayOf(1)
        every { Random.nextLong(5000L, 15000L) } returns (Scanner.SCAN_TIMEOUT_THRESHOLD * 0.2).toLong()
        every { Random.nextBytes(100) } returns expected

        var actual = Scanner.getScanData()

        assertContentEquals(expected, actual)
    }
}