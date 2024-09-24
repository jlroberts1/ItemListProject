package com.jlroberts1.itemlistproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jlroberts1.itemlistproject.ui.theme.ItemListProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App() {

        val navController: NavHostController = rememberNavController()
        val scaffoldState by viewModel.scaffoldState.collectAsStateWithLifecycle(ScaffoldState())

        ItemListProjectTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { scaffoldState.topAppBarTitle() },
                        colors = scaffoldState.topAppBarColors(),
                        navigationIcon = { scaffoldState.navigationIcon() }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    MainAppNavHost(
                        navController,
                        scaffoldState,
                        viewModel
                    )
                }
            }
        }
    }
}