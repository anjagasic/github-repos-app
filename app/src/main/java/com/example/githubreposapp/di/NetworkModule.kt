package com.example.githubreposapp.di

import com.example.githubreposapp.data.network.GithubApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideRetrofit(get()) }
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideApi(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

fun provideApi(retrofit: Retrofit): GithubApiService = retrofit.create(GithubApiService::class.java)