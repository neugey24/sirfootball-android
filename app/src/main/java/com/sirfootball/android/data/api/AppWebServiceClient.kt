package com.sirfootball.android.data.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppWebServiceClient {

    // actual local IP needed for Android Dev locally
    companion object {
        //private const val SERVICE_URL = "http://192.168.86.35/app/user/"
        private const val SERVICE_URL = "https://www.sirfootball.com/app/"
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
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideApiService(retrofitIn: Retrofit): AppService {
        return retrofitIn.create(AppService::class.java)
    }

}