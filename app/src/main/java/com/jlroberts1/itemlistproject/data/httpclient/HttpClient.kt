package com.jlroberts1.itemlistproject.data.httpclient

import okhttp3.OkHttpClient

interface HttpClient {
    val client: OkHttpClient
}