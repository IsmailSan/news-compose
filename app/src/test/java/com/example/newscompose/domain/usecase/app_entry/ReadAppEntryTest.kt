package com.example.newscompose.domain.usecase.app_entry

import app.cash.turbine.test
import com.example.newscompose.domain.manager.LocalUserManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ReadAppEntryTest {

    private val localUserManager: LocalUserManager = mockk()
    private lateinit var readAppEntry: ReadAppEntry

    @Before
    fun setUp() {
        readAppEntry = ReadAppEntry(localUserManager)
    }

    @Test
    fun `readAppEntry returns flow of boolean from localUserManager`() = runTest {
        every { localUserManager.readAppEntry() } returns flowOf(true)

        readAppEntry().test {
            assertEquals(true, awaitItem())
            awaitComplete()
        }
    }
}
