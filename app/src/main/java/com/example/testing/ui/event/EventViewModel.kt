package com.example.testing.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is event Fragment"
    }
    val text: LiveData<String> = _text
}