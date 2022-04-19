package com.example.testing.ui.applybenefit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApplyBenefitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Apply Benefit Fragment"
    }
    val text: LiveData<String> = _text
}