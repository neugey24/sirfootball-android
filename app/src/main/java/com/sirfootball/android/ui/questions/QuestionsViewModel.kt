package com.sirfootball.android.ui.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Questions Fragment"
    }
    val text: LiveData<String> = _text
}