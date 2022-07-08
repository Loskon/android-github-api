package com.loskon.githubapi

import android.app.Application
import android.content.SharedPreferences
import com.loskon.githubapi.app.presentation.screens.repositoryinfo.RepoInfoViewModel
import com.loskon.githubapi.domain.model.RepositoryModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class CheckKoinModulesTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> Mockito.mock(clazz.java) }

    private val context = Mockito.mock(Application::class.java)
    private val repoInfoModuleTest = module { viewModel { RepoInfoViewModel(RepositoryModel()) } }

    @Test
    fun testKoinModules() {
        val koinApp = koinApplication {
            androidContext(context)
            modules(listOf(repoInfoModuleTest))
        }
        koinApp.koin.declareMock<SharedPreferences>()
        koinApp.checkModules()
    }
}