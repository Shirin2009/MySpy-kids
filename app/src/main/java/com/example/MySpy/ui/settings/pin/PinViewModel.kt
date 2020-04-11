package com.example.MySpy.ui.settings.pin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.MySpy.data.SharedPreferencesManager

class PinViewModel(private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {


    val pin = MutableLiveData<String>()

    init {

        if(sharedPreferencesManager.hasPin){
            pin.postValue(sharedPreferencesManager.getPin().toString())
        }else{
            pin.postValue("No pin set yet")
        }

    }

    fun setPin(newPin: String) {
        sharedPreferencesManager.setPin(newPin.toInt())
        pin.postValue(newPin)
    }

}