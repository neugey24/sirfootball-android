package com.sirfootball.android.data.api

import com.sirfootball.android.MainApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppWebServiceClient {

    // actual local IP needed for Android Dev
    companion object {
        // private const val SERVICE_URL = "http://localhost/"
        //private const val SERVICE_URL = "http://192.168.86.35/"
        private const val SERVICE_URL = "http://192.168.86.35/app/user/"
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshiIn : Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiIn))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofitIn: Retrofit): AppService {
        return retrofitIn.create(AppService::class.java)
    }

}