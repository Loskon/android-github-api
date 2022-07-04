package com.loskon.githubapi

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.utils.NetworkUtil
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
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

        AppPreference.save(context, key, testValue)
        val insertedValue = AppPreference.load(context, key, defValue)
        assertEquals(insertedValue, testValue)

        AppPreference.remove(context, key)
        val removedValue = AppPreference.load(context, key, defValue)
        assertEquals(removedValue, defValue)
    }

    @Test
    fun testInternetConnection() {
        val hasConnected = NetworkUtil.hasConnected(context)
        assertTrue("No Internet connection", hasConnected)
    }
}