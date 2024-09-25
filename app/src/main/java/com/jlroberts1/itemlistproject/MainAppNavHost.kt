package com.jlroberts1.itemlistproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jlroberts1.itemlistproject.presentation.composables.getGroupColor
import com.jlroberts1.itemlistproject.presentation.itemgroups.ItemGroupsScreen
import com.jlroberts1.itemlistproject.presentation.items.ItemsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppNavHost(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.ItemGroupsScreen
    ) {
        composable<NavRoute.ItemGroupsScreen> {
            viewModel.updateScaffoldState(
                scaffoldState.copy(
                    topAppBarTitle = { Text(stringResource(R.string.item_groups_name)) },
                    topAppBarColors = { TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray) },
                    navigationIcon = {}
                )
            )
            ItemGroupsScreen(
                onNavigateToItems = { listId ->
                    navController.navigate(
                        NavRoute.ItemsScreen(listId = listId)
                    )
                }
            )
        }
        composable<NavRoute.ItemsScreen> {
            val args = it.toRoute<NavRoute.ItemsScreen>()
            val containerColor = getGroupColor(args.listId).containerColor
            viewModel.updateScaffoldState(
                scaffoldState.copy(
                    topAppBarTitle = {
                        Text(
                            stringResource(
                                R.string.items_for_group,
                                args.listId
                            )
                        )
                    },
                    topAppBarColors = { TopAppBarDefaults.topAppBarColors(containerColor = containerColor) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_content_desc)
                            )
                        }
                    }
                )
            )
            ItemsScreen(listId = args.listId)
        }
    }
}