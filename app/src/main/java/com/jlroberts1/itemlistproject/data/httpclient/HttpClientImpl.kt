package com.jlroberts1.itemlistproject.data.httpclient

import android.util.Log
import com.jlroberts1.itemlistproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class HttpClientImpl @Inject constructor() : HttpClient {
    override val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor {
                    Log.d("OkHttpClient", it)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return@lazy builder.build()
    }
}