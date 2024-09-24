package com.jlroberts1.itemlistproject

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
data class ScaffoldState(
    val topAppBarTitle: @Composable () -> Unit = {},
    val topAppBarColors: @Composable () -> TopAppBarColors = {
        TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        )
    },
    val navigationIcon: @Composable () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _scaffoldState = MutableStateFlow(ScaffoldState())
    val scaffoldState = _scaffoldState.asStateFlow()

    fun updateScaffoldState(scaffoldState: ScaffoldState) {
        _scaffoldState.value = scaffoldState
    }
}