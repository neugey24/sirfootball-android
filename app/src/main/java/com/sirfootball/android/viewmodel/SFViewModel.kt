package com.sirfootball.android.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sirfootball.android.MainApplication
import com.sirfootball.android.SF_USER_SESSION
import com.sirfootball.android.SF_USER_SESSION_KEY
import dagger.hilt.android.qualifiers.ApplicationContext

open class SFViewModel(): ViewModel() {
    companion object {

        const val APP_SECRET_HEADER_NAME = "X-SF-APP-SECRET"
        const val APP_USER_TOKEN_HEADER_NAME = "X-SF-APP-USER-TOKEN"

        const val APP_SECRET = "sfmob-K33CARhtTNwFbULM"
    }


    protected fun produceApiHeaders(): Map<String, String> {
        return mapOf(APP_SECRET_HEADER_NAME to APP_SECRET)
    }

    protected fun produceAuthHeaders(applicationContext: Context): Map<String, String> {
        val appToken = applicationContext.getSharedPreferences(SF_USER_SESSION, 0)
            .getString(SF_USER_SESSION_KEY, "NA") ?: "NA"
        return mapOf(APP_SECRET_HEADER_NAME to APP_SECRET, APP_USER_TOKEN_HEADER_NAME to appToken)
    }
}