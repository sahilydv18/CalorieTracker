package com.example.calorietracker.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

// interceptor for attaching api key in the header for all the https requests
class IngredientInterceptor(
    private val apiKey: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithApiKey: Request = originalRequest.newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .build()
        return chain.proceed(requestWithApiKey)
    }
}