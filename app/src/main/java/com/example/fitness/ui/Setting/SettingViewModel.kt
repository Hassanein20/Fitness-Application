package com.example.fitness.ui.Setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness.UData

class SettingViewModel : ViewModel() {

    private val _udata = MutableLiveData<UData>()
    val sharedData :  LiveData<UData> = _udata

    fun sharedclass(Class: UData){
        _udata.value = Class
    }

}