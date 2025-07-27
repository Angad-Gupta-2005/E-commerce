package com.angad.ecommerce.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBuilder {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Optional: timeout settings
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: ApiService = Retrofit.Builder()
        .baseUrl("https://klinq.com/rest/V1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}