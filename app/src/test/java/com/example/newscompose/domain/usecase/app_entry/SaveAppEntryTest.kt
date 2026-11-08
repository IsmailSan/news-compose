package com.example.newscompose.domain.usecase.app_entry

import com.example.newscompose.domain.manager.LocalUserManager
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveAppEntryTest {

    private val localUserManager: LocalUserManager = mockk(relaxed = true)
    private lateinit var saveAppEntry: SaveAppEntry

    @Before
    fun setUp() {
        saveAppEntry = SaveAppEntry(localUserManager)
    }

    @Test
    fun `saveAppEntry calls localUserManager saveAppEntry`() = runTest {
        saveAppEntry()
        coVerify(exactly = 1) { localUserManager.saveAppEntry() }
    }
}
