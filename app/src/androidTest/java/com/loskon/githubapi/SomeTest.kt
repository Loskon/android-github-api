package com.loskon.githubapi

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.loskon.features.util.preference.AppPreference
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SomeTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testAppPreference() {
        val key = "test_key"
        val testValue = "test_value"
        val defValue = "test_def_value"

        AppPreference.set(context, key, testValue)
        val insertedValue = AppPreference.get(context, key, defValue)
        assertEquals(insertedValue, testValue)

        AppPreference.remove(context, key)
        val removedValue = AppPreference.get(context, key, defValue)
        assertEquals(removedValue, defValue)
    }
}