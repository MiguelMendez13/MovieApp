package com.tesoem.movieapp

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Host", "online-movie-database.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "3770d554afmshd2b1f19413ba899p18c6fdjsn7d3466b49f92")
            .build()

        return  chain.proceed(request)
    }
}