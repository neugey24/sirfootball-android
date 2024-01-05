package com.sirfootball.android.client

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppWebServiceClient {

    // private const val SERVICE_URL = "http://localhost/"

    // actual local IP needed for Android Dev
    //private const val SERVICE_URL = "http://192.168.86.35/"
    private const val SERVICE_URL = "http://192.168.86.35/app/user/"



        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
}

object ApiClient {
    val apiService: AppApi by lazy {
        AppWebServiceClient.retrofit.create(AppApi::class.java)
    }
}