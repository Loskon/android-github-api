package com.loskon.githubapi

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.loskon.features.util.preference.AppPref
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SomeTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testAppPreference() {
        val key = "test_key"
        val testValue = true
        val defValue = false

        AppPref.set(context, key, testValue)
        val insertedValue = AppPref.get(context, key, defValue)
        assertEquals(insertedValue, testValue)

        AppPref.remove(context, key)
        val removedValue = AppPref.get(context, key, defValue)
        assertEquals(removedValue, defValue)
    }
}