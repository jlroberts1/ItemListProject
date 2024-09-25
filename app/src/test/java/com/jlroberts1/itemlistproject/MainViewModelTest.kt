package com.jlroberts1.itemlistproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class MainViewModelTest {

    @Test
    fun `Initial scaffold state is default`() = runTest {
        val viewModel = MainViewModel()

        viewModel.scaffoldState.test {
            assertThat(awaitItem()).isEqualTo(ScaffoldState())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateScaffoldState should update the scaffold state`() = runTest {
        val viewModel = MainViewModel()
        val scaffoldState = ScaffoldState(
            topAppBarTitle = { Text("Test Title") },
            topAppBarColors = { TopAppBarDefaults.topAppBarColors(containerColor = Color.Red) },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        null
                    )
                }
            }
        )

        viewModel.updateScaffoldState(scaffoldState)

        viewModel.scaffoldState.test {
            assertThat(awaitItem()).isEqualTo(scaffoldState)
            cancelAndIgnoreRemainingEvents()
        }
    }
}