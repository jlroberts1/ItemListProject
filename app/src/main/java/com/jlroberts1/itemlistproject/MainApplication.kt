package com.jlroberts1.itemlistproject

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    @ApplicationContext
    lateinit var context: Context
}