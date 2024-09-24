package com.jlroberts1.itemlistproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jlroberts1.itemlistproject.data.httpclient.HttpClient
import com.jlroberts1.itemlistproject.data.retrofit.RetrofitClient
import com.jlroberts1.itemlistproject.data.serializer.Serializer
import com.jlroberts1.itemlistproject.ui.theme.ItemListProjectTheme
import com.jlroberts1.itemlistproject.ui.theme.composables.OneListItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var httpClient: HttpClient

    @Inject
    lateinit var retrofitClient: RetrofitClient

    @Inject
    lateinit var serializer: Serializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ItemListProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        val items = viewModel.items.collectAsStateWithLifecycle(emptyList())
                        val lazyListState = rememberLazyListState()
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items.value) { item ->
                                OneListItem(textValue = item.id.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}