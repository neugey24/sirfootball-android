package com.sirfootball.android.ui.draft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DraftViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Draft Fragment"
    }
    val text: LiveData<String> = _text
}